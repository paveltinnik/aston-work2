package org.paveltinnik.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.paveltinnik.dto.AuthorDTO;
import org.paveltinnik.dto.BookDTO;
import org.paveltinnik.service.AuthorService;
import org.paveltinnik.service.impl.AuthorServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

@WebServlet("/author")
public class AuthorServlet extends HttpServlet {

    private final AuthorService authorService = new AuthorServiceImpl();

    private final ObjectMapper objectMapper = new ObjectMapper();

    Logger logger = LoggerFactory.getLogger(AuthorServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if (id != null) {
            AuthorDTO author = authorService.findById(Long.parseLong(id));
            logger.info("Author found: " + author);
            resp.setContentType("application/json");
            resp.getWriter().write(objectMapper.writeValueAsString(author));
        } else {
            // Получение всех книг
            List<AuthorDTO> authorDTOList = authorService.findAll();
            resp.setContentType("application/json");
            resp.getWriter().write(objectMapper.writeValueAsString(authorDTOList));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AuthorDTO authorDTO = objectMapper.readValue(req.getReader(), AuthorDTO.class);
        authorService.save(authorDTO);
        resp.setStatus(HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AuthorDTO authorDTO = objectMapper.readValue(req.getReader(), AuthorDTO.class);
        authorService.update(authorDTO);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if (id != null) {
            authorService.delete(Long.parseLong(id));
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            resp.getWriter().write("ID not provided");
        }
    }
}