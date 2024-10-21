package org.paveltinnik.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.paveltinnik.dto.GenreDTO;
import org.paveltinnik.entity.Book;
import org.paveltinnik.entity.Genre;
import org.paveltinnik.mapper.GenreMapper;
import org.paveltinnik.repository.impl.BookRepositoryImpl;
import org.paveltinnik.repository.impl.GenreRepositoryImpl;
import org.paveltinnik.service.impl.GenreServiceImpl;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GenreServiceImplTest {

    @Mock
    private GenreRepositoryImpl genreRepository;

    @Mock
    private BookRepositoryImpl bookRepository;

    @Mock
    private GenreMapper genreMapper;

    @InjectMocks
    private GenreServiceImpl genreService;

    private Genre genre;
    private GenreDTO genreDTO;
    private Book book;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        // Инициализация объектов
        book = new Book(1L, "Test Book", null);
        genre = new Genre(1L, "Fiction");
        Set<Book> books = new HashSet<>();
        books.add(book);
        genre.setBooks(books);

        genreDTO = new GenreDTO();
        genreDTO.setId(1L);
        genreDTO.setName("Fiction");
        genreDTO.setBookIds(Collections.singleton(1L));
    }

    @Test
    void testFindAllGenres() {
        // Подготовка mock поведения
        when(genreRepository.findAll()).thenReturn(List.of(genre));
        when(genreMapper.toGenreDTO(any(Genre.class))).thenReturn(genreDTO);

        // Выполнение теста
        List<GenreDTO> result = genreService.findAll();

        // Проверка
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Fiction", result.get(0).getName());

        // Проверка взаимодействий с mock объектами
        verify(genreRepository, times(1)).findAll();
        verify(genreMapper, times(1)).toGenreDTO(any(Genre.class));
    }

    @Test
    void testFindGenreById() {
        // Подготовка mock поведения
        when(genreRepository.findById(1L)).thenReturn(genre);
        when(genreMapper.toGenreDTO(any(Genre.class))).thenReturn(genreDTO);

        // Выполнение теста
        GenreDTO result = genreService.findById(1L);

        // Проверка
        assertNotNull(result);
        assertEquals("Fiction", result.getName());
        assertEquals(1L, result.getId());

        // Проверка взаимодействий с mock объектами
        verify(genreRepository, times(1)).findById(1L);
        verify(genreMapper, times(1)).toGenreDTO(any(Genre.class));
    }

    @Test
    void testSaveGenre() {
        // Подготовка mock поведения
        when(genreMapper.toGenre(any(GenreDTO.class))).thenReturn(genre);
        when(bookRepository.findById(1L)).thenReturn(book);

        // Выполнение теста
        genreService.save(genreDTO);

        // Проверка взаимодействий с mock объектами
        verify(genreRepository, times(1)).save(any(Genre.class));
        verify(genreMapper, times(1)).toGenre(any(GenreDTO.class));
    }

    @Test
    void testUpdateGenre() {
        // Подготовка mock поведения
        when(genreMapper.toGenre(any(GenreDTO.class))).thenReturn(genre);

        // Выполнение теста
        genreService.update(genreDTO);

        // Проверка взаимодействий с mock объектами
        verify(genreRepository, times(1)).update(any(Genre.class));
        verify(genreMapper, times(1)).toGenre(any(GenreDTO.class));
    }

    @Test
    void testDeleteGenre() {
        // Выполнение теста
        genreService.delete(1L);

        // Проверка взаимодействий с mock объектами
        verify(genreRepository, times(1)).delete(1L);
    }
}
