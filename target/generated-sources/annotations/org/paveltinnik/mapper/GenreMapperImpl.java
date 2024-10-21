package org.paveltinnik.mapper;

import javax.annotation.processing.Generated;
import org.paveltinnik.dto.GenreDTO;
import org.paveltinnik.entity.Genre;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-21T14:24:35+0700",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.2 (Oracle Corporation)"
)
@Component
public class GenreMapperImpl implements GenreMapper {

    @Override
    public GenreDTO toGenreDTO(Genre genre) {
        if ( genre == null ) {
            return null;
        }

        GenreDTO genreDTO = new GenreDTO();

        genreDTO.setId( genre.getId() );
        genreDTO.setName( genre.getName() );

        genreDTO.setBookIds( mapBooksToIDs(genre.getBooks()) );

        return genreDTO;
    }

    @Override
    public Genre toGenre(GenreDTO genreDTO) {
        if ( genreDTO == null ) {
            return null;
        }

        Genre genre = new Genre();

        genre.setId( genreDTO.getId() );
        genre.setName( genreDTO.getName() );

        return genre;
    }
}
