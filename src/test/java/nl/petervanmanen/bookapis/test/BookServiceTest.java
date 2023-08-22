package nl.petervanmanen.bookapis.test;

import nl.petervanmanen.bookapis.exception.ResponseException;
import nl.petervanmanen.bookapis.model.BookResponse;
import nl.petervanmanen.bookapis.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class BookServiceTest {
    @Autowired
    BookService bookService;

    @Test
    void shouldReturnBookList() {
        List<BookResponse> books = bookService.getBooks("java", "nl");
        assertEquals("Jeanne d'Arc. De maagd van Orleans", books.get(0).getTitle());
        assertEquals("9788728400029", books.get(0).getIsbn());
        assertEquals("Mathilde", books.get(0).getAuthors().get(0));
        assertEquals("13 oktober 2022", books.get(0).getPublicatieDatum());
        assertEquals(10, books.size());
    }

    @Test
    void shouldGiveBookListWhenPublicatieDatumMalformed() {
        List<BookResponse> books = bookService.getBooks("banaan", "nl");
        assertEquals("1 april", books.get(0).getPublicatieDatum());
    }

    @Test
    void shouldGiveBookListWhenNoIsbn() {
        List<BookResponse> books = bookService.getBooks("banaan", "nl");
        assertEquals("", books.get(9).getIsbn());
    }

    @Test
    void shouldGiveBookListWhenNoAuthor() {
        List<BookResponse> books = bookService.getBooks("banaan", "nl");
        assertEquals(null, books.get(8).getAuthors());
    }

    @Test
    void shouldGiveClientError() {
        ResponseException thrown = assertThrows(ResponseException.class, () -> {
            List<BookResponse> books = bookService.getBooks("java", "EN");
        });
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getHttpstatus());
        assertEquals("EN is not a valid ISO 639-1 code", thrown.getMessage());
    }

    @Test
    void shouldGiveEmptyListWhenNoResponse() {
        List<BookResponse> books = bookService.getBooks("noresponse", "en");
        assertEquals(0, books.size());
    }
}
