package xyz.woowa.dnf.character.application.query.dto.equipment.flag;

import lombok.Builder;
import lombok.Getter;
import xyz.woowa.dnf.character.application.query.dto.common.ProfileDto;
import xyz.woowa.dnf.character.application.query.dto.common.ReinforceDto;

import java.util.List;

@Builder
@Getter
public class FlagDto {
    private final ProfileDto base;
    private final ReinforceDto reinforce;
    private final List<GemDto> gems;
    private final String baseStatusHtml;
    private final String detailStatusHtml;
    private final String otherStatusHtml;
}

