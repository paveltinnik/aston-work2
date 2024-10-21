package org.paveltinnik.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.paveltinnik.dto.AuthorDTO;
import org.paveltinnik.entity.Author;
import org.paveltinnik.mapper.AuthorMapper;
import org.paveltinnik.repository.impl.AuthorRepositoryImpl;
import org.paveltinnik.service.impl.AuthorServiceImpl;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthorServiceImplTest {

    @Mock
    private AuthorRepositoryImpl authorRepository;

    @Mock
    private AuthorMapper authorMapper;

    @InjectMocks
    private AuthorServiceImpl authorService;

    @BeforeEach
    void setUp() {
        // Инициализация моков
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        // Исходные данные
        AuthorDTO authorDTO = new AuthorDTO(1L, "John Doe");
        Author author = new Author(1L, "John Doe");

        // Настройка поведения маппера
        when(authorMapper.toAuthor(authorDTO)).thenReturn(author);

        // Выполнение метода
        authorService.save(authorDTO);

        // Проверка, что метод репозитория был вызван
        verify(authorRepository, times(1)).save(author);
    }

    @Test
    void testFindById() {
        // Исходные данные
        Long id = 1L;
        Author author = new Author(1L, "John Doe");
        AuthorDTO authorDTO = new AuthorDTO(1L, "John Doe");

        // Настройка поведения маппера и репозитория
        when(authorRepository.findById(id)).thenReturn(author);
        when(authorMapper.toAuthorDTO(author)).thenReturn(authorDTO);

        // Выполнение метода
        AuthorDTO result = authorService.findById(id);

        // Проверка результатов
        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        verify(authorRepository, times(1)).findById(id);
    }

    @Test
    void testFindAll() {
        // Исходные данные
        Author author1 = new Author(1L, "John Doe");
        Author author2 = new Author(2L, "Jane Doe");
        List<Author> authors = Arrays.asList(author1, author2);

        AuthorDTO authorDTO1 = new AuthorDTO(1L, "John Doe");
        AuthorDTO authorDTO2 = new AuthorDTO(2L, "Jane Doe");
        List<AuthorDTO> authorDTOs = Arrays.asList(authorDTO1, authorDTO2);

        // Настройка поведения маппера и репозитория
        when(authorRepository.findAll()).thenReturn(authors);
        when(authorMapper.toAuthorDTO(author1)).thenReturn(authorDTO1);
        when(authorMapper.toAuthorDTO(author2)).thenReturn(authorDTO2);

        // Выполнение метода
        List<AuthorDTO> result = authorService.findAll();

        // Проверка результатов
        assertEquals(2, result.size());
        verify(authorRepository, times(1)).findAll();
    }

    @Test
    void testUpdate() {
        // Исходные данные
        AuthorDTO authorDTO = new AuthorDTO(1L, "Updated Author");
        Author author = new Author(1L, "Updated Author");

        // Настройка поведения маппера
        when(authorMapper.toAuthor(authorDTO)).thenReturn(author);

        // Выполнение метода
        authorService.update(authorDTO);

        // Проверка, что метод репозитория был вызван
        verify(authorRepository, times(1)).update(author);
    }

    @Test
    void testDelete() {
        // Исходные данные
        Long id = 1L;

        // Выполнение метода
        authorService.delete(id);

        // Проверка, что метод репозитория был вызван
        verify(authorRepository, times(1)).delete(id);
    }
}
