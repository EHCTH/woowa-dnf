package xyz.woowa.dnf.character.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CharacterSnapshotJpaRepository
        extends JpaRepository<CharacterSnapshotEntity, CharacterSnapshotId> {

    Optional<CharacterSnapshotEntity> findById(CharacterSnapshotId id);

    List<CharacterSnapshotEntity> findAllByGuildNameIgnoreCase(String guildName);

    List<CharacterSnapshotEntity> findAllByAdventureNameIgnoreCase(String adventureName);

}
