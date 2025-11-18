package xyz.woowa.dnf.character.application.query.assembler.creature;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.woowa.dnf.character.application.query.assembler.common.CommonValueMapper;
import xyz.woowa.dnf.character.application.query.port.outbound.equipment.ExternalCreaturePort;
import xyz.woowa.dnf.character.domain.equipment.common.ItemName;
import xyz.woowa.dnf.character.domain.equipment.common.Rarity;
import xyz.woowa.dnf.character.domain.equipment.common.Status;
import xyz.woowa.dnf.character.domain.equipment.creature.Creature;
import xyz.woowa.dnf.character.domain.equipment.creature.vo.*;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CreatureMapper {
    private final CommonValueMapper commonValueMapper;

    public Creature toCreature(ExternalCreaturePort.Creature creature) {
        ExternalCreaturePort.Clone clone = creature.clonCreature();
        return Creature.builder()
                .base(toCreatureProfile(creature))
                .clone(toClone(clone))
                .artifacts(toArtifacts(creature.artifact()))
                .detail(toDetail(creature.detail()))
                .build();

    }

    private ItemName toClone(ExternalCreaturePort.Clone clone) {
        if (clone == null) {
            return ItemName.EMPTY;
        }
        return commonValueMapper.toItemName(clone.itemId(), clone.itemName());
    }

    private Detail toDetail(ExternalCreaturePort.Detail detail) {
        CreatureSkill creatureInfo = getCreatureSkill(detail);
        CreatureSkill overSkill = getOverSkill(detail);
        List<Status> status = detail.itemStatus().stream()
                .map(stat -> commonValueMapper.toNameStatus(stat.name(), stat.value()))
                .toList();

        return new Detail(creatureInfo, overSkill, status, detail.itemTypeDetail());
    }

    private CreatureSkill getOverSkill(ExternalCreaturePort.Detail detail) {
        ExternalCreaturePort.Skill overSkillV = getOverSkillV(detail);
        return new CreatureSkill(overSkillV.name(), overSkillV.description());
    }

    private CreatureSkill getCreatureSkill(ExternalCreaturePort.Detail detail) {
        ExternalCreaturePort.Skill skill = getSkill(detail);
        return new CreatureSkill(skill.name(), skill.description());
    }

    private ExternalCreaturePort.Skill getOverSkillV(ExternalCreaturePort.Detail detail) {
        if (!detail.hasOverSkill()) {
            return ExternalCreaturePort.Skill.EMPTY;
        }
        return detail.creatureInfo().overskill();
    }

    private ExternalCreaturePort.Skill getSkill(ExternalCreaturePort.Detail detail) {
        if (!detail.hasSkill()) {
            return ExternalCreaturePort.Skill.EMPTY;
        }
        return detail.creatureInfo().skill();
    }

    private CreatureProfile toCreatureProfile(ExternalCreaturePort.Creature creature) {
        return new CreatureProfile(
                commonValueMapper.toItemName(creature.itemId(), creature.itemName()),
                creature.detail().fame(),
                Rarity.findByValue(creature.itemRarity())
        );
    }

    private Artifacts toArtifacts(List<ExternalCreaturePort.Artifact> artifacts) {
        return artifacts.stream()
                .map(this::toArtifact)
                .collect(Collectors.collectingAndThen(Collectors.toList(), Artifacts::new));
    }

    private Artifact toArtifact(ExternalCreaturePort.Artifact artifact) {
        ArtifactSlot slot = ArtifactSlot.findByColor(artifact.slotColor());
        ItemName itemName = commonValueMapper.toItemName(artifact.itemId(), artifact.itemName());
        Rarity rarity = Rarity.findByValue(artifact.itemRarity());
        List<Status> status = artifact.status()
                .stream()
                .map(stat -> commonValueMapper.toNameStatus(stat.name(), stat.value()))
                .toList();
        return new Artifact(slot, itemName, rarity, artifact.fame(), artifact.detailType(), status);
    }
}
