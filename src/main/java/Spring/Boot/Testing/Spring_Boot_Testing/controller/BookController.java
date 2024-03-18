package Spring.Boot.Testing.Spring_Boot_Testing.controller;

import Spring.Boot.Testing.Spring_Boot_Testing.entities.Book;
import Spring.Boot.Testing.Spring_Boot_Testing.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<Book> saveBook (@RequestBody Book book){
        return ResponseEntity.ok(bookService.saveBook(book));
    }
}
