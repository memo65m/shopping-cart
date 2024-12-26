package com.indra.shoppingcart.infrastructure.adapter.input.rest.exception;

import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.regex.Pattern;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.indra.shoppingcart.domain.exception.BadRequestException;
import com.indra.shoppingcart.domain.exception.ConflictException;
import com.indra.shoppingcart.domain.exception.ForbiddenException;
import com.indra.shoppingcart.domain.exception.NotFoundException;
import com.indra.shoppingcart.infrastructure.adapter.input.dto.response.ExceptionResponseDto;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;

@ControllerAdvice
public class ApiExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({ NotFoundException.class, FileNotFoundException.class })
    @ResponseBody
    public ExceptionResponseDto notFoundRequest(HttpServletRequest request, Exception exception) {
        return new ExceptionResponseDto(exception.getMessage(), request.getRequestURI());
    }

    @ResponseStatus(HttpStatus.INSUFFICIENT_STORAGE)
    @ExceptionHandler({ FileUploadException.class })
    @ResponseBody
    public ExceptionResponseDto fileStorageRequest(HttpServletRequest request, Exception exception) {
        return new ExceptionResponseDto(exception.getMessage(), request.getRequestURI());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ org.springframework.dao.DuplicateKeyException.class,
            org.springframework.dao.DataAccessException.class, java.sql.SQLException.class,
            org.springframework.dao.EmptyResultDataAccessException.class,
            org.springframework.web.bind.MethodArgumentNotValidException.class,
            org.springframework.web.bind.MissingRequestHeaderException.class,
            org.springframework.web.bind.MissingServletRequestParameterException.class,
            org.springframework.web.method.annotation.MethodArgumentTypeMismatchException.class,
            org.springframework.http.converter.HttpMessageNotReadableException.class,
            MethodArgumentNotValidException.class,
            InvalidDataAccessApiUsageException.class,
            NullPointerException.class,
            BadRequestException.class
    })
    @ResponseBody
    public ExceptionResponseDto badRequest(HttpServletRequest request, Exception exception) {
        String message = getMessage(exception);
        return new ExceptionResponseDto(message, request.getRequestURI());
    }

    private String getMessage(Exception exception) {

        String message = exception.getMessage();
        StringBuilder errors = new StringBuilder();

        if (exception instanceof MethodArgumentNotValidException) {
            ((MethodArgumentNotValidException) exception).getBindingResult().getAllErrors().forEach(error -> {
                String errorMessage = error.getDefaultMessage();
                if (errorMessage != null) {
                    errorMessage = errorMessage.trim();
                    char lastLetterErrorMessage = errorMessage.charAt(errorMessage.length() - 1);
                    if (lastLetterErrorMessage != '.')
                        errorMessage = String.format("%s. ", errorMessage);
                }
                errors.append(errorMessage);
            });
            message = errors.toString();
        }

        if (exception instanceof jakarta.validation.ConstraintViolationException) {
            for (ConstraintViolation<?> violation : ((jakarta.validation.ConstraintViolationException) exception)
                    .getConstraintViolations()) {
                String queryParamPath = violation.getPropertyPath().toString();
                String field = splitStringAndGetLastElement(queryParamPath, ".");
                errors.append(String.format("%s : %s. ", field, violation.getMessage()));
            }
            message = errors.toString();
        }

        return message;

    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({ ConflictException.class, DataIntegrityViolationException.class })
    @ResponseBody
    public ExceptionResponseDto conflictRequest(HttpServletRequest request, Exception exception) {
        String message = exception.getMessage();
        return new ExceptionResponseDto(message, request.getRequestURI());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({ Exception.class, ServletException.class })
    @ResponseBody
    public ExceptionResponseDto fatalErrorUnexpectedException(HttpServletRequest request, Exception exception) {
        return new ExceptionResponseDto(exception.getMessage(), request.getRequestURI());
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler({ org.springframework.web.HttpRequestMethodNotSupportedException.class })
    @ResponseBody
    public ExceptionResponseDto methodNotAllowed(HttpServletRequest request, Exception exception) {
        return new ExceptionResponseDto(exception.getMessage(), request.getRequestURI());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler({ ForbiddenException.class })
    @ResponseBody
    public ExceptionResponseDto forbiddenRequest(HttpServletRequest request, Exception exception) {
        return new ExceptionResponseDto(exception.getMessage(), request.getRequestURI());
    }

    private static String splitStringAndGetLastElement(String value, String split) {
        Objects.requireNonNull(split, "split cannot be null");
        if (value != null && !value.isEmpty() && Boolean.TRUE.equals(value.contains(split))) {
            String[] parts = value.split(Pattern.quote(split));
            value = parts[parts.length - 1];
        }

        return value;
    }

}
