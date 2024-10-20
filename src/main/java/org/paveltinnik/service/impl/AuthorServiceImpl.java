package org.paveltinnik.service.impl;

import org.paveltinnik.dto.AuthorDTO;
import org.paveltinnik.entity.Author;
import org.paveltinnik.mapper.AuthorMapper;
import org.paveltinnik.repository.impl.AuthorRepositoryImpl;
import org.paveltinnik.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepositoryImpl authorRepository;
    private final AuthorMapper authorMapper;

    @Autowired
    public AuthorServiceImpl(AuthorRepositoryImpl authorRepository, AuthorMapper authorMapper) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    @Override
    public void save(AuthorDTO authorDTO) {
        Author author = authorMapper.toAuthor(authorDTO);
        authorRepository.save(author);
    }

    @Override
    public AuthorDTO findById(Long id) {
        return authorMapper.toAuthorDTO(authorRepository.findById(id));
    }

    @Override
    public List<AuthorDTO> findAll() {
        return authorRepository.findAll().stream()
                .map(authorMapper::toAuthorDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void update(AuthorDTO authorDTO) {
        authorRepository.update(authorMapper.toAuthor(authorDTO));
    }

    @Override
    public void delete(Long id) {
        authorRepository.delete(id);
    }
}