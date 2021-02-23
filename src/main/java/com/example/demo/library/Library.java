package com.example.demo.library;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface Library extends JpaRepository<Book, String> {

    default Collection<Book> inventory() {
        return findAll();
    }

    default Optional<Book> findBook(String isbn) {
        return findById(isbn);
    }
}
