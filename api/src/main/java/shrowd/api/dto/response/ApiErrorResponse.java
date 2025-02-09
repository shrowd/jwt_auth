package shrowd.api.dto.response;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ApiErrorResponse(
        LocalDateTime timestamp,
        int code,
        HttpStatus status,
        String message
) {
    public ApiErrorResponse(int code, HttpStatus status, String message) {
        this(LocalDateTime.now(), code, status, message);
    }
}