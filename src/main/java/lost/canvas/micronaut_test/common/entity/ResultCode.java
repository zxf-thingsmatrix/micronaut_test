package lost.canvas.micronaut_test.common.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ResultCode {

    ok(0, "ok"),
    fail(4000, "fail"),
    validate_fail(4444, "validate fail: {0}");
    private final Integer code;
    private final String defaultMessage;

    public String getMessageKey() {
        return "result.code." + code;
    }
}
