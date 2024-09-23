package org.paveltinnik.service;

import org.paveltinnik.dto.AuthorDTO;

import java.sql.SQLException;
import java.util.List;

public interface AuthorService {
    void save(AuthorDTO authorDTO);

    AuthorDTO findById(Long id);

    List<AuthorDTO> findAll();

    void update(AuthorDTO authorDTO);

    void delete(Long id);
}