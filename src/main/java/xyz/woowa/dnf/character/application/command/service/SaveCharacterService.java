package xyz.woowa.dnf.character.application.command.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.woowa.dnf.character.application.command.port.inbound.SaveCharacterUseCase;
import xyz.woowa.dnf.character.application.command.port.outbound.CharacterRepository;
import xyz.woowa.dnf.character.domain.Character;
import xyz.woowa.dnf.character.domain.base.EntityId;

@Service
@Transactional
@RequiredArgsConstructor
public class SaveCharacterService implements SaveCharacterUseCase {
    private final CharacterRepository characterRepository;

    @Override
    public void save(Character character) {
        characterRepository.save(character);
    }

    @Override
    @Transactional(readOnly = true)
    public Character findByEntityId(EntityId id) {
        return characterRepository.findById(id);
    }

    @Override
    public void update(Character refresh) {
        characterRepository.update(refresh);
    }
}
