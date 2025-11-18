package xyz.woowa.dnf.character.application.query.dto.equipment.avatar;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import xyz.woowa.dnf.character.application.query.dto.common.ItemValue;
import xyz.woowa.dnf.character.domain.equipment.avatar.vo.AvatarSlot;
import xyz.woowa.dnf.character.domain.equipment.common.Rarity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AvatarsDtoTest {

    @Test
    @DisplayName("오라 스킨과 오라 아바타가 있을경우 하나로 머지한다")
    void 오라_스킨과_오라_아바타가_있을경우_하나로_머지한다() {
        // give
        AvatarDto auraSkin = auraSkin("SKIN_ID", "오라 스킨");
        AvatarDto auraAvatar = auraAvatar("AURA_ID", "오라 아바타");
        AvatarDto other = otherAvatar("모자");

        AvatarsDto avatarsDto = new AvatarsDto(List.of(auraSkin, auraAvatar, other));
        List<AvatarDto> result = avatarsDto.getAvatars();

        // when
        AvatarDto merged = result.stream()
                .filter(a -> a.equalsSlotName(AvatarSlot.AURORA.getDisplayName()))
                .findFirst()
                .orElseThrow();

        // then
        assertThat(result).hasSize(2);
        assertThat(merged.getBase().id()).isEqualTo(auraAvatar.getBase().id());
        assertThat(merged.getBase().name()).isEqualTo(auraAvatar.getBase().name());
        assertThat(merged.getBase().slot()).isEqualTo(auraAvatar.getBase().slot());

        ItemValue clone = merged.getClone();
        assertThat(clone.id()).isEqualTo(auraSkin.getBase().id());
        assertThat(clone.name()).isEqualTo(auraSkin.getBase().name());

        assertThat(merged.getOptionAbility()).isEqualTo(auraAvatar.getOptionAbility());
        assertThat(merged.getEmblems()).isEqualTo(auraAvatar.getEmblems());
        assertThat(merged.getDetail()).isEqualTo(auraAvatar.getDetail());

        assertThat(result).anySatisfy(a -> assertThat(a.getBase().slot()).isEqualTo("모자"));
    }

    @Test
    @DisplayName("오라 스킨만 있는경우 변경되지 않는다")
    void 오라_스킨만_있는경우_변경되지_않는다() {
        // give
        AvatarDto auraSkin = auraSkin("SKIN_ID", "오라 스킨");
        AvatarDto other = otherAvatar("모자");
        AvatarsDto avatarsDto = new AvatarsDto(List.of(auraSkin, other));

        // when
        List<AvatarDto> result = avatarsDto.getAvatars();

        // then
        assertThat(result).hasSize(2);
        assertThat(result).containsExactlyInAnyOrder(auraSkin, other);
    }

    @Test
    @DisplayName("오라 아바타만 있는 경우 변경되지 않는다")
    void 오라_아바타만_있는경우_변경되지_않는다() {
        // give
        AvatarDto auraAvatar = auraAvatar("AURA_ID", "오라 아바타");
        AvatarDto other = otherAvatar("모자");
        AvatarsDto avatarsDto = new AvatarsDto(List.of(auraAvatar, other));

        // when
        List<AvatarDto> result = avatarsDto.getAvatars();

        // then
        assertThat(result).hasSize(2);
        assertThat(result).containsExactlyInAnyOrder(auraAvatar, other);
    }

    @Test
    @DisplayName("오라 관련 아바타가 없으면 그대로 반환한다")
    void 오라_관련_아바타가_없으면_그대로_반환한다() {
        // give
        AvatarDto hat = otherAvatar("모자");
        AvatarDto hair = otherAvatar("머리");
        AvatarsDto avatarsDto = new AvatarsDto(List.of(hat, hair));

        // when
        List<AvatarDto> result = avatarsDto.getAvatars();

        // then
        assertThat(result).containsExactlyInAnyOrder(hat, hair);
    }

    private AvatarDto auraSkin(String id, String name) {
        ProfileDto base = new ProfileDto(AvatarSlot.AURA_SKIN.getDisplayName(), Rarity.레어.name(), new ItemValue(id, name), "1234");
        return AvatarDto.builder()
                .base(base)
                .build();
    }

    private AvatarDto auraAvatar(String id, String name) {
        ProfileDto base = new ProfileDto(AvatarSlot.AURORA.getDisplayName(), Rarity.레어.name(), new ItemValue(id, name), "1234");
        return AvatarDto.builder()
                .base(base)
                .build();
    }

    private AvatarDto otherAvatar(String slotName) {
        ProfileDto base = new ProfileDto(slotName, Rarity.레어.name(), ItemValue.EMPTY, "1234");
        return AvatarDto.builder()
                .base(base)
                .build();
    }
}
