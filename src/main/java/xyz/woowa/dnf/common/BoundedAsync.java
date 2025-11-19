package xyz.woowa.dnf.common;

import lombok.Builder;

import java.time.Duration;
import java.util.Collection;
import java.util.LinkedHashMap;
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
            return Map.of();
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
                .collect(Collectors.toMap(
                        Function.identity(),
                        key -> submit(key, task, semaphore),
                        (a, b) -> a,
                        LinkedHashMap::new
                ));
    }

    private <K, V> CompletableFuture<V> submit(K key, Function<K, V> task, Semaphore semaphore) {
        return CompletableFuture
                .supplyAsync(() -> runWithPermit(semaphore, () -> task.apply(key)), executor)
                .orTimeout(timeout.toSeconds(), TimeUnit.SECONDS)
                .exceptionally(ex -> null);
    }

    private <V> V runWithPermit(Semaphore semaphore, Supplier<V> supplier) {
        try {
            semaphore.acquire();
            return supplier.get();
        }
        catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            return null;
        }
        finally {
            semaphore.release();
        }
    }

    private <K, V> Map<K, V> collectResults(Map<K, CompletableFuture<V>> futures) {
        Map<K, V> out = new LinkedHashMap<>(futures.size());
        futures.forEach((key, f) -> {
            V join = f.join();
            if (join != null) {
                out.put(key, join);
            }
        });
        return out;
    }
}
