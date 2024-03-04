package br.com.azship.gestaofretesapi.modulos.comum.controller;

import br.com.azship.gestaofretesapi.modulos.comum.dto.ErrorMessage;
import br.com.azship.gestaofretesapi.modulos.comum.exception.NotFoundException;
import br.com.azship.gestaofretesapi.modulos.comum.exception.ValidacaoException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ExceptionHandlingController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public List<ErrorMessage> handleBeanValidationException(Exception ex) {
        var result = ex instanceof MethodArgumentNotValidException manvex
                ? manvex.getBindingResult()
                : ((BindException) ex).getBindingResult();

        return result.getFieldErrors()
                .stream()
                .map(this::getErrorMessage)
                .toList();
    }

    @ExceptionHandler(ValidacaoException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleValidacaoException(ValidacaoException ex) {
        return new ErrorMessage(ex.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage handleNotFoundException(NotFoundException ex) {
        return new ErrorMessage(ex.getMessage());
    }

    private ErrorMessage getErrorMessage(FieldError fieldError) {
        return new ErrorMessage(
                String.format("O campo %s %s", fieldError.getField(), fieldError.getDefaultMessage()),
                fieldError.getField());
    }
}
