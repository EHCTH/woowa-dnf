package xyz.woowa.dnf.character.domain.equipment.creature.vo;

import xyz.woowa.dnf.character.domain.equipment.common.ItemName;
import xyz.woowa.dnf.character.domain.equipment.common.Rarity;
import xyz.woowa.dnf.character.domain.equipment.common.Status;

import java.util.List;

public record Artifact(ArtifactSlot slot, ItemName itemName, Rarity rarity, String fame, String detailType, List<Status> status) {
    public String color() {
        return slot.getColor();
    }

    public String name() {
        return itemName.name();
    }

    public String id() {
        return itemName.id();
    }

    public String getRarity() {
        return rarity.name();
    }
    public String fame() {
        return fame;
    }
    public List<Status> status() {
        return status;
    }
}
