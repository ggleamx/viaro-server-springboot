package com.lgap.portfolio.exception;

import com.lgap.portfolio.dto.APIResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class APIValidationException {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIResponse<?>> customValidationErrorHandling(MethodArgumentNotValidException exception) {
        BindingResult result = exception.getBindingResult();
        List<String> errorMessages = result.getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList());

        return ResponseEntity.badRequest().body(new APIResponse<>(true, String.join(", ", errorMessages), null, 400));
    }
}
