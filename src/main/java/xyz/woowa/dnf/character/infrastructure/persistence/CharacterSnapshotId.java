package xyz.woowa.dnf.character.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public record CharacterSnapshotId(
        @Column(name = "character_id", nullable = false, length = 50)
        String characterId,
        @Column(name = "server_id", nullable = false, length = 20)
        String serverId
) implements Serializable {
}
