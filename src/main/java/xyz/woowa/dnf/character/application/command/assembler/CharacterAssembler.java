package xyz.woowa.dnf.character.application.command.assembler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.woowa.dnf.character.application.query.assembler.avatar.AvatarDtoMapper;
import xyz.woowa.dnf.character.application.query.assembler.base.BaseDtoMapper;
import xyz.woowa.dnf.character.application.query.assembler.buff.equipment.BuffEquipmentDtoMapper;
import xyz.woowa.dnf.character.application.query.assembler.creature.CreatureDtoMapper;
import xyz.woowa.dnf.character.application.query.assembler.equipment.EquipmentDtoMapper;
import xyz.woowa.dnf.character.application.query.assembler.flag.FlagDtoMapper;
import xyz.woowa.dnf.character.application.query.dto.CharacterDto;
import xyz.woowa.dnf.character.application.query.dto.equipment.avatar.AvatarDto;
import xyz.woowa.dnf.character.application.query.dto.equipment.avatar.AvatarsDto;
import xyz.woowa.dnf.character.application.query.dto.equipment.buff.equipment.BuffEquipmentDto;
import xyz.woowa.dnf.character.application.query.dto.equipment.creature.CreatureDto;
import xyz.woowa.dnf.character.application.query.dto.equipment.equipment.EquipmentDto;
import xyz.woowa.dnf.character.application.query.dto.equipment.flag.FlagDto;
import xyz.woowa.dnf.character.domain.Character;
import xyz.woowa.dnf.character.domain.equipment.avatar.Avatar;
import xyz.woowa.dnf.character.domain.equipment.avatar.Avatars;
import xyz.woowa.dnf.character.domain.equipment.buff.equipment.BuffEquipment;
import xyz.woowa.dnf.character.domain.equipment.creature.Creature;
import xyz.woowa.dnf.character.domain.equipment.equipment.Equipment;
import xyz.woowa.dnf.character.domain.equipment.flag.Flag;

import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class CharacterAssembler {
    private final BaseDtoMapper baseDtoMapper;
    private final EquipmentDtoMapper equipmentDtoMapper;
    private final CreatureDtoMapper creatureDtoMapper;
    private final AvatarDtoMapper avatarDtoMapper;
    private final BuffEquipmentDtoMapper buffEquipmentDtoMapper;
    private final FlagDtoMapper flagDtoMapper;

    public EquipmentDto toEquipmentDto(Equipment equipment) {
        return equipmentDtoMapper.toMap(equipment);
    }

    public AvatarDto toAvatarDto(Avatar avatar) {
        return avatarDtoMapper.toMap(avatar);
    }

    public CreatureDto toCreatureDto(Creature creature) {
        return creatureDtoMapper.toMap(creature);
    }

    public AvatarsDto toAvatarsDto(Avatars avatars) {
        return avatars.getAvatars()
                .stream()
                .map(this::toAvatarDto)
                .collect(Collectors.collectingAndThen(Collectors.toList(), AvatarsDto::new));
    }

    public BuffEquipmentDto toBuffEquipmentDto(BuffEquipment buffEquipment) {
        return buffEquipmentDtoMapper.toMap(buffEquipment);
    }

    public FlagDto toFlagDto(Flag flag) {
        return flagDtoMapper.toMap(flag);
    }

    public CharacterDto toCharacterDto(Character character) {
        return CharacterDto.builder()
                .base(baseDtoMapper.toDto(character.getBase()))
                .equipment(toEquipmentDto(character.getEquipment()))
                .buffEquipment(toBuffEquipmentDto(character.getBuffEquipment()))
                .avatars(toAvatarsDto(character.getAvatars()))
                .buffAvatars(toAvatarsDto(character.getBuffAvatars()))
                .creature(toCreatureDto(character.getCreature()))
                .buffCreature(toCreatureDto(character.getBuffCreature()))
                .flag(toFlagDto(character.getFlag()))
                .build();

    }
}
