package org.github.unicon.web.model;

public class BaseResponse {
    private ResultStatus status = ResultStatus.OK;
    private String message;

    public ResultStatus getStatus() {
        return status;
    }

    public void setStatus(ResultStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
