package it.jump3.urbi.exception;

import brave.Span;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.List;
import java.util.UUID;

@Slf4j
public class ErrorUtils {

    public static ErrorResponse getResponse(MessageSource messageSource, Span span, int code, Exception ex) {

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());

        setErrorResponse(messageSource, span, code, ex, errorResponse);

        return errorResponse;
    }

    public static ErrorResponse getResponse(MessageSource messageSource, Span span, int code, List<String> msgs, Exception ex) {

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessages(msgs);

        setErrorResponse(messageSource, span, code, ex, errorResponse);

        return errorResponse;
    }

    private static void setErrorResponse(MessageSource messageSource, Span span, int code, Exception ex, ErrorResponse errorResponse) {

        setCorrelationId(span, errorResponse);
        errorResponse.setCode(code);
        errorResponse.setExceptionType(ex.getClass().getName());
        errorResponse.setErrorSourceSystem(messageSource.getMessage("application.name", null, LocaleContextHolder.getLocale()));

        log.error(errorResponse.getMessage());
    }

    private static void setCorrelationId(Span span, ErrorResponse errorResponse) {
        errorResponse.setCorrelationId(span != null && span.context() != null ?
                span.context().spanIdString() :
                UUID.randomUUID().toString());
    }
}
