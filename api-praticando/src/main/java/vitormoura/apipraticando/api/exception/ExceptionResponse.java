package vitormoura.apipraticando.api.exception;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

public class ExceptionResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String title;
    private Date timestamp;
    private String message;


    public ExceptionResponse(String title, Date timestamp, String message) {
        this.title = title;
        this.timestamp = timestamp;
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

}
