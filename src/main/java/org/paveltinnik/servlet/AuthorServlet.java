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
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.Servlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/author")
public class AuthorServlet extends HttpServlet {

    private final AuthorService authorService = new AuthorServiceImpl();

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if (id != null) {
            AuthorDTO author = authorService.findById(Long.parseLong(id));
            System.out.println(author);
            resp.setContentType("application/json");
            resp.getWriter().write(objectMapper.writeValueAsString(author));
//            getServletContext().getRequestDispatcher("/author.jsp").forward(req, resp);
        } else {
            resp.getWriter().write("ID not provided");
        }
    }

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