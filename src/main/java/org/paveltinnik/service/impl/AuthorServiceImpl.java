package org.paveltinnik.service.impl;

import org.paveltinnik.dto.AuthorDTO;
import org.paveltinnik.mapper.Mapper;
import org.paveltinnik.model.Author;
import org.paveltinnik.repository.AuthorRepository;
import org.paveltinnik.repository.impl.AuthorRepositoryImpl;
import org.paveltinnik.service.AuthorService;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository = new AuthorRepositoryImpl();

    @Override
    public void save(AuthorDTO authorDTO) {
        try {
            Author author = Mapper.toAuthor(authorDTO);
            authorRepository.save(author);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public AuthorDTO findById(Long id){
        try {
            return Mapper.toAuthorDTO(authorRepository.findById(id));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<AuthorDTO> findAll() {
        try {
            return authorRepository.findAll().stream()
                    .map(Mapper::toAuthorDTO)
                    .collect(Collectors.toList());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void update(AuthorDTO authorDTO) {
        try {
            authorRepository.update(Mapper.toAuthor(authorDTO));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        try {
            authorRepository.delete(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
