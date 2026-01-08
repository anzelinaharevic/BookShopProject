package ie.atu.bookshopproject.controller;

import ie.atu.bookshopproject.DTO.PaymentDTO;
import ie.atu.bookshopproject.FeignClient.paymentClient;
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
    private final paymentClient paymentClient;

    public BookController(BookService bookService, paymentClient paymentClient) {
        this.bookService = bookService;
        this.paymentClient = paymentClient;
    }

    @PostMapping
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
    public ResponseEntity<Book> deleteBook(@PathVariable Long id){
        Optional<Book> maybe = bookService.findById(id);
        if(maybe.isPresent()) {
            return ResponseEntity.noContent().build();
        }
        else  {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/payment/{id}")
    public ResponseEntity<PaymentDTO> getPaymentID(@PathVariable("id") Long paymentID) {
        return ResponseEntity.ok(paymentClient.getPaymentID(paymentID));
    }

}
