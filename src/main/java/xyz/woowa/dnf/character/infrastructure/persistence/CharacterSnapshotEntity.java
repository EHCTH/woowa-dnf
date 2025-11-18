package xyz.woowa.dnf.character.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "character_snapshot")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CharacterSnapshotEntity {

    @EmbeddedId
    private CharacterSnapshotId id;

    @Column(name = "level", nullable = false)
    private Integer level;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "adventure_name", nullable = false, length = 50)
    private String adventureName;

    @Column(name = "fame", nullable = false)
    private Integer fame;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "job_grow_name", length = 20)
    private String jobGrowName;

    @Column(name = "guild_name", length = 50)
    private String guildName;

    @Column(name = "base_status_json", columnDefinition = "json")
    private String baseStatusJson;

    @Column(name = "equipment_json", columnDefinition = "json")
    private String equipmentJson;

    @Column(name = "avatars_json", columnDefinition = "json")
    private String avatarsJson;

    @Column(name = "buff_avatars_json", columnDefinition = "json")
    private String buffAvatarsJson;

    @Column(name = "buff_equipment_json", columnDefinition = "json")
    private String buffEquipmentJson;

    @Column(name = "creature_json", columnDefinition = "json")
    private String creatureJson;

    @Column(name = "buff_creature_json", columnDefinition = "json")
    private String buffCreatureJson;

    @Column(name = "flag_json", columnDefinition = "json")
    private String flagJson;

    public void refreshFromDomain(CharacterSnapshotEntity other) {
        this.name = other.name;
        this.level = other.level;
        this.adventureName = other.adventureName;
        this.guildName = other.guildName;
        this.jobGrowName = other.jobGrowName;
        this.fame = other.fame;
        this.baseStatusJson = other.baseStatusJson;
        this.equipmentJson = other.equipmentJson;
        this.avatarsJson = other.avatarsJson;
        this.buffAvatarsJson = other.buffAvatarsJson;
        this.buffEquipmentJson = other.buffEquipmentJson;
        this.creatureJson = other.creatureJson;
        this.buffCreatureJson = other.buffCreatureJson;
        this.flagJson = other.flagJson;
        this.updatedAt = LocalDateTime.now();
    }

}

