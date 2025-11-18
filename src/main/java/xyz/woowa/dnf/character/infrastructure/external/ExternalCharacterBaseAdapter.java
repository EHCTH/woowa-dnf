package xyz.woowa.dnf.character.infrastructure.external;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.woowa.dnf.character.application.query.port.outbound.base.ExternalBasePort;
import xyz.woowa.dnf.character.infrastructure.NeopleClient;

@Component
@RequiredArgsConstructor
public class ExternalCharacterBaseAdapter implements ExternalBasePort {
    private static final String BASE_PATH = "/df/servers/{serverId}/characters/{characterId}";
    private static final String STATUS_PATH = "/df/servers/{serverId}/characters/{characterId}/status";
    private final NeopleClient neopleClient;

    @Override
    public BasicDto basic(String serverId, String id) {
        return neopleClient.get(BASE_PATH, BasicDto.class, serverId, id);
    }

    @Override
    public StatusRow status(String serverId, String id) {
        return neopleClient.get(STATUS_PATH, StatusRow.class, serverId, id);
    }

}
