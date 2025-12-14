package ie.atu.bookshopproject.controller;

import ie.atu.bookshopproject.DTO.UserDTO;
import ie.atu.bookshopproject.FeignClient.UserClient;
import ie.atu.bookshopproject.Service.BookService;
import ie.atu.bookshopproject.model.Book;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;
    private final UserClient userClient;

    public BookController(BookService bookService, UserClient userClient) {
        this.bookService = bookService;
        this.userClient = userClient;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book createBook(@Valid @RequestBody Book book) {
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
    @ResponseStatus(HttpStatus.CREATED)
    public Book updateBook(@PathVariable Long id, @RequestBody Book book) {
        return bookService.updateBook(id, book);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Book deleteBook(@PathVariable Long id){
        return bookService.delete(id);
    }

    @GetMapping("/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getUserId(@PathVariable Long id) {
        return userClient.getUserID(id); // Call the injected client
    }

}
