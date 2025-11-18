package xyz.woowa.dnf.character.application.query.dto.equipment.flag;

import lombok.Builder;
import lombok.Getter;
import xyz.woowa.dnf.character.application.query.dto.common.ProfileDto;

@Builder
@Getter
public class GemDto {
    private final ProfileDto base;
    private final String baseStatusHtml;
    private final String detailStatusHtml;
    private final String otherStatusHtml;
}
