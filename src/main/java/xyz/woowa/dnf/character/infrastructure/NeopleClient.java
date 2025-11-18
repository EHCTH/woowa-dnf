package xyz.woowa.dnf.character.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriBuilder;
import xyz.woowa.dnf.character.infrastructure.external.ExternalHandler;
import xyz.woowa.dnf.character.infrastructure.external.NeoplePaths;
import xyz.woowa.dnf.common.config.NeopleProps;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class NeopleClient {
    private static final String API_KEY = "apikey";
    private final RestClient neopleRestClient;
    private final NeopleProps props;
    private final ExternalHandler handler;

    public <T> T get(NeoplePaths path, Class<T> type, Object... vars) {
        return handler.handle(() -> neopleRestClient.get()
                .uri(u -> u.path(path.getPath())
                        .queryParam(API_KEY, props.apiKey()).build(vars))
                .retrieve()
                .body(type));
    }

    public <T> T get(NeoplePaths path, Class<T> type, Map<String, ?> query, Object... vars) {
        return handler.handle(() -> neopleRestClient.get()
                .uri(uri -> {
                    UriBuilder builder = uri.path(path.getPath());
                    if (query != null) {
                        query.forEach(builder::queryParam);
                    }
                    builder.queryParam(API_KEY, props.apiKey());
                    return builder.build(vars);
                })
                .retrieve()
                .body(type));
    }
}
