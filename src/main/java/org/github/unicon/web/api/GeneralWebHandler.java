package org.github.unicon.web.api;

import org.github.unicon.web.api.model.BaseResponse;
import org.github.unicon.web.api.model.ResultStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GeneralWebHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public BaseResponse handleBadArgument(Exception cause) {
        return buildErrorResponse(cause);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public BaseResponse handleUnexpectedError(Exception cause) {
        return buildErrorResponse(cause);
    }

    private BaseResponse buildErrorResponse(Exception cause) {
        final BaseResponse response = new BaseResponse();
        response.setStatus(ResultStatus.FAIL);
        response.setMessage(cause.getMessage());
        return response;
    }

}
