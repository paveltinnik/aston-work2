package org.paveltinnik.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.paveltinnik.dto.AuthorDTO;
import org.paveltinnik.service.AuthorService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthorServletImplTest {

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private AuthorServlet authorServlet;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
    }

    @Test
    void doGet_WithId_ShouldReturnAuthor() throws Exception {
        // Arrange
        AuthorDTO authorDTO = new AuthorDTO(1L, "Test Author");
        when(authorService.findById(1L)).thenReturn(authorDTO);

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("id")).thenReturn("1");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(outputStream);
        when(response.getWriter()).thenReturn(writer);

        // Act
        authorServlet.doGet(request, response);

        // Assert
        writer.flush();
        assertEquals("{\"id\":1,\"name\":\"Test Author\"}", outputStream.toString().trim());
        verify(authorService, times(1)).findById(1L);
    }

    @Test
    void doGet_WithoutId_ShouldReturnAllAuthors() throws Exception {
        // Arrange
        AuthorDTO author1 = new AuthorDTO(1L, "Author 1");
        AuthorDTO author2 = new AuthorDTO(2L, "Author 2");
        List<AuthorDTO> authors = Arrays.asList(author1, author2);
        when(authorService.findAll()).thenReturn(authors);

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(outputStream);
        when(response.getWriter()).thenReturn(writer);

        // Act
        authorServlet.doGet(request, response);

        // Assert
        writer.flush();
        assertEquals("[{\"id\":1,\"name\":\"Author 1\"},{\"id\":2,\"name\":\"Author 2\"}]", outputStream.toString().trim());
        verify(authorService, times(1)).findAll();
    }

    @Test
    void doPost_ShouldSaveAuthor() throws Exception {
        // Arrange
        AuthorDTO authorDTO = new AuthorDTO(null, "New Author");
        String json = objectMapper.writeValueAsString(authorDTO);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getReader()).thenReturn(new java.io.BufferedReader(new java.io.InputStreamReader(new ByteArrayInputStream(json.getBytes()))));

        // Act
        authorServlet.doPost(request, response);

        // Assert
        verify(authorService, times(1)).save(authorDTO);
        assertEquals(HttpServletResponse.SC_CREATED, response.getStatus());
    }

    @Test
    void doDelete_ShouldDeleteAuthor() throws Exception {
        // Arrange
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("id")).thenReturn("1");

        // Act
        authorServlet.doDelete(request, response);

        // Assert
        verify(authorService, times(1)).delete(1L);
        assertEquals(HttpServletResponse.SC_NO_CONTENT, response.getStatus());
    }
}
