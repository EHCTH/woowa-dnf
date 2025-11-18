package xyz.woowa.dnf.character.application.query.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import xyz.woowa.dnf.character.application.command.port.outbound.CharacterRepository;
import xyz.woowa.dnf.character.application.query.assembler.base.BaseDtoMapper;
import xyz.woowa.dnf.character.application.query.dto.base.BaseDto;
import xyz.woowa.dnf.character.application.query.port.inbound.GetCharacterDetailUseCase;
import xyz.woowa.dnf.character.application.query.port.inbound.GetCharacterIdUseCase;
import xyz.woowa.dnf.character.domain.Character;
import xyz.woowa.dnf.character.domain.base.Base;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CharacterSearchFacadeTest {
    private final GetCharacterIdUseCase idQuery = mock(GetCharacterIdUseCase.class);
    private final CharacterRepository repository = mock(CharacterRepository.class);
    private final GetCharacterDetailUseCase detailQuery = mock(GetCharacterDetailUseCase.class);
    private final BaseDtoMapper baseDtoMapper = mock(BaseDtoMapper.class);

    private final CharacterSearchFacade facade = new CharacterSearchFacade(idQuery, repository, detailQuery, baseDtoMapper);

    @Test
    @DisplayName("해당 데이터가 없을경우 빈 리스트 반환")
    void 해당_데이터가_없을경우_빈_리스트_반환() {
        // give
        String serverId = "CAIN";
        String name = "1234";
        var command = new GetCharacterIdUseCase.CharacterCommand(serverId, name);

        // when
        when(idQuery.getCharactersId(command)).thenReturn(Collections.emptyList());

        // then
        List<BaseDto> baseDtos = facade.searchAll(serverId, name);
        assertThat(baseDtos).isEmpty();
    }

    @Test
    @DisplayName("해당 데이터가 있을경우 BaseDto 리스트 반환")
    void 해당_데이터가_있을경우_BaseDto_리스트_반환() {
        // give
        String serverId = "CAIN";
        String name = "1234";
        var command = new GetCharacterIdUseCase.CharacterCommand(serverId, name);

        var id1 = new GetCharacterIdUseCase.Result(serverId, "id1");
        var id2 = new GetCharacterIdUseCase.Result(serverId, "id2");

        var detailCmd1 = new GetCharacterDetailUseCase.Command(serverId, "id1");
        var detailCmd2 = new GetCharacterDetailUseCase.Command(serverId, "id2");

        Base base1 = mock(Base.class);
        Base base2 = mock(Base.class);

        BaseDto dto1 = mock(BaseDto.class);
        BaseDto dto2 = mock(BaseDto.class);

        // when
        when(idQuery.getCharactersId(command)).thenReturn(List.of(id1, id2));
        when(detailQuery.getAll(List.of(detailCmd1, detailCmd2))).thenReturn(List.of(base1, base2));
        when(baseDtoMapper.toDto(base1)).thenReturn(dto1);
        when(baseDtoMapper.toDto(base2)).thenReturn(dto2);
        // then

        List<BaseDto> result = facade.searchAll(serverId, name);
        assertThat(result).containsExactly(dto1, dto2);
    }


    @Test
    @DisplayName("길드명으로 조회 후 BaseDto 리스트를 반환한다")
    void 길드명으로_조회_후_BaseDto_리스트를_반환한다() {
        // given
        String guildName = "길드A";
        Character character = mock(Character.class);
        Base base = mock(Base.class);
        BaseDto dto = mock(BaseDto.class);

        // when
        when(repository.findAllByGuildName(guildName.toUpperCase())).thenReturn(List.of(character));
        when(character.getBase()).thenReturn(base);
        when(baseDtoMapper.toDto(base)).thenReturn(dto);

        // then
        List<BaseDto> result = facade.guildSearchAll(guildName);
        assertThat(result).containsExactly(dto);
    }

    @Test
    @DisplayName("모험단명으로 조회 후 BaseDto 리스트를 반환한다")
    void 모험단명으로_조회_후_BaseDto_리스트를_반환한다() {
        // given
        String adventureName = "모험단A";
        Character character = mock(Character.class);
        Base base = mock(Base.class);
        BaseDto dto = mock(BaseDto.class);

        // when
        when(repository.findAllByAdventureName(adventureName.toUpperCase())).thenReturn(List.of(character));
        when(character.getBase()).thenReturn(base);
        when(baseDtoMapper.toDto(base)).thenReturn(dto);

        // then
        List<BaseDto> result = facade.adventureSearchAll(adventureName);
        assertThat(result).containsExactly(dto);
    }
}
