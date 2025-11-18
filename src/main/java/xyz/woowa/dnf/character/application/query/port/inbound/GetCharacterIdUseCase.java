package xyz.woowa.dnf.character.application.query.port.inbound;
import java.util.List;

public interface GetCharacterIdUseCase {
    record Result(String serverId, String id){}
    record CharacterCommand(String serverId, String name){}
    List<Result> getCharactersId(CharacterCommand command);
}
