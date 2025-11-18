package xyz.woowa.dnf.character.application.command.port.inbound;

import xyz.woowa.dnf.character.domain.Character;
import xyz.woowa.dnf.character.domain.base.EntityId;

public interface SaveCharacterUseCase {
     void save(Character character);
     Character findByEntityId(EntityId id);
     void update(Character refresh);
}
