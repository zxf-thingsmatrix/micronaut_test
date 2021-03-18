package lost.canvas.micronaut_test.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CompanyTypeEnum {

    normal(0), distribute(1), sub_company(2);
    private final Integer code;
}
