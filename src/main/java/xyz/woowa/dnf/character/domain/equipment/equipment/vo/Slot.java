package xyz.woowa.dnf.character.domain.equipment.equipment.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Slot {

    WEAPON("WEAPON", ItemType.WEAPON, "무기", 1),
    SUPPORT_WEAPON("SUPPORT_WEAPON", ItemType.WEAPON, "보조무기", 2),
    TITLE("TITLE", ItemType.ACCESSORY, "칭호", 3),

    JACKET("JACKET", ItemType.ARMOR, "상의", 4),
    SHOULDER("SHOULDER", ItemType.ARMOR, "머리어깨", 5),
    PANTS("PANTS", ItemType.ARMOR, "하의", 6),
    SHOES("SHOES", ItemType.ARMOR, "신발", 7),
    WAIST("WAIST", ItemType.ARMOR, "벨트", 8),

    AMULET("AMULET", ItemType.ACCESSORY, "목걸이", 9),
    WRIST("WRIST", ItemType.ACCESSORY, "팔찌", 10),
    RING("RING", ItemType.ACCESSORY, "반지", 11),

    SUPPORT("SUPPORT", ItemType.SUPPORT, "보조장비", 12),
    MAGIC_STON("MAGIC_STON", ItemType.SUPPORT, "마법석", 13),
    EARRING("EARRING", ItemType.SUPPORT, "귀걸이", 14),

    FLAG("FLAG", ItemType.ADD_EQUIPMENT, "휘장", 15),
    GEM("GEM", ItemType.STACKABLE, "젬", 16),

    UNKNOWN("UNKNOWN", ItemType.NONE, "NONE", Integer.MAX_VALUE);

    private final String id;
    private final ItemType itemType;
    private final String displayName;
    private final int order;


    public static Slot findById(String id) {
        return Arrays.stream(values())
                .filter(slot -> slot.id.equals(id))
                .findFirst()
                .orElse(UNKNOWN);
    }

    public static int orderByDisplayName(String displayName) {
        return fromDisplayName(displayName).order;
    }

    private static Slot fromDisplayName(String displayName) {
        return Arrays.stream(values())
                .filter(slot -> slot.displayName.equals(displayName))
                .findFirst()
                .orElse(UNKNOWN);
    }


    @Getter
    enum ItemType {
        WEAPON, ARMOR, ACCESSORY, SUPPORT, ADD_EQUIPMENT, STACKABLE, NONE

    }
}
