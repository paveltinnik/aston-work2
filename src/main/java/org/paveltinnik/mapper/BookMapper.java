package org.paveltinnik.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.paveltinnik.dto.BookDTO;
import org.paveltinnik.entity.Author;
import org.paveltinnik.entity.Book;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mapping(target = "authorId", source = "book.author.id")
    BookDTO toBookDTO(Book book);

    @Mapping(target = "id", source = "bookDTO.id")
    Book toBook(BookDTO bookDTO, Author author);
}