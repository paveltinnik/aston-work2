package org.paveltinnik.service.impl;

import org.paveltinnik.dto.GenreDTO;
import org.paveltinnik.model.Genre;
import org.paveltinnik.repository.GenreRepository;
import org.paveltinnik.service.GenreService;
import org.paveltinnik.mapper.Mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public void save(GenreDTO genreDTO) {
        try {
            // Преобразуем DTO в сущность Genre
            Genre genre = Mapper.toGenre(genreDTO);

            // Сохраняем жанр в репозиторий
            genreRepository.save(genre);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save genre", e);
        }
    }

    @Override
    public GenreDTO findById(Long id) {
        try {
            // Получаем жанр по ID
            Genre genre = genreRepository.findById(id);
            if (genre == null) {
                throw new RuntimeException("Genre not found");
            }

            // Преобразуем сущность в DTO
            return Mapper.toGenreDTO(genre);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find genre by id", e);
        }
    }

    @Override
    public List<GenreDTO> findAll() {
        try {
            // Получаем все жанры из базы данных
            List<Genre> genres = genreRepository.findAll();

            // Преобразуем список жанров в список DTO
            return genres.stream()
                    .map(Mapper::toGenreDTO)
                    .collect(Collectors.toList());
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve all genres", e);
        }
    }

    @Override
    public void update(GenreDTO genreDTO) {
        try {
            // Преобразуем DTO в сущность Genre
            Genre genre = Mapper.toGenre(genreDTO);

            // Обновляем жанр в репозитории
            genreRepository.update(genre);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update genre", e);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            // Удаляем жанр по ID
            genreRepository.delete(id);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete genre", e);
        }
    }
}
