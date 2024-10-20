package org.paveltinnik.controller;

import org.paveltinnik.dto.GenreDTO;
import org.paveltinnik.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/genre")
public class GenreController {

    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @PostMapping
    public ResponseEntity<Void> createGenre(@RequestBody GenreDTO genreDTO) {
        genreService.save(genreDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenreDTO> getGenreById(@PathVariable("id") Long id) {
        GenreDTO genreDTO = genreService.findById(id);
        if (genreDTO != null) {
            return new ResponseEntity<>(genreDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<GenreDTO>> getAllGenres() {
        List<GenreDTO> genres = genreService.findAll();
        return new ResponseEntity<>(genres, HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<Void> updateGenre(@RequestBody GenreDTO genreDTO) {
        genreService.update(genreDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGenre(@PathVariable("id") Long id) {
        genreService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

//    private GenreService genreService;
//    private ObjectMapper objectMapper = new ObjectMapper();
//
//    @Override
//    public void init() throws ServletException {
//        genreService = new GenreServiceImpl(new GenreRepositoryImpl());
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String idParam = request.getParameter("id");
//        if (idParam != null) {
//            // Get a single genre by ID
//            Long id = Long.parseLong(idParam);
//            GenreDTO genreDTO = genreService.findById(id);
//            response.setContentType("application/json");
//            objectMapper.writeValue(response.getWriter(), genreDTO);
//        } else {
//            // Get all genres
//            List<GenreDTO> genres = genreService.findAll();
//            response.setContentType("application/json");
//            objectMapper.writeValue(response.getWriter(), genres);
//        }
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        // Create a new genre
//        GenreDTO genreDTO = objectMapper.readValue(request.getReader(), GenreDTO.class);
//        genreService.save(genreDTO);
//        response.setStatus(HttpServletResponse.SC_CREATED);
//    }
//
//    @Override
//    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        // Update an existing genre
//        GenreDTO genreDTO = objectMapper.readValue(request.getReader(), GenreDTO.class);
//        genreService.update(genreDTO);
//        response.setStatus(HttpServletResponse.SC_OK);
//    }
//
//    @Override
//    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String idParam = request.getParameter("id");
//        if (idParam != null) {
//            Long id = Long.parseLong(idParam);
//            genreService.delete(id);
//            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
//        } else {
//            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID parameter is missing");
//        }
//    }
}