package xyz.woowa.dnf.character.infrastructure.external;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import xyz.woowa.dnf.common.exception.DomainException;

import java.util.function.Supplier;

@Component
public class ExternalHandler {
    public <T> T handle(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (RestClientException restClientException) {
            throw new DomainException("external.failed", restClientException.getMessage());
        }
    }
}
