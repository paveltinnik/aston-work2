package org.paveltinnik.repository.impl;

import org.paveltinnik.entity.Author;
import org.paveltinnik.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class AuthorRepositoryImpl implements AuthorRepository {

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

    @Override
    public List<Author> findAll() {
        String sql = "SELECT * FROM author";
        return jdbcTemplate.query(sql, authorRowMapper);
    }

    @Override
    public Author findById(Long id) {
        String sql = "SELECT * FROM author WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, authorRowMapper);
    }

    @Override
    public void save(Author author) {
        String sql = "INSERT INTO author (name) VALUES (?)";
        jdbcTemplate.update(sql, author.getName());
    }

    @Override
    public void update(Author author) {
        String sql = "UPDATE author SET name = ? WHERE id = ?";
        jdbcTemplate.update(sql, author.getName(), author.getId());
    }

    //TODO
    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM author WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
