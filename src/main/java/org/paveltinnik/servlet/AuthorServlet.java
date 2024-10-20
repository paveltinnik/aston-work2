package org.paveltinnik.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.paveltinnik.dto.AuthorDTO;
import org.paveltinnik.service.AuthorService;
import org.paveltinnik.service.impl.AuthorServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/author")
public class AuthorServlet {

    @Autowired
    private final AuthorServiceImpl authorService;

    public AuthorServlet(AuthorServiceImpl authorService) {
        this.authorService = authorService;
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    Logger logger = LoggerFactory.getLogger(AuthorServlet.class);

    @GetMapping
    public String getAuthors() {
        List<AuthorDTO> authorDTOList = authorService.findAll();
        return authorDTOList.toString();
    }

//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String id = req.getParameter("id");
//        if (id != null) {
//            AuthorDTO author = authorService.findById(Long.parseLong(id));
//            logger.info("Author found: " + author);
//            resp.setContentType("application/json");
//            resp.getWriter().write(objectMapper.writeValueAsString(author));
//        } else {
//            // Получение всех книг
//            List<AuthorDTO> authorDTOList = authorService.findAll();
//            resp.setContentType("application/json");
//            resp.getWriter().write(objectMapper.writeValueAsString(authorDTOList));
//        }
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        AuthorDTO authorDTO = objectMapper.readValue(req.getReader(), AuthorDTO.class);
//        authorService.save(authorDTO);
//        resp.setStatus(HttpServletResponse.SC_CREATED);
//    }
//
//    @Override
//    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        AuthorDTO authorDTO = objectMapper.readValue(req.getReader(), AuthorDTO.class);
//        authorService.update(authorDTO);
//        resp.setStatus(HttpServletResponse.SC_OK);
//    }
//
//    @Override
//    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String id = req.getParameter("id");
//        if (id != null) {
//            authorService.delete(Long.parseLong(id));
//            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
//        } else {
//            resp.getWriter().write("ID not provided");
//        }
//    }
}