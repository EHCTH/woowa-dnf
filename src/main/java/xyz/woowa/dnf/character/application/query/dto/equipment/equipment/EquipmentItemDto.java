package xyz.woowa.dnf.character.application.query.dto.equipment.equipment;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import xyz.woowa.dnf.character.application.query.dto.common.NameValue;
import xyz.woowa.dnf.character.application.query.dto.common.ReinforceDto;

import java.util.*;

@Getter
@Builder
public class EquipmentItemDto {
    @NonNull private final ItemProfileDto base;
    @NonNull private final ReinforceDto reinforce;
    private final SkinDto skin;
    private final ItemDetailDto detail;
    private final ItemProfileDto fusion;
    private final ItemDetailDto fusionDetail;
    private final List<NameValue> enchant;
    private final TuneDto tune;

    public boolean hasEnchant() {
        return !enchant.isEmpty();
    }
}
