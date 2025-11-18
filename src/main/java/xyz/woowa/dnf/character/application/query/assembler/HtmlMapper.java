package xyz.woowa.dnf.character.application.query.assembler;

import org.springframework.stereotype.Component;
import xyz.woowa.dnf.character.application.query.dto.common.NameValue;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class HtmlMapper {
    private static final Set<String> BASE_STATUS = Set.of("힘", "지능", "체력", "정신력");
    private static final Set<String> DETAIL_STATUS = Set.of("물리 공격력", "마법 공격력", "독립 공격력", "물리 방어력", "마법 방어력");

    public String itemExplainHtml(String itemExplain) {
        if (itemExplain.isEmpty()) {
            return itemExplain;
        }
        return itemExplain.replace("\n", "<br>");
    }

    public String baseStatusHtml(List<NameValue> itemStatus) {
        return String.join("<br>", itemStatus.stream()
                .filter(nameValue -> BASE_STATUS.contains(nameValue.name()))
                .map(nameValue -> nameValue.name() + " " + nameValue.value())
                .toList());
    }

    public String detailStatusHtml(List<NameValue> itemStatus) {
        return String.join("<br>", itemStatus.stream()
                .filter(nameValue -> DETAIL_STATUS.contains(nameValue.name()))
                .map(nameValue -> nameValue.name() + " " + nameValue.value())
                .toList());
    }

    public String otherStatusHtml(List<NameValue> itemStatus) {
        Set<String> otherStatus = itemStatus.stream()
                .map(NameValue::name)
                .collect(Collectors.toSet());
        otherStatus.removeAll(Set.of("내구도", "모험가 명성"));
        otherStatus.removeAll(BASE_STATUS);
        otherStatus.removeAll(DETAIL_STATUS);
        return String.join("<br>", itemStatus.stream()
                .filter(nameValue -> otherStatus.contains(nameValue.name()))
                .map(nameValue -> nameValue.name() + " " + nameValue.value())
                .toList());

    }

    public String buffExplainHtml(String buffExplain) {
        if (buffExplain.isEmpty()) {
            return buffExplain;
        }
        return "<버퍼 전용 옵션><br>".concat(buffExplain.replace("\n", "<br>"));
    }

}
