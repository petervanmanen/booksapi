package nl.petervanmanen.bookapis.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.PatternProperty;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.Pattern;
import lombok.extern.java.Log;
import nl.petervanmanen.bookapis.exception.ResponseException;
import nl.petervanmanen.bookapis.model.BookResponse;
import nl.petervanmanen.bookapis.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BookController {

    @Autowired
    BookService bookService;

    @GetMapping("/books")
    @Operation(summary = "Queries google books api for books")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "API was succesfully queried, first 10 results are shown. If the api contains no results an empty list will be returned"),
            @ApiResponse(responseCode = "503", description = "The API malfunctioned"),
            @ApiResponse(responseCode = "400", description = "The requestobject was invalid")
    })
    public List<BookResponse> getBooks(@Parameter(description = "Text to search for") String query,@Pattern(regexp = "^[a-z]{2}$") @Parameter(description = " ISO 639-1 language code filter(2 lowercase letters like \'en\'") @RequestParam(required = false) String language) {
        return bookService.getBooks(query, language);
    }

    @ExceptionHandler({ResponseException.class})
    public ResponseEntity<String> handleResponseException(ResponseException exception) {

        return new ResponseEntity<>(exception.getMessage(), exception.getHttpstatus());
    }

}
