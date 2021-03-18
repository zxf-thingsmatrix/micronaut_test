package lost.canvas.micronaut_test.common.util;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamUtil {

    StreamUtil() {
    }

    public <S, K, T> void complete(Stream<S> source,
                                   Function<S, K> keyInSource,
                                   Function<Set<K>, Stream<T>> fetchTargetByKeys,
                                   Function<T, K> keyInTarget,
                                   BiConsumer<S, T> completion) {
        Objects.requireNonNull(source, "source");
        Objects.requireNonNull(keyInSource, "keyInSource");
        Objects.requireNonNull(fetchTargetByKeys, "fetchTargetByKeys");
        Objects.requireNonNull(keyInTarget, "keyInTarget");
        Objects.requireNonNull(completion, "completion");

        Map<K, List<S>> sourceByKey = source.filter(e -> nonNullOrNotBlank(keyInSource.apply(e))).collect(Collectors.groupingBy(keyInSource));

        Set<K> keys = sourceByKey.keySet();

        Stream<T> target = fetchTargetByKeys.apply(keys);

        target.forEach(t -> {
            K key = keyInTarget.apply(t);
            List<S> s = sourceByKey.get(key);
            if (Utils.empty.nonEmpty(s)) {
                s.forEach(ss -> {
                    completion.accept(ss, t);
                });
            }
        });

    }

    private boolean nonNullOrNotBlank(Object obj) {
        if (Objects.isNull(obj)) return false;
        return !CharSequence.class.isAssignableFrom(obj.getClass())
                || Utils.string.nonBlank((CharSequence) obj);
    }
}
