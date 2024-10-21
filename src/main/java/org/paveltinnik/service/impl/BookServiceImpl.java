package org.paveltinnik.service.impl;

import org.paveltinnik.dto.BookDTO;
import org.paveltinnik.entity.Author;
import org.paveltinnik.entity.Book;
import org.paveltinnik.entity.Genre;
import org.paveltinnik.mapper.BookMapper;
import org.paveltinnik.repository.impl.AuthorRepositoryImpl;
import org.paveltinnik.repository.impl.BookRepositoryImpl;
import org.paveltinnik.repository.impl.GenreRepositoryImpl;
import org.paveltinnik.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepositoryImpl bookRepository;
    private final AuthorRepositoryImpl authorRepository;
    private final GenreRepositoryImpl genreRepository;

    private final BookMapper bookMapper;

    @Autowired
    public BookServiceImpl(BookRepositoryImpl bookRepository, AuthorRepositoryImpl authorRepository, GenreRepositoryImpl genreRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    public List<BookDTO> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toBookDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BookDTO findById(Long id) {
        return bookMapper.toBookDTO(bookRepository.findById(id));
    }

    @Override
    public void save(BookDTO bookDTO) {
        Author author = authorRepository.findById(bookDTO.getAuthorId());
        Book book = bookMapper.toBook(bookDTO, author);

        // Получаем жанры по их ID
        Set<Genre> genres = bookDTO.getGenreIds().stream()
                .map(genreRepository::findById)
                .collect(Collectors.toSet());

        // Устанавливаем жанры в книгу
        book.setGenres(genres);
        bookRepository.save(book);
    }

    @Override
    public void update(BookDTO bookDTO) {
        Author author = authorRepository.findById(bookDTO.getAuthorId());
        Book book = bookMapper.toBook(bookDTO, author);

        // Получаем жанры по их ID
        Set<Genre> genres = bookDTO.getGenreIds().stream()
                .map(genreRepository::findById)
                .collect(Collectors.toSet());

        // Устанавливаем жанры в книгу
        book.setGenres(genres);
        bookRepository.update(book);
    }

    @Override
    public void delete(Long id) {
        bookRepository.delete(id);
    }
}
