package xyz.woowa.dnf.character.application.query.assembler.creature;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.woowa.dnf.character.application.query.assembler.common.CommonValueDtoMapper;
import xyz.woowa.dnf.character.application.query.assembler.common.DtoMapper;
import xyz.woowa.dnf.character.application.query.assembler.HtmlMapper;
import xyz.woowa.dnf.character.application.query.dto.common.ItemValue;
import xyz.woowa.dnf.character.application.query.dto.common.NameValue;
import xyz.woowa.dnf.character.application.query.dto.equipment.creature.ArtifactDto;
import xyz.woowa.dnf.character.application.query.dto.equipment.creature.CreatureDto;
import xyz.woowa.dnf.character.application.query.dto.equipment.creature.CreatureSkillDto;
import xyz.woowa.dnf.character.application.query.dto.equipment.creature.ProfileDto;
import xyz.woowa.dnf.character.application.query.dto.equipment.equipment.ItemDetailDto;
import xyz.woowa.dnf.character.domain.equipment.common.ItemName;
import xyz.woowa.dnf.character.domain.equipment.common.Status;
import xyz.woowa.dnf.character.domain.equipment.creature.Creature;
import xyz.woowa.dnf.character.domain.equipment.creature.vo.*;

import java.util.List;
@Component
@RequiredArgsConstructor
public class CreatureDtoMapper implements DtoMapper<Creature, CreatureDto> {
    private final CommonValueDtoMapper mapper;
    private final HtmlMapper htmlMapper;
    @Override
    public CreatureDto toMap(Creature creature) {
        ItemName cloneV = creature.getClone();
        ItemDetailDto detail = toDetail(creature.getDetail().status());
        ItemValue clone = mapper.toItemValue(cloneV.id(), cloneV.name());
        ProfileDto base = toProfile(creature);
        List<ArtifactDto> artifacts = toArtifacts(creature.getArtifacts());
        CreatureSkillDto creatureSkill = toCreatureSkill(creature.getDetail());
        return CreatureDto.builder()
                .base(base)
                .clone(clone)
                .detail(detail)
                .artifacts(artifacts)
                .creatureSkill(creatureSkill)
                .build();
    }
    
    private CreatureSkillDto toCreatureSkill(Detail detail) {
        CreatureSkill skill = detail.skill();
        CreatureSkill overSkill = detail.overSkill();
        return new CreatureSkillDto(skill.name(), skill.description(), overSkill.name(), overSkill.description());
    }
    private ProfileDto toProfile(Creature creature) {
        CreatureProfile base = creature.getBase();
        ItemName itemName = base.itemName();
        return new ProfileDto(mapper.toItemValue(itemName.id(), itemName.name()), base.rarity().name(), base.fame());
    }
    private ProfileDto toProfile(Artifact artifact) {
        return new ProfileDto(mapper.toItemValue(artifact.id(), artifact.name()), artifact.getRarity(), artifact.fame());
    }

    private List<ArtifactDto> toArtifacts(Artifacts artifacts) {
        return artifacts.artifact().stream()
                .map(this::toArtifact)
                .toList();

    }

    private ArtifactDto toArtifact(Artifact artifact) {
        List<NameValue> status = artifact.status()
                .stream()
                .map(mapper::toNameValue)
                .toList();
        String baseStatusHtml = htmlMapper.baseStatusHtml(status);
        String detailStatusHtml = htmlMapper.detailStatusHtml(status);
        String otherStatusHtml = htmlMapper.otherStatusHtml(status);
        return new ArtifactDto(
                artifact.color(),
                toProfile(artifact),
                artifact.detailType(),
                baseStatusHtml,
                detailStatusHtml,
                otherStatusHtml
        );
    }

    private ItemDetailDto toDetail(List<Status> status) {
        List<NameValue> nameValues = status.stream()
                .map(mapper::toNameValue)
                .toList();
        return ItemDetailDto.builder()
                .baseStatusHtml(htmlMapper.baseStatusHtml(nameValues))
                .detailStatusHtml(htmlMapper.detailStatusHtml(nameValues))
                .otherStatusHtml(htmlMapper.otherStatusHtml(nameValues))
                .build();
    }
}
