package org.paveltinnik.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.paveltinnik.dto.AuthorDTO;
import org.paveltinnik.service.impl.AuthorServiceImpl;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class AuthorControllerTest {

    @InjectMocks
    private AuthorController authorController;

    @Mock
    private AuthorServiceImpl authorService;

    private AuthorDTO author1;
    private AuthorDTO author2;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        author1 = new AuthorDTO(1L, "Author One");
        author2 = new AuthorDTO(2L, "Author Two");
    }

    @Test
    public void testGetAllAuthors() {
        List<AuthorDTO> authors = Arrays.asList(author1, author2);
        when(authorService.findAll()).thenReturn(authors);

        List<AuthorDTO> result = authorController.getAllAuthors().getBody();

        assert result != null; // Checking that the result is not null
        assert result.size() == 2; // Check if the list size is correct
        assert result.get(0).getId().equals(1L); // Check if the first author is correct
        assert result.get(0).getName().equals("Author One");
        assert result.get(1).getId().equals(2L); // Check if the second author is correct
        assert result.get(1).getName().equals("Author Two");
    }

    @Test
    public void testGetAuthorById_Success() {
        when(authorService.findById(1L)).thenReturn(author1);

        AuthorDTO result = authorController.getAuthorById(1L).getBody();

        assert result != null; // Checking that the result is not null
        assert result.getId().equals(1L); // Check if the author ID is correct
        assert result.getName().equals("Author One"); // Check if the author name is correct
    }

    @Test
    public void testGetAuthorById_NotFound() {
        when(authorService.findById(anyLong())).thenReturn(null);

        var response = authorController.getAuthorById(999L);

        assert response.getStatusCodeValue() == 404; // Check for NOT_FOUND status
    }

    @Test
    public void testCreateAuthor() {
        AuthorDTO newAuthor = new AuthorDTO(null, "New Author");

        authorController.createAuthor(newAuthor);

        verify(authorService, times(1)).save(newAuthor); // Verify that save was called once
    }

    @Test
    public void testUpdateAuthor() {
        AuthorDTO updatedAuthor = new AuthorDTO(1L, "Updated Author");

        authorController.updateAuthor(updatedAuthor);

        verify(authorService, times(1)).update(updatedAuthor); // Verify that update was called once
    }

    @Test
    public void testDeleteAuthor() {
        authorController.deleteAuthor(1L);

        verify(authorService, times(1)).delete(1L); // Verify that delete was called once
    }
}
