package xyz.woowa.dnf.character.domain.equipment.creature.vo;

import xyz.woowa.dnf.character.domain.equipment.common.ItemName;
import xyz.woowa.dnf.character.domain.equipment.common.Rarity;

public record CreatureProfile(ItemName itemName, String fame, Rarity rarity) {
}
