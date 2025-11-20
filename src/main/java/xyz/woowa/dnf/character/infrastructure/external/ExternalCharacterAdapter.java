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
    private final NeopleClient neopleClient;

    @Override
    public List<Row> search(Server server, Name name) {
        try {
            WordType wordType = WordType.findByNameLength(name.value());
            SearchResponse resp = searchResponse(server, name, wordType);
            if (resp == null || resp.rows() == null || resp.rows().isEmpty()) {
                throw new DomainException("character.not-found", name.value());
            }
            return resp.rows();

        } catch (RestClientException e) {
            throw new DomainException("external.failed", e.getMessage());
        }
    }

    private SearchResponse searchResponse(Server server, Name name, WordType wordType) {
        return neopleClient.get(
                NeoplePaths.SEARCH,
                SearchResponse.class,
                Map.of("characterName", name.value(),
                        "limit", 50,
                        "wordType", wordType.getCode()
                ),
                server.getEnglish()
        );
    }


}
