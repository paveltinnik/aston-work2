package org.paveltinnik.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.paveltinnik.dto.AuthorDTO;
import org.paveltinnik.repository.impl.AuthorRepositoryImpl;
import org.paveltinnik.service.impl.AuthorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/author")
public class AuthorController {

    Logger log = Logger.getLogger(AuthorController.class.getName());

    @Autowired
    private final AuthorServiceImpl authorService;

    public AuthorController(AuthorServiceImpl authorService) {
        this.authorService = authorService;
    }

    private final ObjectMapper objectMapper = new ObjectMapper();


    //    @GetMapping
//    public String getAuthors() throws JsonProcessingException {
//        List<AuthorDTO> authorDTOList = authorService.findAll();
//        log.info(objectMapper.writeValueAsString(authorDTOList));
//        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(authorDTOList);
//    }
    @GetMapping
    public ResponseEntity<List<AuthorDTO>> getAllAuthors() {
        List<AuthorDTO> authors = authorService.findAll();
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable("id") Long id) {
        AuthorDTO authorDTO = authorService.findById(id);
        if (authorDTO != null) {
            return new ResponseEntity<>(authorDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Void> createAuthor(@RequestBody AuthorDTO authorDTO) {
        authorService.save(authorDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Void> updateAuthor(@RequestBody AuthorDTO authorDTO) {
        authorService.update(authorDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable("id") Long id) {
        authorService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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