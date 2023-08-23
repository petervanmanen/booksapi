package nl.petervanmanen.bookapis.exception;

import org.springframework.http.HttpStatus;

public class ResponseException extends RuntimeException {
    private final HttpStatus httpstatus;

    public ResponseException(String message, HttpStatus httpstatus) {
        super(message);
        this.httpstatus = httpstatus;
    }

    public HttpStatus getHttpstatus() {
        return httpstatus;
    }
}
