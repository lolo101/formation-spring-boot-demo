package com.example.demo.library;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class LibraryTest {

    @Nested
    class findBookShould {
        @Test
        void return_not_found_on_unknown_ISBN() {
            Library library = new Library();
            String isbn = "12345";
            Book book = library.findBook(isbn);
            Assertions.assertEquals(Book.NotFound, book);
        }
    }
}
