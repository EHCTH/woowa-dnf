package xyz.woowa.dnf.character.application.query.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.woowa.dnf.character.application.query.assembler.base.BaseMapper;
import xyz.woowa.dnf.character.application.query.port.inbound.GetBaseUseCase;
import xyz.woowa.dnf.character.application.query.port.inbound.GetCharacterDetailUseCase;
import xyz.woowa.dnf.character.application.query.port.outbound.base.ExternalBasePort;
import xyz.woowa.dnf.character.domain.base.Base;
import xyz.woowa.dnf.common.BoundedAsync;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class GetBaseService implements GetBaseUseCase {
    private final ExternalBasePort basePort;
    private final BaseMapper baseMapper;
    private final BoundedAsync async;

    @Override
    public List<Base> getAll(List<GetCharacterDetailUseCase.Command> commands) {
        var map = async.toMap(commands, this::fetchAndMapSafe);
        return commands.stream()
                .map(map::get)
                .filter(Objects::nonNull)
                .toList();
    }

    @Override
    public Base get(GetCharacterDetailUseCase.Command command) {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            var basicF = async(() -> basePort.basic(command.serverId(), command.id()), executor);
            var statusF = async(() -> basePort.status(command.serverId(), command.id()), executor);

            CompletableFuture.allOf(basicF, statusF)
                    .join();

            var basic = basicF.join();
            var status = statusF.join();
            return baseMapper.toDomain(basic, status);
        }
    }

    private Base fetchAndMapSafe(GetCharacterDetailUseCase.Command command) {
        var row = basePort.basic(command.serverId(), command.id());
        if (row == null) {
            return null;
        }
        return baseMapper.toDomain(row);
    }

    private <T> CompletableFuture<T> async(Supplier<T> supplier, ExecutorService executor) {
        return CompletableFuture.supplyAsync(supplier, executor);
    }
}
