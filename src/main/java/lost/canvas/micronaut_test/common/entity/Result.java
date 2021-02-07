package lost.canvas.micronaut_test.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@Getter
public class Result<T> {

    private final Integer code;

    private final String message;

    private final T data;

    private final Map<String, Object> variables;

    public Result(Integer code, String message, T data, Map<String, Object> variables) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.variables = variables;
    }

    public Result(Integer code, String message, T data) {
        this(code, message, data, null);
    }

    public Result(Integer code, String message, Map<String, Object> variables) {
        this(code, message, null, variables);
    }

    public static <T> Result<T> ok(T data) {
        return new Result<T>(ResultCode.ok.getCode(), ResultCode.ok.getDefaultMessage(), data);
    }

    public static Result<Void> fail(ResultCode resultCode) {
        return new Result<>(resultCode.getCode(), resultCode.getDefaultMessage(), null);
    }

    public static Result<Void> fail(ResultCode resultCode, Map<String, Object> variables) {
        return new Result<>(resultCode.getCode(), resultCode.getDefaultMessage(), variables);
    }

    @JsonIgnore
    public boolean isOk() {
        return Objects.equals(ResultCode.ok.getCode(), code);
    }

    @JsonIgnore
    public boolean isFail() {
        return !isOk();
    }

    @JsonIgnore
    public String getMessageKey() {
        return "result.code." + code;
    }

    public Result<T> localize(BiFunction<String[], Map<String, Object>, String> fn) {
        //消息变量本地化
        Map<String, Object> localizedVariables = variables.entrySet().stream()
                .collect(Collectors.toMap(e -> e.getKey(), e -> fn.apply(new String[]{e.getValue().toString(), e.getValue().toString()}, null)));
        //消息模板本地化
        String localizeMessage = fn.apply(new String[]{this.getMessageKey(), this.getMessage()}, localizedVariables);
        return new Result<>(this.getCode(), localizeMessage, this.getData(), localizedVariables);
    }

}
