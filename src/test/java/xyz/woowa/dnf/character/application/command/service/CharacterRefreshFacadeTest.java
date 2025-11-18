package xyz.woowa.dnf.character.application.command.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import xyz.woowa.dnf.character.application.command.assembler.CharacterAssembler;
import xyz.woowa.dnf.character.application.command.port.inbound.SaveCharacterUseCase;
import xyz.woowa.dnf.character.application.query.dto.CharacterDto;
import xyz.woowa.dnf.character.application.query.port.inbound.GetCharacterDetailUseCase;
import xyz.woowa.dnf.character.domain.Character;
import xyz.woowa.dnf.character.domain.base.Base;
import xyz.woowa.dnf.character.domain.base.EntityId;
import xyz.woowa.dnf.character.domain.base.Server;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CharacterRefreshFacadeTest {
    private final GetCharacterDetailUseCase query = mock(GetCharacterDetailUseCase.class);
    private final CharacterAssembler assembler = mock(CharacterAssembler.class);
    private final SaveCharacterUseCase saver = mock(SaveCharacterUseCase.class);
    private final CharacterParallelLoader loader = mock(CharacterParallelLoader.class);
    private final CharacterRefreshFacade facade = new CharacterRefreshFacade(query, assembler, saver, loader);

    @Test
    @DisplayName("캐릭터가 없으면 외부에서 조회 후 저장 및 반환한다")
    void 캐릭터_없으면_외부에서_조회하고_저장() {
        // give
        String serverId = "cain";
        String characterId = "1234";
        EntityId entityId = new EntityId(characterId, Server.fromEnglish(serverId));
        var command = new GetCharacterDetailUseCase.Command(serverId, characterId);

        // when
        when(saver.findByEntityId(entityId)).thenReturn(null);

        Character loaded = mock(Character.class);
        CharacterDto dto = mock(CharacterDto.class);

        when(loader.load(entityId, command)).thenReturn(loaded);
        when(assembler.toCharacterDto(loaded)).thenReturn(dto);

        // then
        CharacterDto result = facade.character(serverId, characterId);
        assertThat(result).isEqualTo(dto);
    }


    @Test
    @DisplayName("캐릭터가 있고 Base가 같으면 반환한다")
    void 캐릭터_있고_Base가_같으면_반환한다() {
        // give
        String serverId = "cain";
        String characterId = "1234";
        EntityId entityId = new EntityId(characterId, Server.fromEnglish(serverId));
        var command = new GetCharacterDetailUseCase.Command(serverId, characterId);

        Character exist = mock(Character.class);
        Base base = mock(Base.class);
        CharacterDto dto = mock(CharacterDto.class);

        // when
        when(saver.findByEntityId(entityId)).thenReturn(exist);
        when(query.get(command)).thenReturn(base);

        when(exist.equalsToBase(base)).thenReturn(true);

        when(assembler.toCharacterDto(exist)).thenReturn(dto);

        // then
        CharacterDto result = facade.character(serverId, characterId);
        assertThat(result).isEqualTo(dto);
    }


    @Test
    @DisplayName("캐릭터가 있고 Base가 다르면 갱신후 반환한다")
    void 캐릭터가_있고_Base가_다르면_갱신후_반환한다() {
        // give
        String serverId = "cain";
        String characterId = "1234";
        EntityId entityId = new EntityId(characterId, Server.fromEnglish(serverId));
        var command = new GetCharacterDetailUseCase.Command(serverId, characterId);

        Character exist = mock(Character.class);
        Base newBase = mock(Base.class);
        Character refreshed = mock(Character.class);
        CharacterDto newDto = mock(CharacterDto.class);

        // when
        when(saver.findByEntityId(entityId)).thenReturn(exist);
        when(query.get(command)).thenReturn(newBase);

        when(exist.equalsToBase(newBase)).thenReturn(false);

        when(loader.refresh(newBase, entityId, command)).thenReturn(refreshed);
        when(assembler.toCharacterDto(refreshed)).thenReturn(newDto);

        // then
        CharacterDto result = facade.character(serverId, characterId);
        assertThat(result).isEqualTo(newDto);
    }
}
