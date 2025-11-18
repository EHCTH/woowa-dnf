package xyz.woowa.dnf.character.application.query.assembler.avatar;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.woowa.dnf.character.application.query.assembler.common.CommonValueMapper;
import xyz.woowa.dnf.character.application.query.port.outbound.equipment.ExternalAvatarPort;
import xyz.woowa.dnf.character.domain.equipment.avatar.Avatar;
import xyz.woowa.dnf.character.domain.equipment.avatar.vo.*;
import xyz.woowa.dnf.character.domain.equipment.common.ItemName;
import xyz.woowa.dnf.character.domain.equipment.common.Rarity;
import xyz.woowa.dnf.character.domain.equipment.common.Status;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AvatarMapper {
    private final CommonValueMapper commonValueMapper;
    public Avatar toAvatar(ExternalAvatarPort.Avatar avatar) {
        ExternalAvatarPort.Clone clone = avatar.cloneAvatar();
        return Avatar.builder()
                .base(toItemProfile(avatar))
                .clone(commonValueMapper.toItemName(clone.itemId(), clone.itemName()))
                .emblems(toEmblems(avatar.emblems()))
                .optionAbility(avatar.optionAbility())
                .detail(toDetail(avatar.detail()))
                .build();
    }
    private Emblems toEmblems(List<ExternalAvatarPort.Emblem> emblems) {
        return emblems.stream()
                .map(this::toEmblem)
                .collect(Collectors.collectingAndThen(Collectors.toList(), Emblems::new));
    }
    private Emblem toEmblem(ExternalAvatarPort.Emblem emblem) {
        return new Emblem(
                EmblemSlot.findByColor(emblem.slotColor()),
                commonValueMapper.toItemName(emblem.itemId(), emblem.itemName()),
                Rarity.findByValue(emblem.itemRarity()));
    }
    private ItemProfile toItemProfile(ExternalAvatarPort.Avatar avatar) {
        ItemName itemName = commonValueMapper.toItemName(avatar.itemId(), avatar.itemName());
        return new ItemProfile(AvatarSlot.findById(avatar.slotId()), itemName, Rarity.findByValue(avatar.itemRarity()));
    }
    private Detail toDetail(ExternalAvatarPort.Detail detail) {
        if (detail == null) {
            return Detail.EMPTY;
        }
        return new Detail(detail.fame(), detail.itemExplain(), toStatus(detail.itemStatus()));
    }
    private List<Status> toStatus(List<ExternalAvatarPort.Status> statusList) {
        if (statusList.isEmpty()) {
            return Collections.emptyList();
        }
        return statusList.stream()
                .map(status-> commonValueMapper.toNameStatus(status.name(), status.value()))
                .toList();

    }
}
