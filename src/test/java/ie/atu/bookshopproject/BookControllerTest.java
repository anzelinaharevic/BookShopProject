package ie.atu.bookshopproject;

import com.fasterxml.jackson.databind.ObjectMapper;
import ie.atu.bookshopproject.DTO.UserDTO;
import ie.atu.bookshopproject.FeignClient.UserClient;
import ie.atu.bookshopproject.Service.BookService;
import ie.atu.bookshopproject.controller.BookController;
import ie.atu.bookshopproject.model.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookService bookService;

    @MockitoBean
    private UserClient userClient;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void testCreateBook() throws Exception {
        Book book = new Book(1L, "1984", "George Orwell", "Penguin", 12.99);
        when(bookService.create(any(Book.class))).thenReturn(book);

        String json = mapper.writeValueAsString(book);

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("1984"));
    }

    @Test
    void testGetAllBooks() throws Exception {
        List<Book> books = List.of(
                new Book(1L, "1984", "George Orwell", "Penguin", 12.99),
                new Book(2L, "Dune", "Frank Herbert", "Ace", 15.99)
        );

        when(bookService.findAll()).thenReturn(books);

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testGetBookById() throws Exception {
        Book book = new Book(1L, "1984", "George Orwell", "Penguin", 12.99);
        when(bookService.findById(1L)).thenReturn(Optional.of(book));

        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("1984"));
    }

    @Test
    void testGetBookByIdNotFound() throws Exception {
        when(bookService.findById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/books/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateBook() throws Exception {
        Book book = new Book(1L, "1984", "George Orwell", "Penguin", 12.99);
        when(bookService.findById(1L)).thenReturn(Optional.of(book));

        String json = mapper.writeValueAsString(book);

        mockMvc.perform(put("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateBookNotFound() throws Exception {
        Book book = new Book(1L, "1984", "George Orwell", "Penguin", 12.99);
        when(bookService.findById(999L)).thenReturn(Optional.empty());

        String json = mapper.writeValueAsString(book);

        mockMvc.perform(put("/api/books/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteBook() throws Exception {
        Book book = new Book(1L, "1984", "George Orwell", "Penguin", 12.99);
        when(bookService.findById(1L)).thenReturn(Optional.of(book));

        mockMvc.perform(delete("/api/books/1?id=1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteBookNotFound() throws Exception {
        when(bookService.findById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/books/999?id=999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetUserFromFeignClient() throws Exception {
        UserDTO user = new UserDTO(1L, "eoin", "plasma", true);
        when(userClient.getUserID(anyLong())).thenReturn(user);

        mockMvc.perform(get("/api/books/GetUser/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.loginId").value(1L))
                .andExpect(jsonPath("$.username").value("eoin"));
    }
}


