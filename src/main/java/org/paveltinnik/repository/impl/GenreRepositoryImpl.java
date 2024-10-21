package org.paveltinnik.repository.impl;

import org.paveltinnik.entity.Book;
import org.paveltinnik.entity.Genre;
import org.paveltinnik.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

@Repository
public class GenreRepositoryImpl implements GenreRepository {

    Logger logger = Logger.getLogger(GenreRepositoryImpl.class.getName());

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GenreRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private final RowMapper<Genre> genreRowMapper = (rs, rowNum) -> {
        Genre genre = new Genre();
        genre.setId(rs.getLong("id"));
        genre.setName(rs.getString("name"));
        return genre;
    };

    private final RowMapper<Book> bookRowMapper = (rs, rowNum) -> {
        Book book = new Book();
        book.setId(rs.getLong("id"));
        book.setTitle(rs.getString("title"));
        return book;
    };

    @Override
    public List<Genre> findAll() {
        String sql = "SELECT * FROM genre";
        List<Genre> genres = jdbcTemplate.query(sql, genreRowMapper);

        // Получаем книги, связанные с жанром
        for (Genre genre : genres) {
            genre.setBooks(getBooksForGenre(genre.getId()));
        }

        return genres;
    }

    @Override
    public Genre findById(Long id) {
        String sql = "SELECT * FROM genre WHERE id = ?";
        Genre genre = jdbcTemplate.queryForObject(sql, new Object[]{id}, genreRowMapper);
        genre.setBooks(getBooksForGenre(genre.getId()));

        return genre;
    }

    @Override
    public void save(Genre genre) {
        String sql = "INSERT INTO genre (name) VALUES (?)";
        jdbcTemplate.update(sql, genre.getName());

        saveGenreBooks(genre);
    }

    @Override
    public void update(Genre genre) {
        String sql = "UPDATE genre SET name = ? WHERE id = ?";
        jdbcTemplate.update(sql, genre.getName(), genre.getId());

//        updateGenreBooks(genre);
    }

    @Override
    public void delete(Long id) {
        deleteGenreBooks(id);

        String deleteGenreSql = "DELETE FROM genre WHERE id = ?";
        jdbcTemplate.update(deleteGenreSql, id);
    }

    private Set<Book> getBooksForGenre(Long genreId) {

        String selectBooksQuery = "SELECT b.id, b.title " +
                "FROM book b " +
                "JOIN book_genre bg ON b.id = bg.book_id " +
                "WHERE bg.genre_id = ?";

        return new HashSet<>(jdbcTemplate.query(selectBooksQuery, new Object[]{genreId}, bookRowMapper));
    }

    private void updateGenreBooks(Genre genre) {
        // Удаляем текущие связи жанра с книгами
        deleteGenreBooks(genre.getId());
        // Сохраняем новые связи
        saveGenreBooks(genre);
    }

    private void saveGenreBooks(Genre genre) {
        // Получаем сохраненный genre из БД
        String selectGenreByName = "SELECT * FROM genre WHERE name = ?";
        Genre genreFromDB = jdbcTemplate.queryForObject(selectGenreByName, new Object[]{genre.getName()}, genreRowMapper);

        // Сохраняем bookIds для полученного genre
        String insertBookGenreQuery = "INSERT INTO book_genre (book_id, genre_id) VALUES (?, ?)";
        for (Book book : genre.getBooks()) {
            jdbcTemplate.update(insertBookGenreQuery, book.getId(), genreFromDB.getId());
        }
    }

    private void deleteGenreBooks(Long genreId) {
        String deleteBookGenreQuery = "DELETE FROM book_genre WHERE genre_id = ?";
        jdbcTemplate.update(deleteBookGenreQuery, genreId);
    }
}
