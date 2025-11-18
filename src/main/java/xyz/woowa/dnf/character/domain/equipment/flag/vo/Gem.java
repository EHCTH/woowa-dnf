package xyz.woowa.dnf.character.domain.equipment.flag.vo;

import lombok.Builder;
import xyz.woowa.dnf.character.domain.equipment.avatar.vo.Detail;
import xyz.woowa.dnf.character.domain.equipment.equipment.vo.ItemProfile;

@Builder
public record Gem(ItemProfile base, Detail detail) {
}
