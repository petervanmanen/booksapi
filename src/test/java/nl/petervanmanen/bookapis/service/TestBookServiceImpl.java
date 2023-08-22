package nl.petervanmanen.bookapis.service;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.LowLevelHttpRequest;
import com.google.api.client.http.LowLevelHttpResponse;
import com.google.api.client.json.Json;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.testing.http.MockHttpTransport;
import com.google.api.client.testing.http.MockLowLevelHttpRequest;
import com.google.api.client.testing.http.MockLowLevelHttpResponse;
import com.google.api.services.books.Books;
import com.google.api.services.books.model.Volumes;
import nl.petervanmanen.bookapis.exception.ResponseException;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Service
@Primary
public class TestBookServiceImpl implements GoogleBookService {

    public static final String BOOK_API = "BookApi";
    public static final String NEWEST = "newest";
    public static final long MAX_RESULTS = 10L;

    @Override
    public Volumes getVolumes(String query, String language) {
        Books books = null;
        try {
            HttpTransport transport = new MockHttpTransport() {
                @Override
                public LowLevelHttpRequest buildRequest(String method, String url) throws IOException {
                    return new MockLowLevelHttpRequest() {
                        @Override
                        public LowLevelHttpResponse execute() throws IOException {
                            MockLowLevelHttpResponse response = new MockLowLevelHttpResponse();

                            response.setStatusCode(200);
                            response.setContentType(Json.MEDIA_TYPE);
                            InputStream inputStream = this.getClass().getResourceAsStream(String.format("/%s.json", query));
                            response.setContent(new String(inputStream.readAllBytes(), StandardCharsets.UTF_8));
                            return response;
                        }
                    };
                }
            };

            books = new Books.Builder(transport, JacksonFactory.getDefaultInstance(), null).setApplicationName(BOOK_API).build();
            Books.Volumes.List list = books.volumes().list(query);
            list.setLangRestrict(language);
            list.setOrderBy(NEWEST);
            list.setMaxResults(MAX_RESULTS);

            Volumes execution = list.execute();
            return execution;
        } catch (IOException e) {
            throw new ResponseException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

/*    @Override
    public Volumes getVolumes(String query, String language) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            InputStream inputStream = this.getClass().getResourceAsStream("/java.json");
            return objectMapper.readValue(inputStream, com.google.api.services.books.model.Volumes.class);
        } catch (JsonProcessingException e) {
            throw new ResponseException("Json parse exception",HttpStatus.I_AM_A_TEAPOT);
        } catch (IOException e) {
            throw new ResponseException("File read exception",HttpStatus.I_AM_A_TEAPOT);
        }
    }*/
}
