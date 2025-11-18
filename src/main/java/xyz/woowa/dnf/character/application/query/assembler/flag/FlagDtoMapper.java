package xyz.woowa.dnf.character.application.query.assembler.flag;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.woowa.dnf.character.application.query.assembler.HtmlMapper;
import xyz.woowa.dnf.character.application.query.assembler.common.CommonValueDtoMapper;
import xyz.woowa.dnf.character.application.query.assembler.common.DtoMapper;
import xyz.woowa.dnf.character.application.query.dto.common.NameValue;
import xyz.woowa.dnf.character.application.query.dto.common.ProfileDto;
import xyz.woowa.dnf.character.application.query.dto.common.ReinforceDto;
import xyz.woowa.dnf.character.application.query.dto.equipment.flag.FlagDto;
import xyz.woowa.dnf.character.application.query.dto.equipment.flag.GemDto;
import xyz.woowa.dnf.character.domain.equipment.avatar.vo.Detail;
import xyz.woowa.dnf.character.domain.equipment.common.Status;
import xyz.woowa.dnf.character.domain.equipment.equipment.vo.ItemProfile;
import xyz.woowa.dnf.character.domain.equipment.equipment.vo.ReinForce;
import xyz.woowa.dnf.character.domain.equipment.flag.Flag;
import xyz.woowa.dnf.character.domain.equipment.flag.vo.Gem;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FlagDtoMapper implements DtoMapper<Flag, FlagDto> {
    private final CommonValueDtoMapper mapper;
    private final HtmlMapper htmlMapper;
    @Override
    public FlagDto toMap(Flag flag) {
        ReinForce reinForce = flag.getReinForce();
        List<NameValue> status = flag.getDetail().status()
                .stream()
                .map(mapper::toNameValue)
                .toList();
        return FlagDto.builder()
                .base(toProfileDto(flag.getBase(), flag.getDetail()))
                .reinforce(toReinforce(reinForce.name(), reinForce.value()))
                .gems(toGemsDto(flag.getGems()))
                .baseStatusHtml(htmlMapper.baseStatusHtml(status))
                .detailStatusHtml(htmlMapper.detailStatusHtml(status))
                .otherStatusHtml(htmlMapper.otherStatusHtml(status))
                .build();
    }
    private ReinforceDto toReinforce(String name, String value) {
        if (name.equals("강화")) {
            return ReinforceDto.enforce(value);
        }
        return ReinforceDto.dimension(name, value);
    }

    private List<GemDto> toGemsDto(List<Gem> gems) {
        return gems.stream()
                .map(this::gemDto)
                .toList();
    }
    private GemDto gemDto(Gem gem) {
        List<NameValue> status = gem.detail().status().stream()
                .map(mapper::toNameValue)
                .toList();
        return GemDto.builder()
                .base(toProfileDto(gem.base(), gem.detail()))
                .baseStatusHtml(htmlMapper.baseStatusHtml(status))
                .detailStatusHtml(htmlMapper.detailStatusHtml(status))
                .otherStatusHtml(htmlMapper.otherStatusHtml(status))
                .build();
    }

    private ProfileDto toProfileDto(ItemProfile base, Detail detail) {
        return new ProfileDto(
                base.slot().getDisplayName(),
                base.getRarity(),
                mapper.toItemValue(base.getId(), base.getName()),
                mapper.toItemValue(base.getDetailId(), base.getDetailName()),
                detail.fame()
        );
    }
}
