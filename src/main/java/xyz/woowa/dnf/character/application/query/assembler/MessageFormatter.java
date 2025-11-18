package xyz.woowa.dnf.character.application.query.assembler;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class MessageFormatter {
    private static final Pattern VALUE_PATTERN = Pattern.compile("\\{value(\\d+)}");
    private static final String PERCENTAGE_UNIT = "%";
    private static final String PERCENTAGE_KOREAN = "퍼센트";
    public String replaceValue(String text) {
        String replace = text.replaceAll(PERCENTAGE_UNIT, PERCENTAGE_KOREAN);
        return replace.replaceAll(VALUE_PATTERN.pattern(), "%s");
    }
    public String replacePercent(String text) {
        return text.replaceAll(PERCENTAGE_KOREAN, PERCENTAGE_UNIT);
    }
}
