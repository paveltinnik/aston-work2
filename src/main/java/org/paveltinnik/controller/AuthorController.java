package org.paveltinnik.controller;

import org.paveltinnik.dto.AuthorDTO;
import org.paveltinnik.service.impl.AuthorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    private final AuthorServiceImpl authorService;

    public AuthorController(AuthorServiceImpl authorService) {
        this.authorService = authorService;
    }

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
}