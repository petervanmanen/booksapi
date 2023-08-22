package nl.petervanmanen.bookapis.service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.books.Books;
import com.google.api.services.books.model.Volumes;
import nl.petervanmanen.bookapis.exception.ResponseException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Service
public class GooglebookServiceImpl implements GoogleBookService {

    public static final String BOOK_API = "BookApi";
    public static final String NEWEST = "newest";
    public static final long MAX_RESULTS = 10L;

    @Override
    public Volumes getVolumes(String query, String language) {
        Books books = null;
        try {
            books = new Books.Builder(GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(), null).setApplicationName(BOOK_API).build();
            Books.Volumes.List list = books.volumes().list(query);
            list.setLangRestrict(language);
            list.setOrderBy(NEWEST);
            list.setMaxResults(MAX_RESULTS);

            Volumes execution = list.execute();
            return execution;
        } catch (GeneralSecurityException e) {
            throw new ResponseException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IOException e) {
            throw new ResponseException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
