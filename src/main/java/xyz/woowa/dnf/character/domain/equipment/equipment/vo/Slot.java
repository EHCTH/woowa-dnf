package xyz.woowa.dnf.character.domain.equipment.equipment.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Slot {

    WEAPON("WEAPON", ItemType.WEAPON, "무기"),

    JACKET("JACKET", ItemType.ARMOR, "상의"),
    SHOULDER("SHOULDER", ItemType.ARMOR, "머리어깨"),
    PANTS("PANTS", ItemType.ARMOR, "하의"),
    SHOES("SHOES", ItemType.ARMOR, "신발"),
    WAIST("WAIST", ItemType.ARMOR, "벨트"),

    TITLE("TITLE", ItemType.ACCESSORY, "칭호"),
    AMULET("AMULET", ItemType.ACCESSORY, "목걸이"),
    WRIST("WRIST", ItemType.ACCESSORY, "팔찌"),
    RING("RING", ItemType.ACCESSORY, "반지"),

    SUPPORT("SUPPORT", ItemType.SUPPORT, "보조장비"),
    MAGIC_STON("MAGIC_STON", ItemType.SUPPORT, "마법석"),
    EARRING("EARRING", ItemType.SUPPORT, "귀걸이"),

    FLAG("FLAG", ItemType.ADD_EQUIPMENT, "휘장"),
    GEM("GEM", ItemType.STACKABLE, "젬"),

    UNKNOWN("UNKNOWN", ItemType.NONE,"NONE");

    private final String id;

    private final ItemType itemType;

    private final String displayName;



    public static Slot findById(String id) {
        return Arrays.stream(values())
                .filter(slot -> slot.id.equals(id))
                .findFirst()
                .orElse(UNKNOWN);
    }

    @Getter
    enum ItemType {
        WEAPON, ARMOR,ACCESSORY,SUPPORT,ADD_EQUIPMENT,STACKABLE,NONE

    }
}
