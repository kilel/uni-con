package org.github.unicon.web.model;

public class BaseResponse {
    private boolean isOk = true;
    private String message;

    public boolean isOk() {
        return isOk;
    }

    public void setOk(boolean ok) {
        isOk = ok;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
