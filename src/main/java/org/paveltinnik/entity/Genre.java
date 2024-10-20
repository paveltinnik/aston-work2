package org.paveltinnik.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Genre {
    @Id
    private Long id;
    private String name;
    @ManyToMany
    @JoinColumn(name = "book_id")
    private Set<Book> books = new HashSet<>(); // ManyToMany relationship

    public Genre() {}

    public Genre(Long id, String name) {
        this.id = id;
        this.name = name;
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

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }
}