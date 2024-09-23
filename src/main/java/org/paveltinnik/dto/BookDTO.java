package org.paveltinnik.dto;

import java.util.Set;

public class BookDTO {
    private Long id;
    private String title;
    private Long authorId; // ID of the author
    private Set<Long> genreIds; // IDs of genres associated with this book

    public BookDTO() {}

    public BookDTO(Long id, String title, Long authorId, Set<Long> genreIds) {
        this.id = id;
        this.title = title;
        this.authorId = authorId;
        this.genreIds = genreIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Set<Long> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(Set<Long> genreIds) {
        this.genreIds = genreIds;
    }
}
