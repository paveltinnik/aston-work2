package org.paveltinnik.mapper;

import javax.annotation.processing.Generated;
import org.paveltinnik.dto.BookDTO;
import org.paveltinnik.entity.Author;
import org.paveltinnik.entity.Book;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-21T08:14:58+0700",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.2 (Oracle Corporation)"
)
@Component
public class BookMapperImpl implements BookMapper {

    @Override
    public BookDTO toBookDTO(Book book) {
        if ( book == null ) {
            return null;
        }

        BookDTO bookDTO = new BookDTO();

        bookDTO.setAuthorId( bookAuthorId( book ) );
        bookDTO.setId( book.getId() );
        bookDTO.setTitle( book.getTitle() );

        bookDTO.setGenreIds( mapGenresToIDs(book.getGenres()) );

        return bookDTO;
    }

    @Override
    public Book toBook(BookDTO bookDTO, Author author) {
        if ( bookDTO == null && author == null ) {
            return null;
        }

        Book book = new Book();

        if ( bookDTO != null ) {
            book.setId( bookDTO.getId() );
            book.setTitle( bookDTO.getTitle() );
        }
        if ( author != null ) {
            book.setAuthor( author );
        }

        return book;
    }

    private Long bookAuthorId(Book book) {
        if ( book == null ) {
            return null;
        }
        Author author = book.getAuthor();
        if ( author == null ) {
            return null;
        }
        Long id = author.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
