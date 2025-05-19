package pl.complaint.app.controller.exception;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.complaint.app.controller.exception.model.InvalidSortParamException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InvalidSortParamException.class)
    public ResponseEntity<ProblemDetail> handleException(InvalidSortParamException ex) {
        log.info("Handled invalid sort param exception: {}", ex.getMessage());
        return ResponseEntity.badRequest()
                .body(ProblemDetail.forStatusAndDetail(BAD_REQUEST, ex.getMessage()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleException(EntityNotFoundException ex) {
        log.info("Handled entity not found exception: {}", ex.getMessage());
        return ResponseEntity.badRequest()
                .body(ProblemDetail.forStatusAndDetail(BAD_REQUEST, ex.getMessage()));
    }

}
