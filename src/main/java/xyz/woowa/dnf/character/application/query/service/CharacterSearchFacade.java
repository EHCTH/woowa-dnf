package xyz.woowa.dnf.character.application.query.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.woowa.dnf.character.application.command.port.outbound.CharacterRepository;
import xyz.woowa.dnf.character.application.query.assembler.base.BaseDtoMapper;
import xyz.woowa.dnf.character.application.query.dto.base.BaseDto;
import xyz.woowa.dnf.character.application.query.port.inbound.GetCharacterDetailUseCase;
import xyz.woowa.dnf.character.application.query.port.inbound.GetCharacterIdUseCase;
import xyz.woowa.dnf.character.domain.Character;
import xyz.woowa.dnf.character.domain.base.Base;

import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class CharacterSearchFacade {
    private final GetCharacterIdUseCase idQuery;
    private final CharacterRepository repository;
    private final GetCharacterDetailUseCase detailQuery;
    private final BaseDtoMapper baseDtoMapper;

    public List<BaseDto> searchAll(String server, String name) {
        var refs = idQuery.getCharactersId(toCommand(server, name));
        if (refs.isEmpty()) {
            return List.of();
        }
        var commands = refs.stream()
                .map(this::toCommand)
                .toList();

        return detailQuery.getAll(commands)
                .stream()
                .map(baseDtoMapper::toDto)
                .sorted(BaseDto::descByFame)
                .toList();
    }


    public List<BaseDto> guildSearchAll(String guildName) {
        String normalizeGuildName = normalizeName(guildName);
        List<Character> allByGuildName = repository.findAllByGuildName(normalizeGuildName);
        return allByGuildName.stream()
                .map(this::baseDtoHelper)
                .sorted(BaseDto::descByFame)
                .toList();
    }

    public List<BaseDto> adventureSearchAll(String adventureName) {
        String normalizeAdventureName = normalizeName(adventureName);
        List<Character> allByAdventureName = repository.findAllByAdventureName(normalizeAdventureName);
        return allByAdventureName.stream()
                .map(this::baseDtoHelper)
                .sorted(BaseDto::descByFame)
                .toList();
    }

    private BaseDto baseDtoHelper(Character character) {
        Base base = character.getBase();
        return baseDtoMapper.toDto(base);
    }

    private GetCharacterDetailUseCase.Command toCommand(GetCharacterIdUseCase.Result result) {
        return new GetCharacterDetailUseCase.Command(result.serverId(), result.id());
    }

    private GetCharacterIdUseCase.CharacterCommand toCommand(String server, String name) {
        return new GetCharacterIdUseCase.CharacterCommand(server, name);
    }

    private String normalizeName(String name) {
        return name.toUpperCase(Locale.ROOT);
    }

}
