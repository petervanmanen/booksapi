package nl.petervanmanen.bookapis.service;

import com.google.api.services.books.model.Volumes;

public interface GoogleBookService {
    Volumes getVolumes(String query, String language);
}
