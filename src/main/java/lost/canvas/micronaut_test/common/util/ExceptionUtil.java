package lost.canvas.micronaut_test.common.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Objects;

public final class ExceptionUtil {

    ExceptionUtil() {
    }

    public String toString(Throwable t) {
        if (Objects.isNull(t)) return null;
        try (StringWriter sw = new StringWriter();
             PrintWriter pw = new PrintWriter(sw)) {
            t.printStackTrace(pw);
            return sw.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
