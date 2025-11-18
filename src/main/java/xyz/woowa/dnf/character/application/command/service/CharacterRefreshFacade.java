package xyz.woowa.dnf.character.application.command.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.woowa.dnf.character.application.command.assembler.CharacterAssembler;
import xyz.woowa.dnf.character.application.command.port.inbound.SaveCharacterUseCase;
import xyz.woowa.dnf.character.application.query.dto.CharacterDto;
import xyz.woowa.dnf.character.application.query.port.inbound.GetCharacterDetailUseCase;
import xyz.woowa.dnf.character.domain.Character;
import xyz.woowa.dnf.character.domain.base.Base;
import xyz.woowa.dnf.character.domain.base.EntityId;
import xyz.woowa.dnf.character.domain.base.Server;
import xyz.woowa.dnf.common.aop.CharacterLog;

@Service
@Slf4j
@RequiredArgsConstructor
public class CharacterRefreshFacade {
    private final GetCharacterDetailUseCase query;
    private final CharacterAssembler assembler;
    private final SaveCharacterUseCase saver;
    private final CharacterParallelLoader loader;

    @Transactional
    @CharacterLog("캐릭터 조회")
    public CharacterDto character(String serverId, String characterId) {
        EntityId entityId = new EntityId(characterId, Server.fromEnglish(serverId));
        var command = command(serverId, characterId);
        Character exist = saver.findByEntityId(entityId);
        if (hasCharacter(exist)) {
            return refreshIfChanged(exist, entityId, command);
        }
        return loadAndSaveNewCharacter(entityId, command);
    }

    @Transactional
    @CharacterLog("캐릭터 강제 갱신")
    public CharacterDto refresh(String serverId, String characterId) {
        EntityId entityId = new EntityId(characterId, Server.fromEnglish(serverId));
        var command = command(serverId, characterId);
        Base base = query.get(command);
        return refresh(entityId, command, base);
    }

    private GetCharacterDetailUseCase.Command command(String serverId, String characterId) {
        return new GetCharacterDetailUseCase.Command(serverId, characterId);
    }

    private boolean hasCharacter(Character character) {
        return character != null;
    }

    private CharacterDto refreshIfChanged(Character exist,
                                          EntityId entityId,
                                          GetCharacterDetailUseCase.Command command) {
        Base base = query.get(command);
        if (exist.equalsToBase(base)) {
            return assembler.toCharacterDto(exist);
        }
        return refresh(entityId, command, base);
    }

    private CharacterDto refresh(EntityId entityId, GetCharacterDetailUseCase.Command command, Base base) {
        Character refreshed = loader.refresh(base, entityId, command);
        saver.update(refreshed);
        return assembler.toCharacterDto(refreshed);
    }

    private CharacterDto loadAndSaveNewCharacter(EntityId entityId,
                                                 GetCharacterDetailUseCase.Command command) {
        Character character = loader.load(entityId, command);
        saver.save(character);
        return assembler.toCharacterDto(character);
    }
}
