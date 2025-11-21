package xyz.woowa.dnf.character.application.query.assembler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.woowa.dnf.character.application.query.dto.common.NameValue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
public class HtmlMapper {
    private static final String NEXT_LINE = System.lineSeparator();
    private static final String NEXT_LINE_HTML = "<br>";
    private static final String BUFF_PREFIX = "<버퍼 전용 옵션>";

    public String itemExplainHtml(String itemExplain) {
        if (itemExplain.isEmpty()) {
            return itemExplain;
        }
        return toHtmlLines(itemExplain);
    }

    public String buffExplainHtml(String buffExplain) {
        if (buffExplain.isEmpty()) {
            return buffExplain;
        }
        return BUFF_PREFIX + NEXT_LINE_HTML + toHtmlLines(buffExplain);
    }

    public String baseStatusHtml(List<NameValue> itemStatus) {
        return joinByIncludeCategory(itemStatus, StatusCategory.BASE);
    }

    public String detailStatusHtml(List<NameValue> itemStatus) {
        return joinByIncludeCategory(itemStatus, StatusCategory.DETAIL);
    }

    public String otherStatusHtml(List<NameValue> itemStatus) {
        return joinByExcludedCategories(itemStatus, StatusCategory.BASE, StatusCategory.DETAIL, StatusCategory.EXCLUDE);
    }

    private String joinByExcludedCategories(List<NameValue> itemStatus, StatusCategory... excludedCategories) {
        return itemStatus.stream()
                .filter(filterHandler(excludedCategories).negate())
                .map(this::formatStatus)
                .collect(Collectors.joining(NEXT_LINE_HTML));
    }

    private String joinByIncludeCategory(List<NameValue> itemStatus, StatusCategory... categories) {
        return itemStatus.stream()
                .filter(filterHandler(categories))
                .map(this::formatStatus)
                .collect(Collectors.joining(NEXT_LINE_HTML));
    }

    private Predicate<NameValue> filterHandler(StatusCategory... categories) {
        return stat -> Arrays.stream(categories)
                .anyMatch(statusCategory -> statusCategory.contains(stat.name()));

    }

    private String formatStatus(NameValue status) {
        return status.name() + " " + status.value();
    }

    private String toHtmlLines(String text) {
        return text.replace(NEXT_LINE, NEXT_LINE_HTML);
    }

    @RequiredArgsConstructor
    private enum StatusCategory {
        BASE(List.of("힘", "지능", "체력", "정신력")),
        DETAIL(List.of("물리 공격력", "마법 공격력", "독립 공격력", "물리 방어력", "마법 방어력")),
        EXCLUDE(List.of("내구도", "모험가 명성"));

        private final List<String> names;

        public boolean contains(String name) {
            return names.contains(name);
        }
    }
}
