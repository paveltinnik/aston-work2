package org.paveltinnik.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.paveltinnik.dto.GenreDTO;
import org.paveltinnik.service.GenreService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class GenreControllerTest {

    @InjectMocks
    private GenreController genreController;

    @Mock
    private GenreService genreService;

    private GenreDTO genre1;
    private GenreDTO genre2;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        genre1 = new GenreDTO(1L, "Fiction", Stream.of(1L, 2L).collect(Collectors.toSet()));
        genre2 = new GenreDTO(2L, "Non-Fiction", Stream.of(1L, 2L).collect(Collectors.toSet()));
    }

    @Test
    public void testGetAllGenres() {
        List<GenreDTO> genres = Arrays.asList(genre1, genre2);
        when(genreService.findAll()).thenReturn(genres);

        List<GenreDTO> result = genreController.getAllGenres().getBody();

        assert result != null; // Ensure the result is not null
        assert result.size() == 2; // Check that the size of the list is correct
        assert result.get(0).getId().equals(1L); // Verify the first genre
        assert result.get(0).getName().equals("Fiction");
        assert result.get(1).getId().equals(2L); // Verify the second genre
        assert result.get(1).getName().equals("Non-Fiction");
    }

    @Test
    public void testGetGenreById_Success() {
        when(genreService.findById(1L)).thenReturn(genre1);

        GenreDTO result = genreController.getGenreById(1L).getBody();

        assert result != null; // Ensure the result is not null
        assert result.getId().equals(1L); // Verify the genre ID
        assert result.getName().equals("Fiction"); // Verify the genre name
    }

    @Test
    public void testGetGenreById_NotFound() {
        when(genreService.findById(anyLong())).thenReturn(null);

        var response = genreController.getGenreById(999L);

        assert response.getStatusCodeValue() == 404; // Check for NOT_FOUND status
    }

    @Test
    public void testCreateGenre() {
        GenreDTO newGenre = new GenreDTO(null, "Fantasy", Stream.of(1L, 2L).collect(Collectors.toSet()));

        genreController.createGenre(newGenre);

        verify(genreService, times(1)).save(newGenre); // Verify that save was called once
    }

    @Test
    public void testUpdateGenre() {
        GenreDTO updatedGenre = new GenreDTO(1L, "Updated Fiction", Stream.of(1L, 2L).collect(Collectors.toSet()));

        genreController.updateGenre(updatedGenre);

        verify(genreService, times(1)).update(updatedGenre); // Verify that update was called once
    }

    @Test
    public void testDeleteGenre() {
        genreController.deleteGenre(1L);

        verify(genreService, times(1)).delete(1L); // Verify that delete was called once
    }
}
