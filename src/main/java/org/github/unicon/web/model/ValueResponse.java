package org.github.unicon.web.model;

public class ValueResponse<T> extends BaseResponse {
    private T value;

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public static <TT> ValueResponse<TT> build(TT value) {
        final ValueResponse<TT> response = new ValueResponse<>();
        response.setValue(value);
        return response;
    }
}
