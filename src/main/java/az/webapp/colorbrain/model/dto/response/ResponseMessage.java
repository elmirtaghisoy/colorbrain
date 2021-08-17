package az.webapp.colorbrain.model.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ResponseMessage {

    SUCCESS(0, "Əməliyyat uğurla yerinə yetirildi"),
    ERROR(1,"Daxili xəta.");

    private final int code;
    private final String message;

    ResponseMessage(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}
