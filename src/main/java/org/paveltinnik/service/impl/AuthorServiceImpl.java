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

    @Autowired
    private final AuthorRepositoryImpl authorRepository;

    public AuthorServiceImpl(AuthorRepositoryImpl authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public void save(AuthorDTO authorDTO) {
        Author author = AuthorMapper.INSTANCE.toAuthor(authorDTO);
        authorRepository.save(author);
    }

    @Override
    public AuthorDTO findById(Long id) {
        return AuthorMapper.INSTANCE.toAuthorDTO(authorRepository.findById(id));
    }

    @Override
    public List<AuthorDTO> findAll() {
        return authorRepository.findAll().stream()
                .map(AuthorMapper.INSTANCE::toAuthorDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void update(AuthorDTO authorDTO) {
        authorRepository.update(AuthorMapper.INSTANCE.toAuthor(authorDTO));
    }

    @Override
    public void delete(Long id) {
        authorRepository.delete(id);
    }
}