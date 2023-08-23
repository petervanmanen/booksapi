package nl.petervanmanen.bookapis.service;

import com.google.api.services.books.model.Volumes;
import lombok.NonNull;
import nl.petervanmanen.bookapis.exception.ResponseException;
import nl.petervanmanen.bookapis.model.BookResponse;
import nl.petervanmanen.bookapis.util.IsoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    GoogleBookService googleBookService;

    @Override
    public List<BookResponse> getBooks(@NonNull String query, String language) {
        if (language != null && !IsoUtil.validateValidIsoLanguage(language)) {
            throw new ResponseException(String.format("%s is not a valid ISO 639-1 code", language), HttpStatus.BAD_REQUEST);
        }
        Volumes volumes = googleBookService.getVolumes(query, language);
        if (volumes.getTotalItems() == 0)
            return new ArrayList<BookResponse>();

        return volumes.getItems().stream().map(volume -> new BookResponse(volume.getVolumeInfo())).toList();
    }
}
