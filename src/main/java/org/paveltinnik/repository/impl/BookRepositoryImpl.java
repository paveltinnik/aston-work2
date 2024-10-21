package org.paveltinnik.repository.impl;

import org.paveltinnik.entity.Author;
import org.paveltinnik.entity.Book;
import org.paveltinnik.entity.Genre;
import org.paveltinnik.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

@Repository
public class BookRepositoryImpl implements BookRepository {

    Logger logger = Logger.getLogger(BookRepositoryImpl.class.getName());

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private final RowMapper<Book> bookRowMapper = (rs, rowNum) -> {
        Book book = new Book();
        book.setId(rs.getLong("id"));
        book.setTitle(rs.getString("title"));
        return book;
    };

    @Override
    public List<Book> findAll() {

        String selectAllBooksQuery = "SELECT b.id, b.title, a.id as author_id, a.name as author_name " +
                "FROM book b " +
                "JOIN author a ON b.author_id = a.id";

        return jdbcTemplate.query(selectAllBooksQuery, this::mapRow);
    }

    @Override
    public Book findById(Long id) {
        String selectBookQuery = "SELECT b.id, b.title, a.id as author_id, a.name as author_name " +
                "FROM book b " +
                "JOIN author a ON b.author_id = a.id " +
                "WHERE b.id = ?";

        return jdbcTemplate.queryForObject(selectBookQuery, new Object[]{id}, this::mapRow);
    }

    @Override
    public void save(Book book) {
        String insertBookQuery = "INSERT INTO book (title, author_id) VALUES (?, ?)";

        jdbcTemplate.update(insertBookQuery, book.getTitle(), book.getAuthor().getId());

        // Получаем сохраненный genre из БД
        String selectBookByName = "SELECT * FROM book WHERE title = ?";
        Book bookFromDB = jdbcTemplate.queryForObject(selectBookByName, new Object[]{book.getTitle()}, bookRowMapper);
        // Устанавливаем genres предыдущего book
        assert bookFromDB != null;
        bookFromDB.setGenres(book.getGenres());

        saveBookGenres(bookFromDB);
    }

    @Override
    public void update(Book book) {
        String updateBookQuery = "UPDATE book SET title = ?, author_id = ? WHERE id = ?";
        jdbcTemplate.update(updateBookQuery, book.getTitle(), book.getAuthor().getId(), book.getId());

        updateBookGenres(book);
    }

    @Override
    public void delete(Long id) {
        deleteBookGenres(id);

        String deleteBookQuery = "DELETE FROM book WHERE id = ?";
        jdbcTemplate.update(deleteBookQuery, id);
    }

    private Set<Genre> getGenresForBook(Long bookId) {

        String selectGenresQuery = "SELECT g.id, g.name " +
                "FROM genre g " +
                "JOIN book_genre bg ON g.id = bg.genre_id " +
                "WHERE bg.book_id = ?";

        return new HashSet<>(jdbcTemplate.query(selectGenresQuery, new Object[]{bookId}, (rs, rowNum) ->
                new Genre(rs.getLong("id"), rs.getString("name"))));
    }

    private void saveBookGenres(Book book) {
        String insertBookGenreQuery = "INSERT INTO book_genre (book_id, genre_id) VALUES (?, ?)";

        for (Genre genre : book.getGenres()) {
            jdbcTemplate.update(insertBookGenreQuery, book.getId(), genre.getId());
        }
    }

    private void updateBookGenres(Book book) {
        // Удаляем текущие жанры книги
        deleteBookGenres(book.getId());
        // Сохраняем новые жанры
        saveBookGenres(book);
    }

    private void deleteBookGenres(Long bookId) {
        String deleteBookGenreQuery = "DELETE FROM book_genre WHERE book_id = ?";
        jdbcTemplate.update(deleteBookGenreQuery, bookId);
    }

    private Book mapRow(ResultSet rs, int rowNum) {
        try {
            Long authorId = rs.getLong("author_id");
            String authorName = rs.getString("author_name");
            Author author = new Author(authorId, authorName);

            Book book = new Book(rs.getLong("id"), rs.getString("title"), author);
            book.setGenres(getGenresForBook(book.getId()));
            return book;
        } catch (Exception e) {
            logger.warning(e.getMessage());
        }
        return null;
    }
}