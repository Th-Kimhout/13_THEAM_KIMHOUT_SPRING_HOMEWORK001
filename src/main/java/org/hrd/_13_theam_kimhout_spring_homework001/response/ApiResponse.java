package org.hrd._13_theam_kimhout_spring_homework001.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;


import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {

    private Boolean success;
    private String message;
    private HttpStatus status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T payload;
    private LocalDateTime timestamp;


//    private static Map<String, Object> createBaseResponse(boolean success, String message, HttpStatus httpStatus) {
//        Map<String, Object> response = new LinkedHashMap<>();
//        response.put("success", success);
//        response.put("message", message);
//        response.put("status", httpStatus.value());
//        response.put("timestamp", Instant.now().toString());
//        return response;
//    }

//    public static ResponseEntity<Object> responseBuilder(boolean success, String message, HttpStatus httpStatus, List<Ticket> tickets, Pagination pagination) {
//        Map<String, Object> response = createBaseResponse(success, message, httpStatus);
//        response.put("payload", Map.of("items", tickets, "pagination", pagination));
//        return new ResponseEntity<>(response, httpStatus);
//    }
//
//    public static ResponseEntity<Object> responseBuilder(boolean success, String message, HttpStatus httpStatus, List<Ticket> tickets) {
//        Map<String, Object> response = createBaseResponse(success, message, httpStatus);
//        response.put("payload", tickets);
//        return new ResponseEntity<>(response, httpStatus);
//    }
//
//    public static ResponseEntity<Object> responseBuilder(boolean success, String message, HttpStatus httpStatus, Ticket ticket) {
//        Map<String, Object> response = createBaseResponse(success, message, httpStatus);
//        response.put("payload", ticket);
//        return new ResponseEntity<>(response, httpStatus);
//
//    }
//
//    public static ResponseEntity<Object> responseBuilder(boolean success, String message, HttpStatus httpStatus) {
//        return new ResponseEntity<>(createBaseResponse(success, message, httpStatus), httpStatus);
//    }
//
//    public static ResponseEntity<Object> responseErrorBuilder(boolean success, String message, HttpStatus httpStatus, List<String> errors) {
//        Map<String, Object> response = createBaseResponse(success, message, httpStatus);
//        response.put("errors", errors);
//        return new ResponseEntity<>(response, httpStatus);
//    }
}
