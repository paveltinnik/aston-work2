package org.paveltinnik.service.impl;

import org.paveltinnik.dto.AuthorDTO;
import org.paveltinnik.mapper.AuthorMapper;
import org.paveltinnik.mapper.Mapper;
import org.paveltinnik.model.Author;
import org.paveltinnik.repository.AuthorRepository;
import org.paveltinnik.repository.impl.AuthorRepositoryImpl;
import org.paveltinnik.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private final AuthorRepositoryImpl authorRepository;

    public AuthorServiceImpl(AuthorRepositoryImpl authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public void save(AuthorDTO authorDTO) {
//        try {
//            Author author = Mapper.toAuthor(authorDTO);
//            authorRepository.save(author);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public AuthorDTO findById(Long id){
//        try {
//            return Mapper.toAuthorDTO(authorRepository.findById(id));
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return null;
//        }
        return null;
    }

    @Override
    public List<AuthorDTO> findAll() {
        return authorRepository.findAll().stream()
                .map(AuthorMapper.INSTANCE::toAuthorDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void update(AuthorDTO authorDTO) {
//        try {
//            authorRepository.update(Mapper.toAuthor(authorDTO));
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
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
