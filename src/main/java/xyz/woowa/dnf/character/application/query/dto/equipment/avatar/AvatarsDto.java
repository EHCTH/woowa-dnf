package xyz.woowa.dnf.character.application.query.dto.equipment.avatar;

import lombok.Getter;
import xyz.woowa.dnf.character.application.query.dto.common.ItemValue;
import xyz.woowa.dnf.character.domain.equipment.avatar.vo.AvatarSlot;

import java.util.ArrayList;
import java.util.List;
@Getter
public class AvatarsDto {

    private final List<AvatarDto> avatars;

    public AvatarsDto(List<AvatarDto> avatars) {
        this.avatars = updateAuraMerge(new ArrayList<>(avatars));
    }

    private List<AvatarDto> updateAuraMerge(List<AvatarDto> avatarsDto) {
        avatarsDto.stream()
                .filter(avatarDto -> avatarDto.equalsSlotName(AvatarSlot.AURA_SKIN.getDisplayName()))
                .findAny()
                .map(AvatarDto::getBase)
                .ifPresent(profileDto -> updateAuraSkin(profileDto, avatarsDto));
        return List.copyOf(avatarsDto);
    }

    private void updateAuraSkin(ProfileDto auraSkinDto, List<AvatarDto> avatarsDto) {
        if (!hasAuraAvatar(avatarsDto)) {
            return;
        }

        AvatarDto auraAvatar = getAuraAvatar(avatarsDto);

        avatarsDto.removeIf(x -> x.equalsSlotName(auraSkinDto.slot()));
        avatarsDto.remove(auraAvatar);

        update(auraSkinDto, avatarsDto, auraAvatar);
    }

    private void update(ProfileDto auraSkinDto, List<AvatarDto> avatarsDto, AvatarDto auraAvatar) {
        AvatarDto ret = AvatarDto.builder()
                .base(auraAvatar.getBase())
                .clone(new ItemValue(auraSkinDto.id(), auraSkinDto.name()))
                .optionAbility(auraAvatar.getOptionAbility())
                .emblems(auraAvatar.getEmblems())
                .detail(auraAvatar.getDetail())
                .build();

        avatarsDto.add(ret);
    }

    private boolean hasAuraAvatar(List<AvatarDto> avatarsDto) {
        return avatarsDto.stream()
                .anyMatch(avatarDto -> avatarDto.equalsSlotName(AvatarSlot.AURORA.getDisplayName()));
    }

    private AvatarDto getAuraAvatar(List<AvatarDto> avatarsDto) {
        return avatarsDto.stream()
                .filter(avatarDto -> avatarDto.equalsSlotName(AvatarSlot.AURORA.getDisplayName()))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("[ERROR] 오라 아바타가 없습니다"));
    }
}
