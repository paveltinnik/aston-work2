package org.paveltinnik.dao;//package org.paveltinnik.dao;
//
//import org.paveltinnik.model.Author;
//
//import java.sql.*;
//import java.util.HashSet;
//import java.util.Set;
//
//public class AuthorDao {
//    private final Connection connection;
//
//    public AuthorDao(Connection connection) {
//        this.connection = connection;
//    }
//
//    public Author save(Author author) throws SQLException {
//        String sql = "INSERT INTO authors (name) VALUES (?)";
//        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
//            stmt.setString(1, author.getName());
//            stmt.executeUpdate();
//            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
//                if (generatedKeys.next()) {
//                    author.setId(generatedKeys.getLong(1));
//                }
//            }
//        }
//        return author;
//    }
//
//    public Set<Author> findAll() throws SQLException {
//        Set<Author> authors = new HashSet<>();
//        String sql = "SELECT * FROM authors";
//        try (Statement stmt = connection.createStatement();
//             ResultSet rs = stmt.executeQuery(sql)) {
//            while (rs.next()) {
//                Author author = new Author();
//                author.setId(rs.getLong("id"));
//                author.setName(rs.getString("name"));
//                authors.add(author);
//            }
//        }
//        return authors;
//    }
//
//    public void delete(Long id) throws SQLException {
//        String sql = "DELETE FROM authors WHERE id = ?";
//        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
//            stmt.setLong(1, id);
//            stmt.executeUpdate();
//        }
//    }
//
//    // Другие методы, как update и т.д.
//}