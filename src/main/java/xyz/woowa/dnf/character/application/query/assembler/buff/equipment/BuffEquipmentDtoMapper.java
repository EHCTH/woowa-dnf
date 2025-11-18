package xyz.woowa.dnf.character.application.query.assembler.buff.equipment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.woowa.dnf.character.application.query.assembler.MessageFormatter;
import xyz.woowa.dnf.character.application.query.assembler.common.CommonValueDtoMapper;
import xyz.woowa.dnf.character.application.query.assembler.common.DtoMapper;
import xyz.woowa.dnf.character.application.query.assembler.HtmlMapper;
import xyz.woowa.dnf.character.application.query.dto.common.NameValue;
import xyz.woowa.dnf.character.application.query.dto.equipment.avatar.ProfileDto;
import xyz.woowa.dnf.character.application.query.dto.equipment.buff.equipment.BuffEquipItemDto;
import xyz.woowa.dnf.character.application.query.dto.equipment.buff.equipment.BuffEquipmentDto;
import xyz.woowa.dnf.character.application.query.dto.equipment.buff.equipment.SkillInfoDto;
import xyz.woowa.dnf.character.domain.equipment.avatar.vo.Detail;
import xyz.woowa.dnf.character.domain.equipment.buff.equipment.BuffEquipItem;
import xyz.woowa.dnf.character.domain.equipment.buff.equipment.BuffEquipment;
import xyz.woowa.dnf.character.domain.equipment.buff.equipment.SkillInfo;
import xyz.woowa.dnf.character.domain.equipment.common.Status;
import xyz.woowa.dnf.character.domain.equipment.equipment.vo.ItemProfile;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BuffEquipmentDtoMapper implements DtoMapper<BuffEquipment, BuffEquipmentDto> {
    private final CommonValueDtoMapper mapper;
    private final HtmlMapper htmlMapper;
    private final MessageFormatter messageFormatter;
    @Override
    public BuffEquipmentDto toMap(BuffEquipment buffEquipment) {
        SkillInfoDto skillInfoDto = toSkillInfoDto(buffEquipment.getSkillInfo());
        return BuffEquipmentDto.builder()
                .skillInfo(skillInfoDto)
                .buffEquipItems(toBuffEquipItems(buffEquipment.getBuffEquipItems()))
                .build();

    }
    private SkillInfoDto toSkillInfoDto(SkillInfo skillInfo) {
        String replaceDesc = messageFormatter.replaceValue(skillInfo.dsec());
        String description = htmlMapper.itemExplainHtml(replaceDesc);
        String descriptionFormat = descriptionFormat(skillInfo, description);
        String replacePercent = messageFormatter.replacePercent(descriptionFormat);
        return new SkillInfoDto(skillInfo.name(), skillInfo.level(), replacePercent);
    }

    private static String descriptionFormat(SkillInfo skillInfo, String description) {
        return String.format(description, skillInfo.values().toArray());
    }

    private List<BuffEquipItemDto> toBuffEquipItems(List<BuffEquipItem> buffEquipItems) {
        return buffEquipItems.stream()
                .map(this::toBuffEquipItem)
                .toList();
    }
    private BuffEquipItemDto toBuffEquipItem(BuffEquipItem buffEquipItem) {
        ItemProfile base = buffEquipItem.getBase();
        Detail itemDetail = buffEquipItem.getDetail();
        Status skills = buffEquipItem.getSkills();
        List<NameValue> status = itemDetail.status()
                .stream()
                .map(mapper::toNameValue)
                .toList();
        return BuffEquipItemDto.builder()
                .base(toProfileDto(base, itemDetail))
                .skill(mapper.toNameValue(skills.name(), skills.value()))
                .detail(mapper.toItemValue(base.getDetailId(), base.getDetailName()))
                .baseStatusHtml(htmlMapper.baseStatusHtml(status))
                .detailStatusHtml(htmlMapper.detailStatusHtml(status))
                .otherStatusHtml(htmlMapper.otherStatusHtml(status))
                .itemExplainHtml(htmlMapper.itemExplainHtml(buffEquipItem.getDetail().itemExplain()))
                .build();

    }

    private ProfileDto toProfileDto(ItemProfile base, Detail itemDetail) {
        return new ProfileDto(
                base.slot().getDisplayName(),
                base.getRarity(),
                mapper.toItemValue(base.getId(), base.getName()),
                itemDetail.fame());
    }


}
