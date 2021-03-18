package lost.canvas.micronaut_test.common.exception;

import lombok.Getter;
import lost.canvas.micronaut_test.common.data.value_object.ResultCode;
import lost.canvas.micronaut_test.common.util.Utils;

import java.util.Map;

@Getter
public class ServiceException extends RuntimeException {

    private final ResultCode resultCode;

    private final Map<String, Object> variables;


    public ServiceException(ResultCode resultCode, Map<String, Object> variables,
                            Throwable cause,
                            boolean enableSuppression,
                            boolean writableStackTrace) {
        super(Utils.message.interpolate(resultCode.getDefaultMessage(), variables), cause, enableSuppression, writableStackTrace);
        this.resultCode = resultCode;
        this.variables = variables;
    }

    public ServiceException(ResultCode resultCode, Map<String, Object> variables,
                            Throwable cause) {
        this(resultCode, variables, cause, false, true);
    }

    public ServiceException(ResultCode resultCode, Throwable cause) {
        this(resultCode, null, cause);
    }


}
