package org.paveltinnik.mapper;

import org.mapstruct.Mapper;
import org.paveltinnik.dto.AuthorDTO;
import org.paveltinnik.entity.Author;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    AuthorDTO toAuthorDTO(Author author);
    Author toAuthor(AuthorDTO authorDTO);
}