package br.com.fiap.motoflow.infra.exception;

import br.com.fiap.motoflow.exceptions.*;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ValidationHandler {

    record ValidationError(String field, String message){
        public ValidationError(FieldError fieldError){
            this(fieldError.getField(), fieldError.getDefaultMessage());
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<ValidationError> handle(MethodArgumentNotValidException e){
        return e.getFieldErrors()
                .stream()
                .map(ValidationError::new)//reference method
                .toList();
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleDataIntegrityViolation(DataIntegrityViolationException e) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Violação de integridade de dados");
        error.put("message", e.getLocalizedMessage());
        return error;
    }

    @ExceptionHandler(InvalidYearException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> InvalidYear(InvalidYearException e) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Ano inválido");
        error.put("message", e.getMessage());
        return error;
    }

    @ExceptionHandler(PatioNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> patioNotFound(PatioNotFoundException e) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Patio informado não existe");
        error.put("message", e.getMessage());
        return error;
    }

    @ExceptionHandler(OperadorNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> operadorNotFound(OperadorNotFoundException e) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Operador informado não existe");
        error.put("message", e.getMessage());
        return error;
    }

    @ExceptionHandler(MotoNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> motoNotFound(MotoNotFoundException e) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Moto informada não foi encontrada");
        error.put("message", e.getMessage());
        return error;
    }

    @ExceptionHandler(PosicaoNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> posicaoNotFound(PosicaoNotFoundException e) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Posição informada não foi encontrada");
        error.put("message", e.getMessage());
        return error;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleConstraintViolation(ConstraintViolationException e) {
        Map<String, String> errors = new HashMap<>();

        for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
            String field = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            errors.put(field, message);
        }

        return errors;
    }
}
