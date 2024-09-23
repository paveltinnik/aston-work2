package org.paveltinnik.dao;//package org.paveltinnik.dao;
//
//import com.zaxxer.hikari.HikariDataSource;
//import org.paveltinnik.model.Genre;
//
//import java.sql.*;
//
//public class GenreDao {
//    private HikariDataSource dataSource;
//
//    public GenreDao(HikariDataSource dataSource) {
//        this.dataSource = dataSource;
//    }
//
//    public Genre findById(Long id) {
//        try (Connection connection = dataSource.getConnection();
//             PreparedStatement statement = connection.prepareStatement("SELECT * FROM genres WHERE id = ?")) {
//            statement.setLong(1, id);
//            ResultSet resultSet = statement.executeQuery();
//            if (resultSet.next()) {
//                Genre genre = new Genre(resultSet.getLong("id"), resultSet.getString("name"));
//                return genre;
//            }
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//        return null;
//    }
//
//    public Genre create(Genre genre) {
//        try (Connection connection = dataSource.getConnection();
//             PreparedStatement statement = connection.prepareStatement("INSERT INTO genres (name) VALUES (?)",
//                     Statement.RETURN_GENERATED_KEYS)) {
//            statement.setString(1, genre.getName());
//            statement.executeUpdate();
//            ResultSet generatedKeys = statement.getGeneratedKeys();
//            if (generatedKeys.next()) {
//                genre.setId(generatedKeys.getLong(1));
//            }
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//        return genre;
//    }
//
//    public Genre update(Genre genre) {
//        try (Connection connection = dataSource.getConnection();
//             PreparedStatement statement = connection.prepareStatement("UPDATE genres SET name = ? WHERE id = ?")) {
//            statement.setString(1, genre.getName());
//            statement.setLong(2, genre.getId());
//            statement.executeUpdate();
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//        return genre;
//    }
//
//    public void delete(Long id) {
//        try (Connection connection = dataSource.getConnection();
//             PreparedStatement statement = connection.prepareStatement("DELETE FROM genres WHERE id = ?")) {
//            statement.setLong(1, id);
//            statement.executeUpdate();
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//    }
//}