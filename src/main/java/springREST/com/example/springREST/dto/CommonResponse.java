package springREST.com.example.springREST.dto;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class CommonResponse {
    String responseMessage;

    HttpStatus status;

    boolean isSuccess;
}
