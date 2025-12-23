package ie.atu.bookshopproject.Service;

import ie.atu.bookshopproject.Repository.BookRepository;
import ie.atu.bookshopproject.errorhandling.BookNotFound;
import ie.atu.bookshopproject.model.Book;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository repo;
    public BookService(BookRepository repo) {
        this.repo = repo;
    }

    public Book create(Book book){
        return repo.save(book);
    }

    public List<Book> findAll(){return repo.findAll();}

    public Optional<Book> findById(Long id){
        Optional<Book> book = repo.findById(id);
        if(book.isPresent()){
            return book;
        }
        else{
            throw new BookNotFound("Book not found" + id + "not found");
        }
    }

    public Optional<Book> updateBook(Long id, Book book){
        Optional<Book> existingBook = repo.findById(id);
        if(existingBook.isPresent()){
            Book updatedBook = existingBook.get();
            updatedBook.setTitle(book.getTitle());
            updatedBook.setAuthor(book.getAuthor());
            updatedBook.setPublisher(book.getPublisher());
            updatedBook.setPrice(book.getPrice());
            repo.save(updatedBook);
            return Optional.of(updatedBook);
        }
        else{
            throw new BookNotFound("Book not found" + id + "not found");
        }
    }

    public Optional<Book> delete(Long id){
        Optional<Book> book = repo.findById(id);
        if(book.isPresent()){
            repo.delete(book.get());
            return book;
        }
        else {
            throw new BookNotFound("Book not found" + id + "not found");
        }
    }
}
