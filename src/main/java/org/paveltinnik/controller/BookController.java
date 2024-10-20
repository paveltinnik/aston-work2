package org.paveltinnik.controller;

import org.paveltinnik.dto.BookDTO;
import org.paveltinnik.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        List<BookDTO> books = bookService.findAll();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable("id") Long id) {
        BookDTO bookDTO = bookService.findById(id);
        if (bookDTO != null) {
            return new ResponseEntity<>(bookDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Void> createBook(@RequestBody BookDTO bookDTO) {
        bookService.save(bookDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<Void> updateBook(@RequestBody BookDTO bookDTO) {
        bookService.update(bookDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable("id") Long id) {
        bookService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

//
//@WebServlet("/book")
//public class BookServlet extends HttpServlet {
//
//    private BookService bookService;
//    private ObjectMapper objectMapper;
//
//    @Override
//    public void init() throws ServletException {
//        BookRepository bookRepository = new BookRepositoryImpl();
//        AuthorRepository authorRepository = new AuthorRepositoryImpl();
//        GenreRepository genreRepository = new GenreRepositoryImpl();
//        bookService = new BookServiceImpl(bookRepository, authorRepository, genreRepository);
//        objectMapper = new ObjectMapper();
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        // Создание новой книги
//        BookDTO bookDTO = objectMapper.readValue(req.getInputStream(), BookDTO.class);
//        bookService.save(bookDTO);
//        resp.setStatus(HttpServletResponse.SC_CREATED);
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String idParam = req.getParameter("id");
//        if (idParam != null) {
//            // Получение книги по ID
//            Long id = Long.parseLong(idParam);
//            BookDTO bookDTO = bookService.findById(id);
//            resp.setContentType("application/json");
//            resp.getWriter().write(objectMapper.writeValueAsString(bookDTO));
//        } else {
//            // Получение всех книг
//            List<BookDTO> books = bookService.findAll();
//            resp.setContentType("application/json");
//            resp.getWriter().write(objectMapper.writeValueAsString(books));
//        }
//    }
//
//    @Override
//    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        // Обновление книги
//        BookDTO bookDTO = objectMapper.readValue(req.getInputStream(), BookDTO.class);
//        bookService.update(bookDTO);
//        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
//    }
//
//    @Override
//    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        // Удаление книги по ID
//        String idParam = req.getParameter("id");
//        if (idParam != null) {
//            Long id = Long.parseLong(idParam);
//            bookService.delete(id);
//            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
//        } else {
//            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID is required for deletion");
//        }
//    }
//}