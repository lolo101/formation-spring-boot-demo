package com.example.demo;

import com.example.demo.library.Book;
import com.example.demo.library.Library;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("books")
public class Books {

    private final Library library;

    public Books(Library library) {
        this.library = library;
    }

    @GetMapping
    public Collection<Book> books() {
        return library.inventory();
    }

    @GetMapping("{isbn}")
    public ResponseEntity<Book> book(@PathVariable String isbn) {
        return library.findBook(isbn)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().location(URI.create("/books")).build());
    }
}
