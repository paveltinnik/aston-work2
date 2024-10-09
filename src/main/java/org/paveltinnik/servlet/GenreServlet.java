package org.paveltinnik.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.paveltinnik.dto.GenreDTO;
import org.paveltinnik.repository.impl.GenreRepositoryImpl;
import org.paveltinnik.service.GenreService;
import org.paveltinnik.service.impl.GenreServiceImpl;

import java.io.IOException;
import java.util.List;

@WebServlet("/genres")
public class GenreServlet extends HttpServlet {

    private GenreService genreService;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void init() throws ServletException {
        genreService = new GenreServiceImpl(new GenreRepositoryImpl());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam != null) {
            // Get a single genre by ID
            Long id = Long.parseLong(idParam);
            GenreDTO genreDTO = genreService.findById(id);
            response.setContentType("application/json");
            objectMapper.writeValue(response.getWriter(), genreDTO);
        } else {
            // Get all genres
            List<GenreDTO> genres = genreService.findAll();
            response.setContentType("application/json");
            objectMapper.writeValue(response.getWriter(), genres);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Create a new genre
        GenreDTO genreDTO = objectMapper.readValue(request.getReader(), GenreDTO.class);
        genreService.save(genreDTO);
        response.setStatus(HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Update an existing genre
        GenreDTO genreDTO = objectMapper.readValue(request.getReader(), GenreDTO.class);
        genreService.update(genreDTO);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam != null) {
            Long id = Long.parseLong(idParam);
            genreService.delete(id);
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID parameter is missing");
        }
    }
}