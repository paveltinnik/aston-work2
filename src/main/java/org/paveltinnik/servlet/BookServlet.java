package org.paveltinnik.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.paveltinnik.dto.BookDTO;
import org.paveltinnik.repository.AuthorRepository;
import org.paveltinnik.repository.BookRepository;
import org.paveltinnik.repository.GenreRepository;
import org.paveltinnik.repository.impl.AuthorRepositoryImpl;
import org.paveltinnik.repository.impl.BookRepositoryImpl;
import org.paveltinnik.repository.impl.GenreRepositoryImpl;
import org.paveltinnik.service.BookService;
import org.paveltinnik.service.impl.BookServiceImpl;


import java.io.IOException;
import java.util.List;

@WebServlet("/books")
public class BookServlet extends HttpServlet {

    private BookService bookService;
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        BookRepository bookRepository = new BookRepositoryImpl();
        AuthorRepository authorRepository = new AuthorRepositoryImpl();
        GenreRepository genreRepository = new GenreRepositoryImpl();
        bookService = new BookServiceImpl(bookRepository, authorRepository, genreRepository);
        objectMapper = new ObjectMapper();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Создание новой книги
        BookDTO bookDTO = objectMapper.readValue(req.getInputStream(), BookDTO.class);
        bookService.save(bookDTO);
        resp.setStatus(HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("id");
        if (idParam != null) {
            // Получение книги по ID
            Long id = Long.parseLong(idParam);
            BookDTO bookDTO = bookService.findById(id);
            resp.setContentType("application/json");
            resp.getWriter().write(objectMapper.writeValueAsString(bookDTO));
        } else {
            // Получение всех книг
            List<BookDTO> books = bookService.findAll();
            resp.setContentType("application/json");
            resp.getWriter().write(objectMapper.writeValueAsString(books));
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Обновление книги
        BookDTO bookDTO = objectMapper.readValue(req.getInputStream(), BookDTO.class);
        bookService.update(bookDTO);
        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Удаление книги по ID
        String idParam = req.getParameter("id");
        if (idParam != null) {
            Long id = Long.parseLong(idParam);
            bookService.delete(id);
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID is required for deletion");
        }
    }
}