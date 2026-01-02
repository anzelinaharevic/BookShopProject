package ie.atu.bookshopproject.controller;

import feign.Response;
import ie.atu.bookshopproject.DTO.UserDTO;
import ie.atu.bookshopproject.FeignClient.UserClient;
import ie.atu.bookshopproject.Service.BookService;
import ie.atu.bookshopproject.model.Book;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<Book> byId(@PathVariable Long id){
        Optional<Book> maybe = bookService.findById(id);
        if(maybe.isPresent()) {
            return ResponseEntity.ok(maybe.get());
        }
        else  {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
        Optional<Book> maybe = bookService.findById(id);
        if(maybe.isPresent()) {
            return ResponseEntity.ok(maybe.get());
        }
        else   {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Book> deleteBook(@RequestParam Long id){
        Optional<Book> maybe = bookService.findById(id);
        if(maybe.isPresent()) {
            return ResponseEntity.noContent().build();
        }
        else  {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/GetUser/{id}")
    public ResponseEntity<UserDTO> getUserId(@PathVariable("id") Long loginId) {
        return ResponseEntity.ok(userClient.getUserID(loginId));
    }

}
