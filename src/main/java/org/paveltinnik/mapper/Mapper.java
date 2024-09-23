package org.paveltinnik.mapper;

import org.paveltinnik.dto.AuthorDTO;
import org.paveltinnik.dto.BookDTO;
import org.paveltinnik.dto.GenreDTO;
import org.paveltinnik.model.Author;
import org.paveltinnik.model.Book;
import org.paveltinnik.model.Genre;

import java.util.stream.Collectors;

public class Mapper {

    public static Author toAuthor(AuthorDTO authorDTO) {
        Author author = new Author();
        author.setId(authorDTO.getId());
        author.setName(authorDTO.getName());
        return author;
    }

    public static AuthorDTO toAuthorDTO(Author author) {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(author.getId());
        authorDTO.setName(author.getName());
        authorDTO.setBookIds(author.getBooks().stream().map(Book::getId).collect(Collectors.toSet()));
        return authorDTO;
    }

    public static Book toBook(BookDTO bookDTO, Author author) {
        Book book = new Book();
        book.setId(bookDTO.getId());
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(author);  // Assuming the author is passed as a parameter
        return book;
    }

    public static BookDTO toBookDTO(Book book) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(book.getId());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setAuthorId(book.getAuthor().getId());
        bookDTO.setGenreIds(book.getGenres().stream().map(Genre::getId).collect(Collectors.toSet()));
        return bookDTO;
    }

    public static Genre toGenre(GenreDTO genreDTO) {
        Genre genre = new Genre();
        genre.setId(genreDTO.getId());
        genre.setName(genreDTO.getName());
        return genre;
    }

    public static GenreDTO toGenreDTO(Genre genre) {
        GenreDTO genreDTO = new GenreDTO();
        genreDTO.setId(genre.getId());
        genreDTO.setName(genre.getName());
        genreDTO.setBookIds(genre.getBooks().stream().map(Book::getId).collect(Collectors.toSet()));
        return genreDTO;
    }
}