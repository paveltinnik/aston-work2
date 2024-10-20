package org.paveltinnik.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.paveltinnik.dto.AuthorDTO;
import org.paveltinnik.entity.Author;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface AuthorMapper {
    AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

    AuthorDTO toAuthorDTO(Author author);
    Author toAuthor(AuthorDTO authorDTO);
}