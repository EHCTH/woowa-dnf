package xyz.woowa.dnf.character.application.query.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.woowa.dnf.character.application.query.port.inbound.GetCharacterIdUseCase;
import xyz.woowa.dnf.character.application.query.port.outbound.base.ExternalCharacterPort;
import xyz.woowa.dnf.character.domain.base.Name;
import xyz.woowa.dnf.character.domain.base.Server;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetIdService implements GetCharacterIdUseCase {
    private final ExternalCharacterPort externalCharacterPort;
    @Override
    public List<Result> getCharactersId(CharacterCommand command) {
        Server server = Server.fromEnglish(command.serverId());
        Name name = new Name(command.name());
        List<ExternalCharacterPort.Row> rows = externalCharacterPort.search(server, name);
        return rows.stream()
                .map(this::toResult)
                .toList();
    }
    private Result toResult(ExternalCharacterPort.Row row) {
        return new Result(row.serverId(), row.characterId());
    }
}
