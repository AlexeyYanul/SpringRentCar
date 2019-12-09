package itacademy.configuration;

import itacademy.dto.response.ErrorResponseDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = NullPointerException.class)
    protected ResponseEntity<Object> handleNullPointerException(NullPointerException exception, WebRequest request) {

        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(HttpStatus.BAD_REQUEST, exception.getMessage());
        return handleExceptionInternal(exception, errorResponseDTO, new HttpHeaders(), errorResponseDTO.getHttpStatus(), request);
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException exception, WebRequest request) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(HttpStatus.NOT_FOUND, exception.getMessage());
        return handleExceptionInternal(exception, errorResponseDTO, new HttpHeaders(), errorResponseDTO.getHttpStatus(), request);
    }
}
