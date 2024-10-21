package org.paveltinnik.service;

import org.paveltinnik.dto.GenreDTO;

import java.util.List;

public interface GenreService {

    List<GenreDTO> findAll();

    GenreDTO findById(Long id);

    void save(GenreDTO genreDTO);

    void update(GenreDTO genreDTO);

    void delete(Long id);
}