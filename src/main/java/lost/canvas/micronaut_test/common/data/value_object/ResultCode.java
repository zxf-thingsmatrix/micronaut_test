package lost.canvas.micronaut_test.common.data.value_object;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ResultCode {

    ok(0, "ok"),
    fail(4000, "fail"),
    validate_fail(4444, "validate fail: {0}"),
    unknown_exception(9999, "unknown exception");
    private final Integer code;
    private final String defaultMessage;
}
