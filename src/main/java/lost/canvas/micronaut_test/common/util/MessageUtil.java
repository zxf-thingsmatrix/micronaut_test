package lost.canvas.micronaut_test.common.util;

import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public final class MessageUtil {

    private final Pattern variable_pattern = Pattern.compile("\\{(.+)?\\}");


    MessageUtil() {
    }


    public String formatMessage(String messageTemplate, Map<String, Object> variables) {
        if (Utils.empty.isEmpty(variables)) return messageTemplate;

        StringBuffer sb = new StringBuffer();
        Matcher matcher = variable_pattern.matcher(messageTemplate);
        while (matcher.find()) {
            String group = matcher.group(1);
            if (log.isDebugEnabled()) {
                log.debug("RegEx[{}] parse message_template[{}] variable: {}", variables, messageTemplate, group);
            }
            Object value = variables.get(group);
            if (Objects.nonNull(value)) {
                matcher.appendReplacement(sb, value.toString());
            }
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    public Map<String, Object> convertVariables(Object... variables) {
        if (Utils.empty.isEmpty(variables)) return Collections.emptyMap();

        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < variables.length; i++) {
            map.put(i + "", variables[i]);
        }
        return map;
    }
}
