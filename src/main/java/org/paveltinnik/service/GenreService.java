package org.paveltinnik.service;

import org.paveltinnik.dto.GenreDTO;

import java.util.List;

public interface GenreService {
    void save(GenreDTO genreDTO);

    GenreDTO findById(Long id);

    List<GenreDTO> findAll();

    void update(GenreDTO genreDTO);

    void delete(Long id);
}