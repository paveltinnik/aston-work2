package org.paveltinnik.mapper;

import javax.annotation.processing.Generated;
import org.paveltinnik.dto.AuthorDTO;
import org.paveltinnik.entity.Author;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-21T12:14:51+0700",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.2 (Oracle Corporation)"
)
@Component
public class AuthorMapperImpl implements AuthorMapper {

    @Override
    public AuthorDTO toAuthorDTO(Author author) {
        if ( author == null ) {
            return null;
        }

        AuthorDTO authorDTO = new AuthorDTO();

        authorDTO.setId( author.getId() );
        authorDTO.setName( author.getName() );

        return authorDTO;
    }

    @Override
    public Author toAuthor(AuthorDTO authorDTO) {
        if ( authorDTO == null ) {
            return null;
        }

        Author author = new Author();

        author.setId( authorDTO.getId() );
        author.setName( authorDTO.getName() );

        return author;
    }
}
