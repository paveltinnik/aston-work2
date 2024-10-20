package org.paveltinnik.service.impl;

import org.mapstruct.factory.Mappers;
import org.paveltinnik.dto.BookDTO;
import org.paveltinnik.entity.Author;
import org.paveltinnik.entity.Book;
import org.paveltinnik.entity.Genre;
import org.paveltinnik.mapper.BookMapper;
import org.paveltinnik.mapper.Mapper;
import org.paveltinnik.repository.AuthorRepository;
import org.paveltinnik.repository.BookRepository;
import org.paveltinnik.repository.GenreRepository;
import org.paveltinnik.repository.impl.AuthorRepositoryImpl;
import org.paveltinnik.repository.impl.BookRepositoryImpl;
import org.paveltinnik.repository.impl.GenreRepositoryImpl;
import org.paveltinnik.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
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

//
//    private BookMapper bookMapper = Mappers.getMapper(BookMapper.class);
//
//    private final BookRepository bookRepository;
//    private final AuthorRepository authorRepository;
//    private final GenreRepository genreRepository;
//
//    @Autowired
//    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository) {
//        this.bookRepository = bookRepository;
//        this.authorRepository = authorRepository;
//        this.genreRepository = genreRepository;
//    }
//
//    @Override
//    public void save(BookDTO bookDTO) {
//        try {
//            // Получаем автора по ID
//            Author author = authorRepository.findById(bookDTO.getAuthorId());
//            if (author == null) {
//                throw new RuntimeException("Author not found");
//            }
//
//            // Преобразуем DTO в сущность Book
//            Book book = Mapper.toBook(bookDTO, author);
//
//            // Получаем жанры по их ID
//            Set<Genre> genres = bookDTO.getGenreIds().stream()
//                    .map(genreId -> {
//                        try {
//                            return genreRepository.findById(genreId);
//                        } catch (SQLException e) {
//                            throw new RuntimeException("Failed to find genre", e);
//                        }
//                    })
//                    .collect(Collectors.toSet());
//
//            // Устанавливаем жанры в книгу
//            book.setGenres(genres);
//
//            // Сохраняем книгу в репозиторий
//            bookRepository.save(book);
//        } catch (SQLException e) {
//            throw new RuntimeException("Failed to save book", e);
//        }
//    }
//
//    @Override
//    public BookDTO findById(Long id) {
//        try {
//            // Получаем книгу по ID
//            Book book = bookRepository.findById(id);
//            if (book == null) {
//                throw new RuntimeException("Book not found");
//            }
//
//            // Преобразуем сущность в DTO
//            return Mapper.toBookDTO(book);
//        } catch (SQLException e) {
//            throw new RuntimeException("Failed to find book by id", e);
//        }
//    }
//
//    @Override
//    public List<BookDTO> findAll() {
//        try {
//            // Получаем все книги
//            List<Book> books = bookRepository.findAll();
//
//            // Преобразуем список книг в список DTO
//            return books.stream()
//                    .map(Mapper::toBookDTO)
//                    .collect(Collectors.toList());
//        } catch (SQLException e) {
//            throw new RuntimeException("Failed to retrieve all books", e);
//        }
//    }
//
//    @Override
//    public void update(BookDTO bookDTO) {
//        try {
//            // Находим автора по ID
//            Author author = authorRepository.findById(bookDTO.getAuthorId());
//            if (author == null) {
//                throw new RuntimeException("Author not found");
//            }
//
//            // Преобразуем DTO в сущность Book
//            Book book = Mapper.toBook(bookDTO, author);
//
//            // Получаем жанры по их ID
//            Set<Genre> genres = bookDTO.getGenreIds().stream()
//                    .map(genreId -> {
//                        try {
//                            return genreRepository.findById(genreId);
//                        } catch (SQLException e) {
//                            throw new RuntimeException("Failed to find genre", e);
//                        }
//                    })
//                    .collect(Collectors.toSet());
//
//            // Устанавливаем жанры в книгу
//            book.setGenres(genres);
//
//            // Обновляем книгу в репозитории
//            bookRepository.update(book);
//        } catch (SQLException e) {
//            throw new RuntimeException("Failed to update book", e);
//        }
//    }
//
//    @Override
//    public void delete(Long id) {
//        try {
//            // Удаляем книгу по ID
//            bookRepository.delete(id);
//        } catch (SQLException e) {
//            throw new RuntimeException("Failed to delete book", e);
//        }
//    }
}
