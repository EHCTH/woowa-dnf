package xyz.woowa.dnf.character.infrastructure.persistence;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.woowa.dnf.character.domain.Character;
import xyz.woowa.dnf.character.domain.base.*;
import xyz.woowa.dnf.character.domain.equipment.avatar.Avatars;
import xyz.woowa.dnf.character.domain.equipment.buff.equipment.BuffEquipment;
import xyz.woowa.dnf.character.domain.equipment.creature.Creature;
import xyz.woowa.dnf.character.domain.equipment.equipment.Equipment;
import xyz.woowa.dnf.character.domain.equipment.flag.Flag;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class CharacterSnapshotMapper {

    private final ObjectMapper objectMapper;

    public CharacterSnapshotEntity toEntity(Character character) {
        EntityId id = character.getId();
        Base base = character.getBase();
        CharacterSnapshotId snapshotId = new CharacterSnapshotId(id.characterId(), id.server().getEnglish());

        return CharacterSnapshotEntity.builder()
                .id(snapshotId)
                .name(base.characterName())
                .level(base.level())
                .jobGrowName(base.jobGrowName())
                .adventureName(base.adventureName())
                .guildName(base.guildName())
                .fame(base.fame())
                .baseStatusJson(write(base.baseStatus()))
                .updatedAt(LocalDateTime.now())
                .equipmentJson(write(character.getEquipment()))
                .avatarsJson(write(character.getAvatars()))
                .buffAvatarsJson(write(character.getBuffAvatars()))
                .buffEquipmentJson(write(character.getBuffEquipment()))
                .creatureJson(write(character.getCreature()))
                .buffCreatureJson(write(character.getBuffCreature()))
                .flagJson(write(character.getFlag()))
                .build();
    }

    public Character toDomain(CharacterSnapshotEntity entity) {
        CharacterSnapshotId id = entity.getId();
        EntityId entityId = new EntityId(
                id.characterId(),
                Server.fromEnglish(id.serverId())
        );

        Server server = entityId.server();
        Profile profile = new Profile(entityId.characterId(), server,
                new Name(entity.getName()),
                entity.getLevel(),
                entity.getJobGrowName(),
                entity.getFame());
        Social social = new Social(entity.getAdventureName(), entity.getGuildName());


        BaseStatus baseStatus = read(entity.getBaseStatusJson(), BaseStatus.class);
        Base base = Base.builder()
                .profile(profile)
                .social(social)
                .baseStatus(baseStatus)
                .build();

        Equipment equipment = read(entity.getEquipmentJson(), Equipment.class);
        Avatars avatars = read(entity.getAvatarsJson(), Avatars.class);
        Avatars buffAvatars = read(entity.getBuffAvatarsJson(), Avatars.class);
        BuffEquipment buffEquipment = read(entity.getBuffEquipmentJson(), BuffEquipment.class);
        Creature creature = read(entity.getCreatureJson(), Creature.class);
        Creature buffCreature = read(entity.getBuffCreatureJson(), Creature.class);
        Flag flag = read(entity.getFlagJson(), Flag.class);

        return Character.builder()
                .id(entityId)
                .base(base)
                .equipment(equipment)
                .avatars(avatars)
                .buffAvatars(buffAvatars)
                .buffEquipment(buffEquipment)
                .creature(creature)
                .buffCreature(buffCreature)
                .flag(flag)
                .build();
    }

    private String write(Object value) {
        if (value == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("캐릭터 스냅샷 직렬화 실패", e);
        }
    }

    private <T> T read(String json, Class<T> type) {
        if (json == null) {
            return null;
        }
        try {
            return objectMapper.readValue(json, type);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("캐릭터 스냅샷 역직렬화 실패", e);
        }
    }
}
