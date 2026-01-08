package ie.atu.bookshopproject;

import ie.atu.bookshopproject.Repository.BookRepository;
import ie.atu.bookshopproject.Service.BookService;
import ie.atu.bookshopproject.errorhandling.BookNotFound;
import ie.atu.bookshopproject.model.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    void createBook_success() {
        Book book = new Book(1L, "1984", "George Orwell", "Penguin", 12.99);
        when(bookRepository.save(book)).thenReturn(book);

        Book result = bookService.create(book);

        assertNotNull(result);
        assertEquals("1984", result.getTitle());
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void findAllBooks_success() {
        Book book1 = new Book(1L, "1984", "George Orwell", "Penguin", 12.99);
        Book book2 = new Book(2L, "Dune", "Frank Herbert", "Ace", 15.99);

        when(bookRepository.findAll()).thenReturn(List.of(book1, book2));

        List<Book> result = bookService.findAll();

        assertEquals(2, result.size());
        verify(bookRepository).findAll();
    }

    @Test
    void findById_success() {
        Book book = new Book(1L, "1984", "George Orwell", "Penguin", 12.99);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Optional<Book> result = bookService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("1984", result.get().getTitle());
        verify(bookRepository).findById(1L);
    }

    @Test
    void findById_notFound() {
        when(bookRepository.findById(999L)).thenReturn(Optional.empty());

        BookNotFound exception = assertThrows(BookNotFound.class, () -> bookService.findById(999L));

        assertEquals("Book with id 999 not found", exception.getMessage());
        verify(bookRepository).findById(999L);
    }

    @Test
    void updateBook_success() {
        Book existingBook = new Book(1L, "Old Title", "Old Author", "Old Pub", 10.00);
        Book updatedBook = new Book(null, "New Title", "New Author", "New Pub", 15.00);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(existingBook));
        when(bookRepository.save(existingBook)).thenReturn(existingBook);

        Optional<Book> result = bookService.updateBook(1L, updatedBook);

        assertTrue(result.isPresent());
        assertEquals("New Title", result.get().getTitle());
        assertEquals("New Author", result.get().getAuthor());
        assertEquals(15.00, result.get().getPrice());

        verify(bookRepository).findById(1L);
        verify(bookRepository).save(existingBook);
    }

    @Test
    void updateBook_notFound() {
        Book book = new Book(null, "Title", "Author", "Pub", 10.00);
        when(bookRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(BookNotFound.class, () -> bookService.updateBook(999L, book));

        verify(bookRepository).findById(999L);
        verify(bookRepository, never()).save(any());
    }

    @Test
    void deleteBook_success() {
        Book book = new Book(1L, "1984", "George Orwell", "Penguin", 12.99);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Optional<Book> result = bookService.delete(1L);

        assertTrue(result.isPresent());
        verify(bookRepository).findById(1L);
        verify(bookRepository).delete(book);
    }

    @Test
    void deleteBook_notFound() {
        when(bookRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(BookNotFound.class,() -> bookService.delete(999L));

        verify(bookRepository).findById(999L);
        verify(bookRepository, never()).delete(any());
    }
}

