package xyz.woowa.dnf.character.domain.equipment.equipment.vo;

import lombok.*;
import xyz.woowa.dnf.character.domain.equipment.common.Enchant;

@Getter
@ToString
@EqualsAndHashCode
@Builder(toBuilder = true)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class EquipmentItem {
    @NonNull private final ItemProfile baseItemProfile;
    @NonNull private final ReinForce reinForce;
    private final ItemDetail baseItemDetail;
    private final ItemProfile fusionItemProfile;
    private final ItemDetail fusionItemDetail;
    private final Enchant enchant;
    private final Tune tune;
}
