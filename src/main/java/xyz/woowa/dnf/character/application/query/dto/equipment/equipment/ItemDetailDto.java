package xyz.woowa.dnf.character.application.query.dto.equipment.equipment;

import lombok.Builder;
import xyz.woowa.dnf.character.domain.equipment.equipment.vo.Tune;

@Builder
public record ItemDetailDto(String fame,
                            String itemFlavorText,
                            String itemExplainHtml,
                            String baseStatusHtml,
                            String detailStatusHtml,
                            String otherStatusHtml,
                            String buffExplainHtml,
                            Tune tune) {
}
