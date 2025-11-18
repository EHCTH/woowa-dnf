package xyz.woowa.dnf.character.application.query.assembler.flag;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.woowa.dnf.character.application.query.assembler.common.CommonValueMapper;
import xyz.woowa.dnf.character.application.query.port.outbound.equipment.ExternalFlagPort;
import xyz.woowa.dnf.character.domain.equipment.avatar.vo.Detail;
import xyz.woowa.dnf.character.domain.equipment.common.Rarity;
import xyz.woowa.dnf.character.domain.equipment.common.Status;
import xyz.woowa.dnf.character.domain.equipment.equipment.vo.ItemProfile;
import xyz.woowa.dnf.character.domain.equipment.equipment.vo.ReinForce;
import xyz.woowa.dnf.character.domain.equipment.equipment.vo.Slot;
import xyz.woowa.dnf.character.domain.equipment.flag.Flag;
import xyz.woowa.dnf.character.domain.equipment.flag.vo.Gem;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FlagMapper {
    private final CommonValueMapper mapper;

    public Flag toFlag(ExternalFlagPort.Flag flag) {
        return Flag.builder()
                .base(toBase(flag))
                .reinForce(ReinForce.enforce(flag.reinforce()))
                .detail(toDetail(flag.detail()))
                .gems(toGems(flag.gems()))
                .build();
    }

    private ItemProfile toBase(ExternalFlagPort.Flag flag) {
        ExternalFlagPort.Detail detail = flag.detail();
        return new ItemProfile(
                Slot.FLAG,
                Rarity.findByValue(flag.itemRarity()),
                mapper.toItemName(flag.itemId(), flag.itemName()),
                mapper.toItemName(detail.itemTypeDetailId(), detail.itemTypeDetail()));
    }

    private Detail toDetail(ExternalFlagPort.Detail detail) {
        var status = toStatus(detail);
        return Detail.of(detail.fame(), status);
    }

    private List<Gem> toGems(List<ExternalFlagPort.Gem> gems) {
        return gems.stream()
                .map(this::toGem)
                .toList();
    }

    private Gem toGem(ExternalFlagPort.Gem gem) {
        var detail = gem.detail();
        return Gem.builder()
                .base(new ItemProfile(
                                Slot.GEM,
                                Rarity.findByValue(gem.itemRarity()),
                                mapper.toItemName(gem.itemId(), gem.itemName()),
                                mapper.toItemName(detail.itemTypeDetailId(), detail.itemTypeDetail())
                        )
                )
                .detail(toDetail(detail))
                .build();
    }

    private List<Status> toStatus(ExternalFlagPort.Detail detail) {
        return detail.itemStatus()
                .stream()
                .map(stat -> mapper.toNameStatus(stat.name(), stat.value()))
                .toList();
    }
}
