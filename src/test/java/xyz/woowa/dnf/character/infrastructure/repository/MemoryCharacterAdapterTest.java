package xyz.woowa.dnf.character.infrastructure.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import xyz.woowa.dnf.character.application.command.port.outbound.CharacterRepository;
import xyz.woowa.dnf.character.domain.Character;
import xyz.woowa.dnf.character.domain.base.EntityId;
import xyz.woowa.dnf.character.domain.base.Server;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MemoryCharacterAdapterTest {
    private CharacterRepository repository;

    @BeforeEach
    void init() {
        repository = new MemoryCharacterAdapter();
    }

    @Test
    @DisplayName("캐릭터 서버 조회 테스트")
    void 캐릭터_서버_조회_테스트() {
        // give
        EntityId id = new EntityId("1234", Server.CAIN);
        Character character = mock(Character.class);

        // when
        when(character.getId()).thenReturn(id);
        repository.save(character);

        // then
        Character findCharacter = repository.findById(id);
        assertThat(findCharacter).isEqualTo(character);
        assertThat(id).isEqualTo(findCharacter.getId());
    }


    @Test
    @DisplayName("캐릭터 길드 조회 테스트")
    void 캐릭터_길드명_조회_테스트() {
        // give
        EntityId id = new EntityId("1234", Server.CAIN);
        String guild = "길드A";
        Character character = mock(Character.class);

        // when
        when(character.getId()).thenReturn(id);
        when(character.guildName()).thenReturn(guild);
        when(character.equalsToGuildName(guild)).thenReturn(true);
        repository.save(character);

        // then
        Character findCharacter = repository.findAllByGuildName(guild).getFirst();
        assertThat(findCharacter).isEqualTo(character);
        assertThat(findCharacter.guildName()).isEqualTo(guild);
        assertThat(id).isEqualTo(findCharacter.getId());
    }

    @Test
    @DisplayName("캐릭터 모험단 조회 테스트")
    void 캐릭터_모험단_조회_테스트() {
        // give
        EntityId id = new EntityId("1234", Server.CAIN);
        String adventureName = "모험단A";
        Character character = mock(Character.class);

        // when
        when(character.getId()).thenReturn(id);
        when(character.adventureName()).thenReturn(adventureName);
        when(character.equalsToAdventureName(adventureName)).thenReturn(true);
        repository.save(character);

        // then
        Character findCharacter = repository.findAllByAdventureName(adventureName).getFirst();
        assertThat(findCharacter).isEqualTo(character);
        assertThat(findCharacter.adventureName()).isEqualTo(adventureName);
        assertThat(id).isEqualTo(findCharacter.getId());
    }


    @Test
    @DisplayName("업데이트 테스트")
    void 업데이트_테스트() {
        // give
        EntityId id = new EntityId("1234", Server.CAIN);
        Character oldCharacter = mock(Character.class);
        Character newCharacter = mock(Character.class);

        // when
        when(oldCharacter.getId()).thenReturn(id);
        when(newCharacter.getId()).thenReturn(id);

        repository.save(oldCharacter);
        repository.update(newCharacter);

        // then
        Character findCharacter = repository.findById(id);
        assertThat(findCharacter).isEqualTo(newCharacter);
    }
}
