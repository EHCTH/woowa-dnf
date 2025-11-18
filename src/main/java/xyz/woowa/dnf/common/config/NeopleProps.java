package xyz.woowa.dnf.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "external.neople")
public record NeopleProps(String baseUrl, String apiKey) {
}
