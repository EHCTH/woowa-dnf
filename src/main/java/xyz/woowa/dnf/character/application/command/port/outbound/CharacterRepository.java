package xyz.woowa.dnf.character.application.command.port.outbound;

import xyz.woowa.dnf.character.domain.Character;
import xyz.woowa.dnf.character.domain.base.EntityId;

import java.util.List;

public interface CharacterRepository {
    void save(Character character);
    Character findById(EntityId id);
    List<Character> findAllByGuildName(String guildName);
    List<Character> findAllByAdventureName(String adventureName);
    void update(Character character);
}
