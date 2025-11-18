package xyz.woowa.dnf.character.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import xyz.woowa.dnf.character.application.command.port.outbound.CharacterRepository;
import xyz.woowa.dnf.character.domain.Character;
import xyz.woowa.dnf.character.domain.base.EntityId;
import xyz.woowa.dnf.character.infrastructure.persistence.CharacterSnapshotEntity;
import xyz.woowa.dnf.character.infrastructure.persistence.CharacterSnapshotId;
import xyz.woowa.dnf.character.infrastructure.persistence.CharacterSnapshotJpaRepository;
import xyz.woowa.dnf.character.infrastructure.persistence.CharacterSnapshotMapper;

import java.util.List;

@Repository
@Profile("mysql")
@RequiredArgsConstructor
public class JpaCharacterRepository implements CharacterRepository {
    private final CharacterSnapshotJpaRepository jpaRepository;
    private final CharacterSnapshotMapper mapper;

    @Override
    public void save(Character character) {
        jpaRepository.save(mapper.toEntity(character));
    }

    @Override
    public Character findById(EntityId id) {
        CharacterSnapshotId snapshotId = new CharacterSnapshotId(id.characterId(), id.server().getEnglish());
        return jpaRepository
                .findById(snapshotId)
                .map(mapper::toDomain)
                .orElse(null);
    }

    @Override
    public List<Character> findAllByGuildName(String guildName) {
        return jpaRepository.findAllByGuildNameIgnoreCase(guildName)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<Character> findAllByAdventureName(String adventureName) {
        return jpaRepository.findAllByAdventureNameIgnoreCase(adventureName)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void update(Character character) {
        EntityId id = character.getId();
        CharacterSnapshotId snapshotId = new CharacterSnapshotId(id.characterId(), id.server().getEnglish());
        CharacterSnapshotEntity existing = jpaRepository.findById(snapshotId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 캐릭터를 업데이트할 수 없습니다. id=" + id));
        CharacterSnapshotEntity entity = mapper.toEntity(character);
        existing.refreshFromDomain(entity);
    }
}
