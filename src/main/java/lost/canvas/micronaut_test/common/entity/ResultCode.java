package lost.canvas.micronaut_test.common.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ResultCode {

    OK(2000),
    FAIL(4000);
    private final Integer code;
}
