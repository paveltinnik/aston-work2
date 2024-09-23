package org.paveltinnik.dto;

import java.util.Set;

public class GenreDTO {
    private Long id;
    private String name;
    private Set<Long> bookIds; // IDs of books that fall under this genre

    public GenreDTO() {}

    public GenreDTO(Long id, String name, Set<Long> bookIds) {
        this.id = id;
        this.name = name;
        this.bookIds = bookIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Long> getBookIds() {
        return bookIds;
    }

    public void setBookIds(Set<Long> bookIds) {
        this.bookIds = bookIds;
    }
}