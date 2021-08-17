package az.webapp.colorbrain.model.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@JsonPropertyOrder({
        "data", "httpCode", "httpMessage", "responseCode", "responseMessage", "timestamp"
})
@ToString
public class StandardResponse {

    public StandardResponse(HttpStatus status) {
        this.status = status;
    }

    public StandardResponse(Object data, HttpStatus httpStatus) {
        this.data.put(RESPONSE, data);
        this.status = httpStatus;
    }

    private static final String RESPONSE = "response";
    private HashMap<String, Object> data = new LinkedHashMap<>();
    private HttpStatus status;
    private final LocalDateTime timestamp = LocalDateTime.now();

    public void setHttpStatus(HttpStatus httpStatus) {
        this.status = httpStatus;
    }

    public void setData(Object responseData) {
        this.data.put(RESPONSE, responseData);
    }

    public int getHttpCode() {
        return this.status.value();
    }

    public boolean hasErrors() {
        return status.value() != 200;
    }

    public String getHttpMessage() {
        return this.status.getReasonPhrase();
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Map<String, Object> getData() {
        return data;
    }

}
