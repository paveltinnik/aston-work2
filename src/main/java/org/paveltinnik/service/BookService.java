package org.paveltinnik.service;

import org.paveltinnik.dto.BookDTO;

import java.util.List;

public interface BookService {
    void save(BookDTO bookDTO);

    BookDTO findById(Long id);

    List<BookDTO> findAll();

    void update(BookDTO bookDTO);

    void delete(Long id);
}