package ie.atu.bookshopproject;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository repo;
    public BookService(BookRepository repo) {
        this.repo = repo;
    }

    public Book createBook(Book book){
        return repo.save(book);
    }

    public List<Book> getAllBooks(){return repo.findAll();}

    public Book findById(Long id){
        return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Book with id " + id + " not found"));
    }

    public Book updateBook(Long id, Book book){
        Book existingBook = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Book with id " + id + " not found"));

        existingBook.setTitle(book.getTitle());
        existingBook.setAuthor(book.getAuthor());
        existingBook.setPublisher(book.getPublisher());
        existingBook.setPrice(book.getPrice());
        return repo.save(existingBook);
    }
}
