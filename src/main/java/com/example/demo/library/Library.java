package com.example.demo.library;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Component
public class Library {

    private static final List<Book> inventory = Arrays.asList(
            new Book("Bilbo le Hobbit", "JRR Tolkien", "225303293X"),
            new Book("The long road", "Christopher Tolkien", "0261102257")
    );

    public Collection<Book> inventory() {
        return inventory;
    }

    public Book findBook(String isbn) {
        return inventory.stream()
                .collect(toMap(Book::getIsbn, identity()))
                .getOrDefault(isbn, Book.NotFound);
    }
}
