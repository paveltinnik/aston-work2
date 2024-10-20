package org.paveltinnik.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.paveltinnik.dto.BookDTO;
import org.paveltinnik.entity.Author;
import org.paveltinnik.entity.Book;
import org.paveltinnik.entity.Genre;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mapping(target = "authorId", source = "book.author.id")
    @Mapping(target = "genreIds", expression = "java(mapGenresToIDs(book.getGenres()))")
    BookDTO toBookDTO(Book book);

    @Mapping(target = "id", source = "bookDTO.id")
    Book toBook(BookDTO bookDTO, Author author);

    default Set<Long> mapGenresToIDs(Set<Genre> genres) {
        return genres.stream().map(Genre::getId).collect(Collectors.toSet());
    }
}