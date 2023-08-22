package nl.petervanmanen.bookapis.model;

import com.google.api.services.books.model.Volume.VolumeInfo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Locale;

public class BookResponse {

    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM uuuu").withLocale(Locale.forLanguageTag("NL"));
    private final String title;
    private final List<String> authors;
    private final String isbn;
    private String publicatieDatum;

    public BookResponse(VolumeInfo volumeInfo) {
        title = volumeInfo.getTitle();
        isbn = volumeInfo.getIndustryIdentifiers() != null ? volumeInfo.getIndustryIdentifiers().stream().filter(industryIdentifiers -> industryIdentifiers.getType().equals("ISBN_13")).map(industryIdentifiers -> industryIdentifiers.getIdentifier()).findFirst().orElse("") : "";
        authors = volumeInfo.getAuthors();
        try {
            publicatieDatum = LocalDate.parse(volumeInfo.getPublishedDate()).format(formatter);
        } catch (DateTimeParseException dateTimeParseException) {
            //sometimes these dates are not parseable; as fallback we return the original value
            publicatieDatum = volumeInfo.getPublishedDate();
        }
    }

    public String getTitle() {
        return title;
    }


    public List<String> getAuthors() {
        return authors;
    }


    public String getISBN() {
        return isbn;
    }


    public String getPublicatieDatum() {
        return publicatieDatum;
    }


}
