package org.paveltinnik.service.impl;

import org.paveltinnik.dto.GenreDTO;
import org.paveltinnik.entity.Book;
import org.paveltinnik.entity.Genre;
import org.paveltinnik.mapper.GenreMapper;
import org.paveltinnik.repository.GenreRepository;
import org.paveltinnik.repository.impl.BookRepositoryImpl;
import org.paveltinnik.repository.impl.GenreRepositoryImpl;
import org.paveltinnik.service.GenreService;
import org.paveltinnik.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class GenreServiceImpl implements GenreService {

    Logger logger = Logger.getLogger(GenreRepositoryImpl.class.getName());

    private final BookRepositoryImpl bookRepository;
    private final GenreRepositoryImpl genreRepository;
    private final GenreMapper genreMapper;

    @Autowired
    public GenreServiceImpl(BookRepositoryImpl bookRepository, GenreRepositoryImpl genreRepository, GenreMapper genreMapper) {
        this.bookRepository = bookRepository;
        this.genreRepository = genreRepository;
        this.genreMapper = genreMapper;
    }

    @Override
    public void save(GenreDTO genreDTO) {

        Genre genre = genreMapper.toGenre(genreDTO);

        Set<Book> genreBooks = new HashSet<>();
        for (Long bookId : genreDTO.getBookIds()) {
            genreBooks.add(bookRepository.findById(bookId));
        }

        genre.setBooks(genreBooks);
        logger.warning(genre.toString());

        genreRepository.save(genre);

    }

    @Override
    public GenreDTO findById(Long id) {
        return genreMapper.toGenreDTO(genreRepository.findById(id));
    }

    @Override
    public List<GenreDTO> findAll() {
        return genreRepository.findAll().stream()
                .map(genreMapper::toGenreDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void update(GenreDTO genreDTO) {
        genreRepository.update(genreMapper.toGenre(genreDTO));
    }

    @Override
    public void delete(Long id) {
        genreRepository.delete(id);
    }
}