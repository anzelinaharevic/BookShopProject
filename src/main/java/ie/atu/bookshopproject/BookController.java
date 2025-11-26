package ie.atu.bookshopproject;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {this.bookService = bookService;}

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book createBook(@RequestBody Book book) {
        return bookService.create(book);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Book> getAllBooks() {return bookService.findAll();}

    @GetMapping("/{id}")
    public Book byId(@PathVariable Long id){
        return bookService.findById(id);
    }

    @PutMapping("/{id}")
    public Book updateBook(@PathVariable Long id, @RequestBody Book book) {
        return bookService.update(id, person);
    }

    @DeleteMapping
    public Book deleteBook(@PathVariable Long id){
        return bookService.delete(id);
    }
}
