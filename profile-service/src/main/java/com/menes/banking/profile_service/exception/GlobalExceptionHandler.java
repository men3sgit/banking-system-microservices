package com.menes.banking.profile_service.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ValidationException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String CHECKING_NON_NULL_REQUIRED_FIELD = "non-null but is null";

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Map<String, List<Object>> handleException(HttpServletRequest request, Exception e) {
        log.error("method: handleException, endpoint: {}, queryString: {}",
                request.getRequestURI(),
                request.getParameterMap(),
                e);
        if (isMissingRequiredField(e)) {
            return ErrorResponse.getErrorResponse(DomainCode.MISSING_REQUIRED_FIELD.toUniversalCode(),
                    DomainCode.MISSING_REQUIRED_FIELD.getMessage());
        }

        return ErrorResponse.getErrorResponse(DomainCode.GENERIC_ERROR.toUniversalCode(),
                DomainCode.GENERIC_ERROR.getMessage());
    }

    private boolean isMissingRequiredField(Exception e) {
        return e.getCause() == null || e.getCause().getMessage().contains(CHECKING_NON_NULL_REQUIRED_FIELD);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DomainException.class)
    public Map<String, List<Object>> handleDomainException(HttpServletRequest request, DomainException e) {
        log.error("method: handleDomainException, endpoint: {}", request.getRequestURI(), e);
        return ErrorResponse.getErrorResponse(e.getDomainCode().toUniversalCode(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {ValidationException.class})
    public Map<String, List<Object>> handleValidationException(ValidationException exception) {
        if (exception.getCause() instanceof DomainException) {
            return ErrorResponse.getErrorResponse(((DomainException) exception.getCause()).getDomainCode().toUniversalCode(),
                    exception.getCause().getMessage());
        }
        return ErrorResponse.getErrorResponse(DomainCode.GENERIC_ERROR.toUniversalCode(),
                DomainCode.GENERIC_ERROR.getMessage());
    }

    public static class ErrorResponse {

        private ErrorResponse() {
            // Prevent create Error response without any information
        }

        public static Map<String, List<Object>> getErrorResponse(String errorCode, String errorMessage) {
            Map<String, List<Object>> response = new HashMap<>();
            String errorKey = "errors";
            ErrorDetail errorDetail = new ErrorDetail(errorCode, errorMessage);
            response.put(errorKey, Collections.singletonList(errorDetail));
            return response;
        }

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class ErrorDetail {

            private String errorCode;

            private String errorMessage;
        }
    }
}