package nl.petervanmanen.bookapis.service;

import nl.petervanmanen.bookapis.model.BookResponse;

import java.util.List;


public interface BookService {

    public List<BookResponse> getBooks(String query, String language);
}
