package xyz.woowa.dnf.character.application.query.dto;

import lombok.Builder;
import lombok.Getter;
import xyz.woowa.dnf.character.application.query.dto.base.BaseDto;
import xyz.woowa.dnf.character.application.query.dto.equipment.avatar.AvatarDto;
import xyz.woowa.dnf.character.application.query.dto.equipment.avatar.AvatarsDto;
import xyz.woowa.dnf.character.application.query.dto.equipment.buff.equipment.BuffEquipItemDto;
import xyz.woowa.dnf.character.application.query.dto.equipment.buff.equipment.BuffEquipmentDto;
import xyz.woowa.dnf.character.application.query.dto.equipment.creature.CreatureDto;
import xyz.woowa.dnf.character.application.query.dto.equipment.equipment.EquipmentDto;
import xyz.woowa.dnf.character.application.query.dto.equipment.flag.FlagDto;

import java.util.List;

@Getter
@Builder
public class  CharacterDto {
    private final BaseDto base;
    private final EquipmentDto equipment;
    private final BuffEquipmentDto buffEquipment;
    private final AvatarsDto avatars;
    private final AvatarsDto buffAvatars;
    private final CreatureDto creature;
    private final CreatureDto buffCreature;
    private final FlagDto flag;

    public String getName() {
        return base.characterName();
    }
    public String getServerId() {
        return base.serverId();
    }

    public List<AvatarDto> getAvatars() {
        return avatars.getAvatars();
    }

    public List<AvatarDto> getBuffAvatars() {
        return buffAvatars.getAvatars();
    }
    public List<BuffEquipItemDto> getBuffEquipments() {
        return buffEquipment.getBuffEquipItems();
    }

}
