package org.paveltinnik.repository.impl;

import org.paveltinnik.config.DatabaseConfig;
import org.paveltinnik.dto.AuthorDTO;
import org.paveltinnik.entity.Author;
import org.paveltinnik.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Repository
public class AuthorRepositoryImpl implements AuthorRepository {

    Logger log = Logger.getLogger(AuthorRepositoryImpl.class.getName());

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AuthorRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private final RowMapper<Author> authorRowMapper = (rs, rowNum) -> {
        Author author = new Author();
        author.setId(rs.getLong("id"));
        author.setName(rs.getString("name"));
        return author;
    };

    private final DataSource dataSource = DatabaseConfig.getDataSource();

    @Override
    public void save(Author author) {
        String sql = "INSERT INTO Author (name) VALUES (?)";
        jdbcTemplate.update(sql, author.getName());
    }

//    @Override
//    public void save(Author author) throws SQLException {
//        String sql = "INSERT INTO author (name) VALUES (?)";
//        try (Connection connection = dataSource.getConnection();
//             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
//            stmt.setString(1, author.getName());
//            stmt.executeUpdate();
//            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
//                if (generatedKeys.next()) {
//                    author.setId(generatedKeys.getLong(1));
//                }
//            }
//        }
//    }

    @Override
    public Author findById(Long id) throws SQLException {
        String sql = "SELECT * FROM author WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Author author = new Author();
                    author.setId(rs.getLong("id"));
                    author.setName(rs.getString("name"));
                    return author;
                } else {
                    return null;
                }
            }
        }
    }

    @Override
    public List<Author> findAll() {
        String sql = "SELECT * FROM author";
        log.info("SQL: " + jdbcTemplate.query(sql, authorRowMapper));
        return jdbcTemplate.query(sql, authorRowMapper);
    }

//    @Override
//    public List<Author> findAll() throws SQLException {
//        String sql = "SELECT * FROM author";
//        List<Author> authors = new ArrayList<>();
//        try (Connection connection = dataSource.getConnection();
//             PreparedStatement stmt = connection.prepareStatement(sql);
//             ResultSet rs = stmt.executeQuery()) {
//            while (rs.next()) {
//                Author author = new Author();
//                author.setId(rs.getLong("id"));
//                author.setName(rs.getString("name"));
//                authors.add(author);
//            }
//        }
//        return authors;
//    }

    @Override
    public void update(Author author) throws SQLException {
        String sql = "UPDATE author SET name = ? WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, author.getName());
            stmt.setLong(2, author.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM author WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }
}
