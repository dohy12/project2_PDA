package pda.server.Controller;

import org.springframework.http.HttpStatus;

public class RestException extends RuntimeException{
    private HttpStatus status;
    private String message;

    public RestException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }
}
