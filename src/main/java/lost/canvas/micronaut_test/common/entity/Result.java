package lost.canvas.micronaut_test.common.entity;

import lombok.Getter;

@Getter
public class Result<T> {

    private final Integer code;

    private final String message;

    private final T data;

    public Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> Result<T> ok(T data) {
        return new Result<T>(ResultCode.ok.getCode(), ResultCode.ok.getDefaultMessage(), data);
    }

    public static Result<Void> fail(ResultCode resultCode) {
        return new Result<Void>(resultCode.getCode(), resultCode.getDefaultMessage(), null);
    }

}
