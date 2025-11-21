package xyz.woowa.dnf.common;

import lombok.Builder;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Builder
public class BoundedAsync {
    private final Executor executor;
    private final int maxConcurrency;
    private final Duration timeout;
    private final boolean fair;

    public <K, V> Map<K, V> toMap(Collection<K> keys, Function<K, V> task) {
        if (keys == null || keys.isEmpty()) {
            return Collections.emptyMap();
        }
        Semaphore semaphore = createSemaphore();
        Map<K, CompletableFuture<V>> futures = submit(keys, task, semaphore);
        return collectResults(futures);
    }

    private Semaphore createSemaphore() {
        return new Semaphore(maxConcurrency, fair);
    }

    private <K, V> Map<K, CompletableFuture<V>> submit(Collection<K> keys, Function<K, V> task, Semaphore semaphore) {
        return keys.stream()
                .collect(Collectors.toMap(Function.identity(), key -> submit(key, task, semaphore)));
    }

    private <K, V> CompletableFuture<V> submit(K key, Function<K, V> task, Semaphore semaphore) {
        return CompletableFuture.supplyAsync(() -> runWithPermit(semaphore, () -> task.apply(key)), executor)
                .orTimeout(timeout.toSeconds(), TimeUnit.SECONDS)
                .exceptionally(ex -> null);
    }

    private <V> V runWithPermit(Semaphore semaphore, Supplier<V> supplier) {
        try {
            semaphore.acquire();
            return supplier.get();
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            return null;
        } finally {
            semaphore.release();
        }
    }

    private <K, V> Map<K, V> collectResults(Map<K, CompletableFuture<V>> futures) {
        return futures.entrySet()
                .stream()
                .map(entry -> Map.entry(entry.getKey(), entry.getValue().join()))
                .filter(entry -> entry.getValue() != null)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
