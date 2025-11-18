package xyz.woowa.dnf.character.domain.equipment.creature.vo;

import xyz.woowa.dnf.character.domain.equipment.common.Status;

import java.util.List;

public record Detail(CreatureSkill skill, CreatureSkill overSkill, List<Status> status, String detailType) {
}
