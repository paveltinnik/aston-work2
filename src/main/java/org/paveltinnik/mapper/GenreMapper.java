package org.paveltinnik.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.paveltinnik.dto.GenreDTO;
import org.paveltinnik.entity.Genre;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface GenreMapper {
    GenreMapper INSTANCE = Mappers.getMapper(GenreMapper.class);

    GenreDTO toGenreDTO(Genre genre);
    Genre toGenre(GenreDTO genreDTO);
}