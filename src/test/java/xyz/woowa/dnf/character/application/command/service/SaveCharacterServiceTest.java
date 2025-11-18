package xyz.woowa.dnf.character.application.command.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import xyz.woowa.dnf.character.application.command.port.outbound.CharacterRepository;
import xyz.woowa.dnf.character.domain.Character;
import xyz.woowa.dnf.character.domain.base.EntityId;
import xyz.woowa.dnf.character.domain.base.Server;
import xyz.woowa.dnf.character.infrastructure.repository.MemoryCharacterRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SaveCharacterServiceTest {
    private CharacterRepository memoryRepository;
    private SaveCharacterService service;

    @BeforeEach
    void init() {
        memoryRepository = new MemoryCharacterRepository();
        service = new SaveCharacterService(memoryRepository);
    }

    @Test
    @DisplayName("저장 후 조회 테스트")
    void 저장_후_조회_테스트() {
        // give
        EntityId id = new EntityId("1234", Server.CAIN);
        Character character = mock(Character.class);

        // when
        when(character.getId()).thenReturn(id);
        service.save(character);

        // then
        Character findCharacter = service.findByEntityId(id);
        assertThat(findCharacter).isEqualTo(character);
        assertThat(findCharacter.getId()).isEqualTo(id);
    }

    @Test
    @DisplayName("업데이트 테스트")
    void 업데이트_테스트() {
        // give
        EntityId id = new EntityId("1234", Server.CAIN);
        Character oldCharacter = mock(Character.class);
        Character newCharacter = mock(Character.class);

        when(oldCharacter.getId()).thenReturn(id);
        when(newCharacter.getId()).thenReturn(id);

        // when
        service.save(oldCharacter);
        service.update(newCharacter);

        // then
        Character findCharacter = service.findByEntityId(id);
        assertThat(findCharacter).isEqualTo(newCharacter);
        assertThat(findCharacter.getId()).isEqualTo(id);
    }



}
