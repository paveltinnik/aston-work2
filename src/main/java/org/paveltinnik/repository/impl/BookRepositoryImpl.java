package org.paveltinnik.repository.impl;

import org.paveltinnik.config.DatabaseConfig;
import org.paveltinnik.model.Author;
import org.paveltinnik.model.Book;
import org.paveltinnik.model.Genre;
import org.paveltinnik.repository.BookRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BookRepositoryImpl implements BookRepository {

    private final DataSource dataSource = DatabaseConfig.getDataSource();

    @Override
    public void save(Book book) throws SQLException {
        String insertBookQuery = "INSERT INTO book (title, author_id) VALUES (?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement bookStmt = connection.prepareStatement(insertBookQuery, Statement.RETURN_GENERATED_KEYS)) {

            bookStmt.setString(1, book.getTitle());
            bookStmt.setLong(2, book.getAuthor().getId());
            bookStmt.executeUpdate();

            // Получаем сгенерированный ключ (ID книги)
            try (ResultSet generatedKeys = bookStmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    book.setId(generatedKeys.getLong(1));
                }
            }

            // Сохраняем жанры книги
            saveBookGenres(book, connection);
        }
    }

    @Override
    public Book findById(Long id) throws SQLException {
        String selectBookQuery = "SELECT b.id, b.title, a.id as author_id, a.name as author_name " +
                "FROM book b " +
                "JOIN author a ON b.author_id = a.id " +
                "WHERE b.id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement bookStmt = connection.prepareStatement(selectBookQuery)) {

            bookStmt.setLong(1, id);
            try (ResultSet resultSet = bookStmt.executeQuery()) {
                if (resultSet.next()) {
                    // Сбор информации о книге и авторе
                    Long authorId = resultSet.getLong("author_id");
                    String authorName = resultSet.getString("author_name");
                    Author author = new Author(authorId, authorName);

                    Book book = new Book(resultSet.getLong("id"), resultSet.getString("title"), author);
                    book.setGenres(getGenresForBook(book.getId(), connection));

                    return book;
                }
            }
        }
        return null;
    }

    @Override
    public List<Book> findAll() throws SQLException {
        List<Book> books = new ArrayList<>();
        String selectAllBooksQuery = "SELECT b.id, b.title, a.id as author_id, a.name as author_name " +
                "FROM book b " +
                "JOIN author a ON b.author_id = a.id";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement bookStmt = connection.prepareStatement(selectAllBooksQuery);
             ResultSet resultSet = bookStmt.executeQuery()) {

            while (resultSet.next()) {
                Long authorId = resultSet.getLong("author_id");
                String authorName = resultSet.getString("author_name");
                Author author = new Author(authorId, authorName);

                Book book = new Book(resultSet.getLong("id"), resultSet.getString("title"), author);
                book.setGenres(getGenresForBook(book.getId(), connection));

                books.add(book);
            }
        }
        return books;
    }

    @Override
    public void update(Book book) throws SQLException {
        String updateBookQuery = "UPDATE book SET title = ?, author_id = ? WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement bookStmt = connection.prepareStatement(updateBookQuery)) {

            bookStmt.setString(1, book.getTitle());
            bookStmt.setLong(2, book.getAuthor().getId());
            bookStmt.setLong(3, book.getId());
            bookStmt.executeUpdate();

            // Обновляем жанры книги
            updateBookGenres(book, connection);
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        String deleteBookQuery = "DELETE FROM book WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement bookStmt = connection.prepareStatement(deleteBookQuery)) {
            // Удаление книги
            bookStmt.setLong(1, id);
            bookStmt.executeUpdate();

            // Удаление связей с жанрами
            deleteBookGenres(id, connection);
        }
    }

    private Set<Genre> getGenresForBook(Long bookId, Connection connection) throws SQLException {
        Set<Genre> genres = new HashSet<>();
        String selectGenresQuery = "SELECT g.id, g.name " +
                "FROM genre g " +
                "JOIN book_genre bg ON g.id = bg.genre_id " +
                "WHERE bg.book_id = ?";

        try (PreparedStatement genreStmt = connection.prepareStatement(selectGenresQuery)) {
            genreStmt.setLong(1, bookId);
            try (ResultSet resultSet = genreStmt.executeQuery()) {
                while (resultSet.next()) {
                    genres.add(new Genre(resultSet.getLong("id"), resultSet.getString("name")));
                }
            }
        }
        return genres;
    }

    private void saveBookGenres(Book book, Connection connection) throws SQLException {
        String insertBookGenreQuery = "INSERT INTO book_genre (book_id, genre_id) VALUES (?, ?)";
        try (PreparedStatement genreStmt = connection.prepareStatement(insertBookGenreQuery)) {
            for (Genre genre : book.getGenres()) {
                genreStmt.setLong(1, book.getId());
                genreStmt.setLong(2, genre.getId());
                genreStmt.addBatch();
            }
            genreStmt.executeBatch();
        }
    }

    private void updateBookGenres(Book book, Connection connection) throws SQLException {
        // Удаляем текущие жанры книги
        deleteBookGenres(book.getId(), connection);
        // Сохраняем новые жанры
        saveBookGenres(book, connection);
    }

    private void deleteBookGenres(Long bookId, Connection connection) throws SQLException {
        String deleteBookGenreQuery = "DELETE FROM book_genre WHERE book_id = ?";
        try (PreparedStatement genreStmt = connection.prepareStatement(deleteBookGenreQuery)) {
            genreStmt.setLong(1, bookId);
            genreStmt.executeUpdate();
        }
    }
}
