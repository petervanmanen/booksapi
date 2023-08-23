package nl.petervanmanen.bookapis.service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.books.Books;
import com.google.api.services.books.model.Volumes;
import lombok.NonNull;
import lombok.extern.java.Log;
import nl.petervanmanen.bookapis.exception.ResponseException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Service
@Log
public class GoogleBookServiceImpl implements GoogleBookService {

    public static final String BOOK_API = "BookApi";
    public static final String NEWEST = "newest";
    public static final long MAX_RESULTS = 10L;

    @Override
    public Volumes getVolumes(@NonNull String query, String language) {
        try {
            Books books = new Books.Builder(GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(), null).setApplicationName(BOOK_API).build();
            Books.Volumes.List list = books.volumes().list(query);
            list.setLangRestrict(language);
            list.setOrderBy(NEWEST);
            list.setMaxResults(MAX_RESULTS);
            list.setPrintType("books");
            Volumes execution = list.execute();
            return execution;
        } catch (GeneralSecurityException e) {
            log.severe(e.getMessage());
            throw new ResponseException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IOException e) {
            log.severe(e.getMessage());
            throw new ResponseException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
