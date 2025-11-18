package xyz.woowa.dnf.common;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xyz.woowa.dnf.common.exception.DomainException;

import java.util.Locale;

@RestControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionHandler {
    private final MessageSource messageSource;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DomainException.class)
    public ErrorResponse handleServerException(DomainException e, Locale locale) {
        String msg = messageSource.getMessage(e.getCode(), e.getArgs(), locale);
        return new ErrorResponse("INVALID_ARGUMENT", msg);
    }

    public record ErrorResponse(String code, String message) {}
}
