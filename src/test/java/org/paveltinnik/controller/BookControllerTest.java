package org.paveltinnik.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.paveltinnik.dto.BookDTO;
import org.paveltinnik.service.BookService;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class BookControllerTest {

    @InjectMocks
    private BookController bookController;

    @Mock
    private BookService bookService;

    private BookDTO book1;
    private BookDTO book2;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        book1 = new BookDTO(1L, "Book One", 1L, Stream.of(1L, 2L).collect(Collectors.toSet()));
        book2 = new BookDTO(2L, "Book Two", 1L, Stream.of(1L).collect(Collectors.toSet()));
    }

    @Test
    public void testGetAllBooks() {
        List<BookDTO> books = Arrays.asList(book1, book2);
        when(bookService.findAll()).thenReturn(books);

        List<BookDTO> result = bookController.getAllBooks().getBody();

        assert result != null; // Проверяем, что результат не равен null
        assert result.size() == 2; // Проверяем, что размер списка правильный
        assert result.get(0).getId().equals(1L); // Проверяем, что первый элемент правильный
        assert result.get(0).getTitle().equals("Book One");
        assert result.get(1).getId().equals(2L); // Проверяем, что второй элемент правильный
        assert result.get(1).getTitle().equals("Book Two");
    }

    @Test
    public void testGetBookById_Success() {
        when(bookService.findById(1L)).thenReturn(book1);

        BookDTO result = bookController.getBookById(1L).getBody();

        assert result != null; // Проверяем, что результат не равен null
        assert result.getId().equals(1L); // Проверяем, что ID книги правильный
        assert result.getTitle().equals("Book One"); // Проверяем, что название книги правильное
    }

    @Test
    public void testGetBookById_NotFound() {
        when(bookService.findById(anyLong())).thenReturn(null);

        var response = bookController.getBookById(999L);

        assert response.getStatusCodeValue() == 404; // Проверяем статус NOT_FOUND
    }

    @Test
    public void testCreateBook() {
        BookDTO newBook = new BookDTO(null, "New Book", 1L, Stream.of(1L, 2L).collect(Collectors.toSet()));

        bookController.createBook(newBook);

        verify(bookService, times(1)).save(newBook); // Проверяем, что метод save был вызван один раз
    }

    @Test
    public void testUpdateBook() {
        BookDTO updatedBook = new BookDTO(1L, "Updated Book", 1L, Stream.of(1L).collect(Collectors.toSet()));

        bookController.updateBook(updatedBook);

        verify(bookService, times(1)).update(updatedBook); // Проверяем, что метод update был вызван один раз
    }

    @Test
    public void testDeleteBook() {
        bookController.deleteBook(1L);

        verify(bookService, times(1)).delete(1L); // Проверяем, что метод delete был вызван один раз
    }
}
