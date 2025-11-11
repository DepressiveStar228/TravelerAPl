package untitled.travelerapi.Controller.Exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleJsonParseException(HttpMessageNotReadableException ex) {
        return new ResponseEntity<>(
                Map.of("error", "Validation error", "details", "Invalid JSON format or data type"),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotFound(EntityNotFoundException ex) {
        return new ResponseEntity<>(
                Map.of("error", ex.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public ResponseEntity<Map<String, Object>> handleConflict(ObjectOptimisticLockingFailureException ex) {
        Integer currentVersion = null;
        if (ex.getMessage().contains("Current version: ")) {
            currentVersion = Integer.parseInt(ex.getMessage().split("Current version: ")[1]);
        }

        return new ResponseEntity<>(
                Map.of("error", "Conflict: Travel plan was modified by another user",
                        "current_version", currentVersion != null ? currentVersion : "unknown"),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .findFirst()
                .orElse("Validation error");

        return new ResponseEntity<>(
                Map.of("error", "Validation error", "details", errorMessage),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleDataIntegrity(DataIntegrityViolationException ex) {
        String details = "A database constraint was violated. This might be due to a concurrent update or duplicate entry.";

        Throwable rootCause = ex.getMostSpecificCause();
        if (rootCause != null && rootCause.getMessage().contains("unique_plan_order")) {
            details = "Conflict: A location with this visit order was created concurrently. Please try again.";
        }

        return new ResponseEntity<>(
                Map.of("error", "Database conflict", "details", details),
                HttpStatus.CONFLICT
        );
    }
}
