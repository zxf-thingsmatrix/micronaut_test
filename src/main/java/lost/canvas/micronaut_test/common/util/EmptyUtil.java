package lost.canvas.micronaut_test.common.util;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

public final class EmptyUtil {

    EmptyUtil() {
    }

    ////////////////////////////////////////////////////////////////////////////////////////
    //  is-empty
    ////////////////////////////////////////////////////////////////////////////////////////

    public boolean isEmpty(Object[] objs) {
        return Objects.isNull(objs) || objs.length < 1;
    }

    public boolean isEmpty(Collection<?> collection) {
        return Objects.isNull(collection) || collection.isEmpty();
    }

    public boolean isEmpty(Map<?, ?> map) {
        return Objects.isNull(map) || map.isEmpty();
    }

    public boolean isEmpty(CharSequence charSequence) {
        return Objects.isNull(charSequence) || charSequence.length() < 1;
    }

    ////////////////////////////////////////////////////////////////////////////////////////
    //  non-empty
    ////////////////////////////////////////////////////////////////////////////////////////

    public boolean nonEmpty(Object[] objs) {
        return !isEmpty(objs);
    }

    public boolean nonEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    public boolean nonEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    public boolean nonEmpty(CharSequence charSequence) {
        return !isEmpty(charSequence);
    }
}
