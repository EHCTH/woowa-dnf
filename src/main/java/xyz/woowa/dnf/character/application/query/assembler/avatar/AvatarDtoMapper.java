package xyz.woowa.dnf.character.application.query.assembler.avatar;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.woowa.dnf.character.application.query.assembler.common.CommonValueDtoMapper;
import xyz.woowa.dnf.character.application.query.assembler.common.DtoMapper;
import xyz.woowa.dnf.character.application.query.assembler.HtmlMapper;
import xyz.woowa.dnf.character.application.query.assembler.common.EmblemColorMapper;
import xyz.woowa.dnf.character.application.query.dto.common.NameValue;
import xyz.woowa.dnf.character.application.query.dto.equipment.avatar.AvatarDto;
import xyz.woowa.dnf.character.application.query.dto.equipment.avatar.EmblemDto;
import xyz.woowa.dnf.character.application.query.dto.equipment.avatar.ProfileDto;
import xyz.woowa.dnf.character.application.query.dto.equipment.equipment.ItemDetailDto;
import xyz.woowa.dnf.character.domain.equipment.avatar.Avatar;
import xyz.woowa.dnf.character.domain.equipment.avatar.vo.Detail;
import xyz.woowa.dnf.character.domain.equipment.avatar.vo.Emblem;
import xyz.woowa.dnf.character.domain.equipment.avatar.vo.ItemProfile;
import xyz.woowa.dnf.character.domain.equipment.common.ItemName;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AvatarDtoMapper implements DtoMapper<Avatar, AvatarDto> {
    private final CommonValueDtoMapper commonValueDtoMapper;
    private final EmblemColorMapper emblemColorMapper;
    private final HtmlMapper htmlMapper;

    public AvatarDto toMap(Avatar avatar) {
        ItemName clone = avatar.getClone();
        return AvatarDto.builder()
                .base(toProfileDto(avatar))
                .clone(commonValueDtoMapper.toItemValue(clone.id(), clone.name()))
                .optionAbility(avatar.getOptionAbility())
                .emblems(toEmblemsDto(avatar.emblems()))
                .detail(toDetail(avatar))
                .build();
    }

    private ItemDetailDto toDetail(Avatar avatar) {
        Detail itemDetail = avatar.getDetail();
        List<NameValue> status = avatar.getDetail().status()
                .stream()
                .map(commonValueDtoMapper::toNameValue)
                .toList();
        String itemExplainHtml = htmlMapper.itemExplainHtml(itemDetail.itemExplain());
        String baseStatusHtml = htmlMapper.baseStatusHtml(status);
        String otherStatusHtml = htmlMapper.otherStatusHtml(status);
        String detailStatusHtml = htmlMapper.detailStatusHtml(status);
        return ItemDetailDto.builder()
                .fame(avatar.getDetail().fame())
                .itemExplainHtml(itemExplainHtml)
                .baseStatusHtml(baseStatusHtml)
                .otherStatusHtml(otherStatusHtml)
                .detailStatusHtml(detailStatusHtml)
                .build();
    }
    private List<EmblemDto> toEmblemsDto(List<Emblem> emblems) {
        return emblems.stream()
                .map(this::toEmblemDto)
                .toList();
    }
    private EmblemDto toEmblemDto(Emblem emblem) {
        ItemName base = emblem.base();
        String color = emblemColorMapper.colorMapper(emblem.emblemColor(), base.name());
        return new EmblemDto(color,
                emblem.rarity().name(),
                commonValueDtoMapper.toItemValue(base.id(), base.name()));
    }

    private ProfileDto toProfileDto(Avatar avatar) {
        ItemProfile base = avatar.getBase();
        return new ProfileDto(base.slotName(),
                base.getRarity(),
                commonValueDtoMapper.toItemValue(base.id(), base.name()),
                avatar.getDetail().fame());
    }

}
