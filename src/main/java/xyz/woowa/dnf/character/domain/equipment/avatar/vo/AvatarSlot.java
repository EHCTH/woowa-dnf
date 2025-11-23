package xyz.woowa.dnf.character.domain.equipment.avatar.vo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum AvatarSlot {
    HEADGEAR ("HEADGEAR",  "모자 아바타"),
    HAIR     ("HAIR",      "머리 아바타"),
    FACE     ("FACE",      "얼굴 아바타"),
    JACKET   ("JACKET",    "상의 아바타"),
    PANTS    ("PANTS",     "하의 아바타"),
    SHOES    ("SHOES",     "신발 아바타"),
    BREAST   ("BREAST",    "목가슴 아바타"),
    WAIST    ("WAIST",     "허리 아바타"),
    SKIN     ("SKIN",      "스킨 아바타"),
    AURORA   ("AURORA",    "오라 아바타"),
    WEAPON   ("WEAPON",    "무기 아바타"),
    AURA_SKIN("AURA_SKIN", "오라 스킨 아바타"),
    EMPTY("EMPTY", "없음");

    private final String id;
    private final String displayName;

    public static AvatarSlot findById(String id) {
        return Arrays.stream(values())
                .filter(avatarSlot -> avatarSlot.id.equals(id))
                .findAny()
                .orElse(EMPTY);
    }
    public static boolean isBuffAvatar(AvatarSlot other) {
        return other == AvatarSlot.JACKET || other == AvatarSlot.PANTS;
    }
    public boolean hasEmpty() {
        return this == EMPTY;
    }
}
