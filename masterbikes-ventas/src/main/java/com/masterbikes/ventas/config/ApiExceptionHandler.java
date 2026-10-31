package com.masterbikes.ventas.config;
import java.util.Map;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String,String>> invalidData(DataIntegrityViolationException ex) {
        return ResponseEntity.badRequest().body(Map.of("error", "Datos incompatibles con las restricciones de la base de datos"));
    }
}
