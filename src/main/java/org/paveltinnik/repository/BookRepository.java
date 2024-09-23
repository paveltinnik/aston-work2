package org.paveltinnik.repository;

import org.paveltinnik.model.Book;

import java.sql.SQLException;
import java.util.List;

public interface BookRepository {
    void save(Book book) throws SQLException;

    Book findById(Long id) throws SQLException;

    List<Book> findAll() throws SQLException;

    void update(Book book) throws SQLException;

    void delete(Long id) throws SQLException;
}
