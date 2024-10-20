package org.paveltinnik.repository.impl;

import org.paveltinnik.config.DatabaseConfig;
import org.paveltinnik.model.Book;
import org.paveltinnik.model.Genre;
import org.paveltinnik.repository.GenreRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GenreRepositoryImpl implements GenreRepository {

    private final DataSource dataSource = DatabaseConfig.getDataSource();

    @Override
    public void save(Genre genre) throws SQLException {
        String insertGenreQuery = "INSERT INTO genre (name) VALUES (?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement genreStmt = connection.prepareStatement(insertGenreQuery, Statement.RETURN_GENERATED_KEYS)) {

            genreStmt.setString(1, genre.getName());
            genreStmt.executeUpdate();

            try (ResultSet generatedKeys = genreStmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    genre.setId(generatedKeys.getLong(1));
                }
            }

            // Сохранение связи с книгами (Many-to-Many)
            saveGenreBooks(genre, connection);
        }
    }

    @Override
    public Genre findById(Long id) throws SQLException {
        String selectGenreQuery = "SELECT id, name FROM genre WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement genreStmt = connection.prepareStatement(selectGenreQuery)) {

            genreStmt.setLong(1, id);
            try (ResultSet resultSet = genreStmt.executeQuery()) {
                if (resultSet.next()) {
                    Genre genre = new Genre();
                    genre.setId(resultSet.getLong("id"));
                    genre.setName(resultSet.getString("name"));

                    // Получаем книги, связанные с жанром
                    genre.setBooks(getBooksForGenre(genre.getId(), connection));
                    return genre;
                }
            }
        }
        return null;
    }

    @Override
    public List<Genre> findAll() throws SQLException {
        List<Genre> genres = new ArrayList<>();
        String selectAllGenresQuery = "SELECT id, name FROM genre";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement genreStmt = connection.prepareStatement(selectAllGenresQuery);
             ResultSet resultSet = genreStmt.executeQuery()) {

            while (resultSet.next()) {
                Genre genre = new Genre();
                genre.setId(resultSet.getLong("id"));
                genre.setName(resultSet.getString("name"));

                // Получаем книги, связанные с жанром
                genre.setBooks(getBooksForGenre(genre.getId(), connection));
                genres.add(genre);
            }
        }
        return genres;
    }

    @Override
    public void update(Genre genre) throws SQLException {
        String updateGenreQuery = "UPDATE genre SET name = ? WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement genreStmt = connection.prepareStatement(updateGenreQuery)) {

            genreStmt.setString(1, genre.getName());
            genreStmt.setLong(2, genre.getId());
            genreStmt.executeUpdate();

            // Обновление связи с книгами
            updateGenreBooks(genre, connection);
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        String deleteGenreQuery = "DELETE FROM genre WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement genreStmt = connection.prepareStatement(deleteGenreQuery)) {

            // Удаление связей жанра с книгами
            deleteGenreBooks(id, connection);

            // Удаление самого жанра
            genreStmt.setLong(1, id);
            genreStmt.executeUpdate();
        }
    }

    private Set<Book> getBooksForGenre(Long genreId, Connection connection) throws SQLException {
        Set<Book> books = new HashSet<>();
        String selectBooksQuery = "SELECT b.id, b.title " +
                "FROM book b " +
                "JOIN book_genre bg ON b.id = bg.book_id " +
                "WHERE bg.genre_id = ?";

        try (PreparedStatement bookStmt = connection.prepareStatement(selectBooksQuery)) {
            bookStmt.setLong(1, genreId);
            try (ResultSet resultSet = bookStmt.executeQuery()) {
                while (resultSet.next()) {
                    Book book = new Book();
                    book.setId(resultSet.getLong("id"));
                    book.setTitle(resultSet.getString("title"));
                    books.add(book);
                }
            }
        }
        return books;
    }

    private void saveGenreBooks(Genre genre, Connection connection) throws SQLException {
        String insertBookGenreQuery = "INSERT INTO book_genre (book_id, genre_id) VALUES (?, ?)";
        try (PreparedStatement genreStmt = connection.prepareStatement(insertBookGenreQuery)) {
            for (Book book : genre.getBooks()) {
                genreStmt.setLong(1, book.getId());
                genreStmt.setLong(2, genre.getId());
                genreStmt.addBatch();
            }
            genreStmt.executeBatch();
        }
    }

    private void updateGenreBooks(Genre genre, Connection connection) throws SQLException {
        // Удаляем текущие связи жанра с книгами
        deleteGenreBooks(genre.getId(), connection);
        // Сохраняем новые связи
        saveGenreBooks(genre, connection);
    }

    private void deleteGenreBooks(Long genreId, Connection connection) throws SQLException {
        String deleteBookGenreQuery = "DELETE FROM book_genre WHERE genre_id = ?";
        try (PreparedStatement genreStmt = connection.prepareStatement(deleteBookGenreQuery)) {
            genreStmt.setLong(1, genreId);
            genreStmt.executeUpdate();
        }
    }
}
