package org.github.unicon.web;

import org.github.unicon.web.model.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GeneralWebHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public BaseResponse handleBadArgument(Exception cause) {
        return buildErrorResponse(cause);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public BaseResponse handleUnexpectedError(Exception cause) {
        return buildErrorResponse(cause);
    }

    private BaseResponse buildErrorResponse(Exception cause) {
        final BaseResponse response = new BaseResponse();
        response.setOk(false);
        response.setMessage(cause.getMessage());
        return response;
    }

}
