package xyz.woowa.dnf.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.woowa.dnf.common.BoundedAsync;

import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ConcurrencyConfig {
    @Bean
    ExecutorService io() {
        return Executors.newVirtualThreadPerTaskExecutor();
    }

    @Bean
    BoundedAsync boundedAsync(ExecutorService io) {
        return BoundedAsync.builder()
                .executor(io)
                .maxConcurrency(12)
                .timeout(Duration.ofSeconds(3))
                .build();
    }
}
