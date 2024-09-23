package org.paveltinnik.dao;//package org.paveltinnik.dao;
//
//import com.zaxxer.hikari.HikariDataSource;
//import org.paveltinnik.model.Book;
//import org.paveltinnik.model.Genre;
//
//import java.sql.*;
//import java.util.HashSet;
//import java.util.Set;
//
//public class BookDao {
//    private HikariDataSource dataSource;
//
//    public BookDao(HikariDataSource dataSource) {
//        this.dataSource = dataSource;
//    }
//
//    public Book findById(Long id) {
//        try (Connection connection = dataSource.getConnection();
//             PreparedStatement statement = connection.prepareStatement("SELECT * FROM books WHERE id = ?")) {
//            statement.setLong(1, id);
//            ResultSet resultSet = statement.executeQuery();
//            if (resultSet.next()) {
//                Book book = new Book(resultSet.getLong("id"), resultSet.getString("title"));
//                book.setAuthor(new AuthorDao(dataSource).findById(resultSet.getLong("author_id")));
//                book.setGenres(loadGenresForBook(book.getId()));
//                return book;
//            }
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//        return null;
//    }
//
//    public Book create(Book book) {
//        try (Connection connection = dataSource.getConnection();
//             PreparedStatement statement = connection.prepareStatement("INSERT INTO books (title, author_id) VALUES (?, ?)",
//                     Statement.RETURN_GENERATED_KEYS)) {
//            statement.setString(1, book.getTitle());
//            statement.setLong(2, book.getAuthor().getId());
//            statement.executeUpdate();
//            ResultSet generatedKeys = statement.getGeneratedKeys();
//            if (generatedKeys.next()) {
//                book.setId(generatedKeys.getLong(1));
//            }
//            saveGenresForBook(book.getId(), book.getGenres());
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//        return book;
//    }
//
//    public Book update(Book book) {
//        try (Connection connection = dataSource.getConnection();
//             PreparedStatement statement = connection.prepareStatement("UPDATE books SET title = ?, author_id = ? WHERE id = ?")) {
//            statement.setString(1, book.getTitle());
//            statement.setLong(2, book.getAuthor().getId());
//            statement.setLong(3, book.getId());
//            statement.executeUpdate();
//            saveGenresForBook(book.getId(), book.getGenres());
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//        return book;
//    }
//
//    public void delete(Long id) {
//        try (Connection connection = dataSource.getConnection();
//             PreparedStatement statement = connection.prepareStatement("DELETE FROM books WHERE id = ?")) {
//            statement.setLong(1, id);
//            statement.executeUpdate();
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//    }
//
//    private Set<Genre> loadGenresForBook(Long bookId) {
//        Set<Genre> genres = new HashSet<>();
//        try (Connection connection = dataSource.getConnection();
//             PreparedStatement statement = connection.prepareStatement(
//                     "SELECT * FROM genres g INNER JOIN book_genre bg ON g.id = bg.genre_id WHERE bg.book_id = ?")) {
//            statement.setLong(1, bookId);
//            ResultSet resultSet = statement.executeQuery();
//            while (resultSet.next()) {
//                genres.add(new Genre(resultSet.getLong("id"), resultSet.getString("name")));
//            }
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//        return genres;
//    }
//
//    private void saveGenresForBook(Long bookId, Set<Genre> genres) {
//        try (Connection connection = dataSource.getConnection();
//             PreparedStatement statement = connection.prepareStatement("DELETE FROM book_genre WHERE book_id = ?");
//             PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO book_genre (book_id, genre_id) VALUES (?, ?)")) {
//            statement.setLong(1, bookId);
//            statement.executeUpdate();
//            for (Genre genre : genres) {
//                insertStatement.setLong(1, bookId);
//                insertStatement.setLong(2, genre.getId());
//                insertStatement.executeUpdate();
//            }
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//    }
//}