package xyz.woowa.dnf.character.application.query.port.outbound.equipment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import lombok.With;

import java.util.Collections;
import java.util.List;

public interface ExternalCreaturePort {
    Row row(String serverId, String characterId);
    BuffRow buffRow(String serverId, String characterId);

    Detail detail(String itemId);

    Detail artifactDetail(String itemId);

    @With
    record Row(Creature creature) {
    }

    record BuffRow(@JsonProperty("skill") BuffSkill skill) {
        public boolean hasCreature() {
            if (skill.buff == null) {
                return false;
            }
            return !skill.buff.creature.isEmpty();
        }
        public Creature creature() {
            return skill.buff.creature.getFirst();
        }

    }
    @With
    record BuffSkill(
            Buff buff) {
    }

    @With
    record Buff(
                @JsonSetter(nulls = Nulls.AS_EMPTY, contentNulls = Nulls.SKIP) List<Creature> creature) {
    }
    @With
    record Creature(
            @JsonSetter(nulls = Nulls.AS_EMPTY) String itemId,
            @JsonSetter(nulls = Nulls.AS_EMPTY) String itemName,
            @JsonSetter(nulls = Nulls.AS_EMPTY) String itemRarity,
            @JsonProperty("clone") Clone clonCreature,
            @JsonSetter(nulls = Nulls.AS_EMPTY, contentNulls = Nulls.SKIP) List<Artifact> artifact,
            Detail detail) {

        public static Creature EMPTY = new Creature("", "", "", Clone.EMPTY, Collections.emptyList(), Detail.EMPTY);
    }

    @With
    record Artifact(@JsonSetter(nulls = Nulls.AS_EMPTY) String slotColor,
                    @JsonSetter(nulls = Nulls.AS_EMPTY) String itemId,
                    @JsonSetter(nulls = Nulls.AS_EMPTY) String itemName,
                    @JsonSetter(nulls = Nulls.AS_EMPTY) String itemRarity,
                    Detail detail) {
        public String fame() {
            return detail.fame;
        }
        public List<Status> status() {
            return detail.itemStatus;
        }
        public String detailType() {
            return detail.itemTypeDetail;
        }
    }

    record Status(
            @JsonSetter(nulls = Nulls.AS_EMPTY) String name,
            @JsonSetter(nulls = Nulls.AS_EMPTY) String value) {
    }

    record Clone(
            @JsonSetter(nulls = Nulls.AS_EMPTY) String itemId, @JsonSetter(nulls = Nulls.AS_EMPTY) String itemName) {
        public static Clone EMPTY = new Clone("", "");
    }



    record Detail(
            @JsonSetter(nulls = Nulls.AS_EMPTY) String itemTypeDetail,
            @JsonSetter(nulls = Nulls.AS_EMPTY) String fame,
            @JsonSetter(nulls = Nulls.AS_EMPTY, contentNulls = Nulls.SKIP) List<Status> itemStatus,
            CreatureInfo creatureInfo) {
        public static Detail EMPTY = new Detail("", "", Collections.emptyList(), CreatureInfo.EMPTY);

        public boolean hasSkill() {
            return creatureInfo.skill != null;
        }
        public boolean hasOverSkill() {
            return creatureInfo.overskill != null;
        }
    }
    record CreatureInfo(
            Skill skill,
            Skill overskill) {
        public static final CreatureInfo EMPTY = new CreatureInfo(Skill.EMPTY, Skill.EMPTY);
    }
    record Skill(
            @JsonSetter String name,
            @JsonSetter String description) {
        public static final Skill EMPTY = new Skill("", "");

    }
}
