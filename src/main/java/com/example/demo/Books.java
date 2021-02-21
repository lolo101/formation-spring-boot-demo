package com.example.demo;

import com.example.demo.library.Book;
import com.example.demo.library.Library;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Book book(@PathVariable String isbn) {
        return library.findBook(isbn);
    }
}
