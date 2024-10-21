package org.paveltinnik.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.paveltinnik.dto.GenreDTO;
import org.paveltinnik.entity.Book;
import org.paveltinnik.entity.Genre;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@Mapper(componentModel = "spring")
public interface GenreMapper {

    @Mapping(target = "bookIds", expression = "java(mapBooksToIDs(genre.getBooks()))")
    GenreDTO toGenreDTO(Genre genre);

    Genre toGenre(GenreDTO genreDTO);

    default Set<Long> mapBooksToIDs(Set<Book> books) {
        return books.stream().map(Book::getId).collect(Collectors.toSet());
    }
}