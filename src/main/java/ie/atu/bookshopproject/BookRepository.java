package ie.atu.bookshopproject;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public class BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByTitle(Sting Title);
}
