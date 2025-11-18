package xyz.woowa.dnf.character.application.query.port.outbound.equipment;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import lombok.With;

import java.util.Collections;
import java.util.List;

public interface ExternalBuffEquipmentPort {
    Row row(String serverId, String characterId);

    Detail detail(String itemId);

    @With
    record Row(Skill skill) {
        public SkillInfo skillInfo() {
            return skill.buff.skillInfo;
        }

        public List<Equipment> equipment() {
            if (skill.buff == null || skill.buff.equipment.isEmpty()) {
                return Collections.emptyList();
            }
            return skill.buff.equipment;
        }
    }

    @With
    record Skill(
            Buff buff) {
    }

    @With
    record Buff(SkillInfo skillInfo,
                @JsonSetter(nulls = Nulls.AS_EMPTY, contentNulls = Nulls.SKIP) List<Equipment> equipment) {
    }

    record SkillInfo(
            @JsonSetter(nulls = Nulls.AS_EMPTY) String name,
            Option option) {

    }

    record Option(
            @JsonSetter(nulls = Nulls.AS_EMPTY) String level,
            @JsonSetter(nulls = Nulls.AS_EMPTY) String desc,
            @JsonSetter(nulls = Nulls.AS_EMPTY, contentNulls = Nulls.SKIP) List<String> values) {
    }

    @With
    record Equipment(@JsonSetter(nulls = Nulls.AS_EMPTY) String slotId,
                     @JsonSetter(nulls = Nulls.AS_EMPTY) String itemId,
                     @JsonSetter(nulls = Nulls.AS_EMPTY) String itemName,
                     @JsonSetter(nulls = Nulls.AS_EMPTY) String itemTypeDetailId,
                     @JsonSetter(nulls = Nulls.AS_EMPTY) String itemTypeDetail,
                     @JsonSetter(nulls = Nulls.AS_EMPTY) String itemRarity,
                     Enchant enchant,
                     Detail detail) {
    }

    record Enchant(
            @JsonSetter(nulls = Nulls.AS_EMPTY, contentNulls = Nulls.SKIP) List<ReinforceSkill> reinforceSkill) {

        public boolean hasSkills() {
            return !reinforceSkill.isEmpty() && !reinforceSkill.getFirst().skills.isEmpty();
        }
        public String name() {
            return reinforceSkill.getFirst().skills.getFirst().name;
        }
        public String value() {
            return reinforceSkill.getFirst().skills.getFirst().value;
        }
    }

    record ReinforceSkill(
            @JsonSetter(nulls = Nulls.AS_EMPTY, contentNulls = Nulls.SKIP) List<Skills> skills) {
    }

    record Skills(
            @JsonSetter(nulls = Nulls.AS_EMPTY) String name,
            @JsonSetter(nulls = Nulls.AS_EMPTY) String value) {
    }

    record Detail(
            @JsonSetter(nulls = Nulls.AS_EMPTY) String fame,
            @JsonSetter(nulls = Nulls.AS_EMPTY) String itemExplain,
            @JsonSetter(nulls = Nulls.AS_EMPTY, contentNulls = Nulls.SKIP) List<Status> itemStatus) {

    }

    record Status(
            @JsonSetter(nulls = Nulls.AS_EMPTY) String name,
            @JsonSetter(nulls = Nulls.AS_EMPTY) String value) {
    }
}

