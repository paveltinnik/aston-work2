package org.paveltinnik.repository;

import org.paveltinnik.model.Genre;

import java.sql.SQLException;
import java.util.List;

public interface GenreRepository {
    void save(Genre genre) throws SQLException;

    Genre findById(Long id) throws SQLException;

    List<Genre> findAll() throws SQLException;

    void update(Genre genre) throws SQLException;

    void delete(Long id) throws SQLException;
}
