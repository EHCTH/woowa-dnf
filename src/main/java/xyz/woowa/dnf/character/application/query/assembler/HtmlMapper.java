package xyz.woowa.dnf.character.application.query.assembler;

import org.springframework.stereotype.Component;
import xyz.woowa.dnf.character.application.query.dto.common.NameValue;

import java.util.Arrays;
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
        return joinStatus(itemStatus, byCategories(StatusCategory.BASE));
    }

    public String detailStatusHtml(List<NameValue> itemStatus) {
        return joinStatus(itemStatus, byCategories(StatusCategory.DETAIL));
    }

    public String otherStatusHtml(List<NameValue> itemStatus) {
        return joinStatus(itemStatus, byCategories(StatusCategory.excludeCategories()).negate());
    }

    private String joinStatus(List<NameValue> itemStatus, Predicate<NameValue> predicate) {
        return itemStatus.stream()
                .filter(predicate)
                .map(this::formatStatus)
                .collect(Collectors.joining(NEXT_LINE_HTML));
    }

    private Predicate<NameValue> byCategories(StatusCategory... categories) {
        return stat -> Arrays.stream(categories)
                .anyMatch(statusCategory -> statusCategory.contains(stat.name()));

    }

    private String formatStatus(NameValue status) {
        return status.name() + " " + status.value();
    }

    private String toHtmlLines(String text) {
        return text.replace(NEXT_LINE, NEXT_LINE_HTML);
    }

    private enum StatusCategory {
        BASE("힘", "지능", "체력", "정신력"),
        DETAIL("공격력", "방어력", "버프력"),
        EXCLUDE("내구도", "모험가 명성", "인벤토리");

        private static final StatusCategory[] EXCLUDED_FOR_OTHER = new StatusCategory[]{BASE, DETAIL, EXCLUDE};
        private final List<String> names;

        StatusCategory(String... args) {
            this.names = List.of(args);
        }

        public static StatusCategory[] excludeCategories() {
            return EXCLUDED_FOR_OTHER;
        }

        public boolean contains(String other) {
            return names.contains(other) || names.stream().anyMatch(other::contains);
        }
    }
}
