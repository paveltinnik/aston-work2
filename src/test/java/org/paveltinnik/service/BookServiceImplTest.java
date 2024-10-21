package org.paveltinnik.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.paveltinnik.dto.BookDTO;
import org.paveltinnik.entity.Author;
import org.paveltinnik.entity.Book;
import org.paveltinnik.entity.Genre;
import org.paveltinnik.mapper.BookMapper;
import org.paveltinnik.repository.impl.AuthorRepositoryImpl;
import org.paveltinnik.repository.impl.BookRepositoryImpl;
import org.paveltinnik.repository.impl.GenreRepositoryImpl;
import org.paveltinnik.service.impl.BookServiceImpl;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BookServiceImplTest {

    @Mock
    private BookRepositoryImpl bookRepository;

    @Mock
    private AuthorRepositoryImpl authorRepository;

    @Mock
    private GenreRepositoryImpl genreRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    private BookDTO bookDTO;
    private Book book;
    private Author author;
    private Genre genre;
    private Set<Genre> genres;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Создаем фиктивные данные
        author = new Author(1L, "Author Name");
        genre = new Genre(1L, "Genre Name");
        genres = new HashSet<>(Collections.singletonList(genre));

        book = new Book(1L, "Test Book", author);
        book.setGenres(genres);

        bookDTO = new BookDTO();
        bookDTO.setId(1L);
        bookDTO.setTitle("Test Book");
        bookDTO.setAuthorId(1L);
        bookDTO.setGenreIds(genres.stream().map(Genre::getId).collect(Collectors.toSet()));
    }

    @Test
    void testFindAll() {
        // Мокируем поведение bookRepository
        when(bookRepository.findAll()).thenReturn(List.of(book));
        when(bookMapper.toBookDTO(any(Book.class))).thenReturn(bookDTO);

        List<BookDTO> books = bookService.findAll();

        // Проверяем результат
        assertNotNull(books);
        assertEquals(1, books.size());
        assertEquals("Test Book", books.get(0).getTitle());

        // Проверяем взаимодействие с моками
        verify(bookRepository, times(1)).findAll();
        verify(bookMapper, times(1)).toBookDTO(any(Book.class));
    }

    @Test
    void testFindById() {
        // Мокируем поведение bookRepository и bookMapper
        when(bookRepository.findById(1L)).thenReturn(book);
        when(bookMapper.toBookDTO(book)).thenReturn(bookDTO);

        BookDTO foundBook = bookService.findById(1L);

        // Проверяем результат
        assertNotNull(foundBook);
        assertEquals("Test Book", foundBook.getTitle());

        // Проверяем взаимодействие с моками
        verify(bookRepository, times(1)).findById(1L);
        verify(bookMapper, times(1)).toBookDTO(book);
    }

    @Test
    void testSave() {
        // Мокируем поведение репозиториев
        when(authorRepository.findById(1L)).thenReturn(author);
        when(genreRepository.findById(1L)).thenReturn(genre);
        when(bookMapper.toBook(any(BookDTO.class), any(Author.class))).thenReturn(book);

        // Вызываем метод сохранения
        bookService.save(bookDTO);

        // Проверяем взаимодействие с моками
        verify(authorRepository, times(1)).findById(1L);
        verify(genreRepository, times(1)).findById(1L);
        verify(bookMapper, times(1)).toBook(any(BookDTO.class), any(Author.class));
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void testUpdate() {
        // Мокируем поведение репозиториев
        when(authorRepository.findById(1L)).thenReturn(author);
        when(genreRepository.findById(1L)).thenReturn(genre);
        when(bookMapper.toBook(any(BookDTO.class), any(Author.class))).thenReturn(book);

        // Вызываем метод обновления
        bookService.update(bookDTO);

        // Проверяем взаимодействие с моками
        verify(authorRepository, times(1)).findById(1L);
        verify(genreRepository, times(1)).findById(1L);
        verify(bookMapper, times(1)).toBook(any(BookDTO.class), any(Author.class));
        verify(bookRepository, times(1)).update(any(Book.class));
    }

    @Test
    void testDelete() {
        // Вызываем метод удаления
        bookService.delete(1L);

        // Проверяем взаимодействие с моками
        verify(bookRepository, times(1)).delete(1L);
    }
}
