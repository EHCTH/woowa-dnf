package xyz.woowa.dnf.character.infrastructure.repository;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import xyz.woowa.dnf.character.application.command.port.outbound.CharacterRepository;
import xyz.woowa.dnf.character.domain.Character;
import xyz.woowa.dnf.character.domain.base.EntityId;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Profile("memory")
public class MemoryCharacterAdapter implements CharacterRepository {
    private final Map<EntityId, Character> characterMap = new HashMap<>();
    @Override
    public void save(Character character) {
        characterMap.put(character.getId(), character);
    }

    @Override
    public Character findById(EntityId id) {
        return characterMap.get(id);
    }

    @Override
    public List<Character> findAllByGuildName(String guildName) {
        return characterMap.values().stream()
                .filter(character -> character.equalsToGuildName(guildName))
                .toList();
    }

    @Override
    public List<Character> findAllByAdventureName(String adventureName) {
        return characterMap.values().stream()
                .filter(character -> character.equalsToAdventureName(adventureName))
                .toList();
    }

    @Override
    public void update(Character character) {
        characterMap.put(character.getId(), character);
    }

}
