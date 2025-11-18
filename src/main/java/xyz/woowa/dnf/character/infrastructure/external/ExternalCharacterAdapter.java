package xyz.woowa.dnf.character.infrastructure.external;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import xyz.woowa.dnf.character.application.query.port.outbound.base.ExternalCharacterPort;
import xyz.woowa.dnf.character.domain.base.Name;
import xyz.woowa.dnf.character.domain.base.Server;
import xyz.woowa.dnf.character.infrastructure.NeopleClient;
import xyz.woowa.dnf.common.exception.DomainException;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ExternalCharacterAdapter implements ExternalCharacterPort {
    private static final String SEARCH_PATH = "/df/servers/{serverId}/characters";
    private final NeopleClient neopleClient;

    @Override
    public List<Row> search(Server server, Name name) {
        try {
            SearchResponse resp = neopleClient.get(
                    SEARCH_PATH,
                    SearchResponse.class,
                    Map.of("characterName", name.value(),
                            "limit", 50,
                            "wordType", WordType.full.name()
                    ),
                    server.getEnglish()
            );
            if (resp == null || resp.rows() == null || resp.rows().isEmpty()) {
                throw new DomainException("character.not-found", name.value());
            }
            return resp.rows();

        } catch (RestClientException e) {
            throw new DomainException("external.failed", e.getMessage());
        }
    }


}
