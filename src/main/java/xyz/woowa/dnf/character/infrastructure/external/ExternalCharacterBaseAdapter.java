package xyz.woowa.dnf.character.infrastructure.external;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.woowa.dnf.character.application.query.port.outbound.base.ExternalBasePort;
import xyz.woowa.dnf.character.infrastructure.NeopleClient;

@Component
@RequiredArgsConstructor
public class ExternalCharacterBaseAdapter implements ExternalBasePort {
    private final NeopleClient neopleClient;

    @Override
    public BasicDto basic(String serverId, String id) {
        return neopleClient.get(NeoplePaths.BASE, BasicDto.class, serverId, id);
    }

    @Override
    public StatusRow status(String serverId, String id) {
        return neopleClient.get(NeoplePaths.STATUS, StatusRow.class, serverId, id);
    }

}
