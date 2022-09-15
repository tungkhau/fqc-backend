package vn.com.assistant.fqcbackend.dto;

import lombok.Getter;

@Getter
public class ResponseBodyDTO {
    private final String message;
    private final String result;
    private final Object data;

    public ResponseBodyDTO(String message, String result, Object data) {
        this.result = result;
        this.message = message;
        this.data = data;
    }
}
