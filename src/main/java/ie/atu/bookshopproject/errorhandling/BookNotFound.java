package ie.atu.bookshopproject.errorhandling;

public class BookNotFound extends RuntimeException {
    private String message;
    private String field;

    public BookNotFound(String field, String message) {
        this.field = field;
    }
    public BookNotFound(String message) {
        super(message);
    }
}
