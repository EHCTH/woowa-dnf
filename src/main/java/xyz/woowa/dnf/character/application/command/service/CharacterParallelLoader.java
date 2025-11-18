package xyz.woowa.dnf.character.application.command.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import xyz.woowa.dnf.character.application.query.port.inbound.GetCharacterDetailUseCase;
import xyz.woowa.dnf.character.domain.Character;
import xyz.woowa.dnf.character.domain.base.Base;
import xyz.woowa.dnf.character.domain.base.EntityId;
import xyz.woowa.dnf.character.domain.equipment.avatar.Avatars;
import xyz.woowa.dnf.character.domain.equipment.buff.equipment.BuffEquipment;
import xyz.woowa.dnf.character.domain.equipment.creature.Creature;
import xyz.woowa.dnf.character.domain.equipment.equipment.Equipment;
import xyz.woowa.dnf.character.domain.equipment.flag.Flag;


import java.util.concurrent.*;
import java.util.function.Function;
import java.util.function.Supplier;

@Component
@Slf4j
@RequiredArgsConstructor
public class CharacterParallelLoader {

    private final GetCharacterDetailUseCase query;

    public Character load(EntityId entityId, GetCharacterDetailUseCase.Command command) {
        return executorAndLogging("외부 캐릭터 정보 병렬 조회 완료", executor -> {
            CompletableFuture<Base> baseF = async(() -> query.get(command), executor);
            return loadWithExecutor(entityId, command, baseF, executor);
        });
    }

    public Character refresh(Base base, EntityId entityId, GetCharacterDetailUseCase.Command command) {
        return executorAndLogging("외부 캐릭터 정보 병렬 리프레시 완료", executor -> {
            CompletableFuture<Base> baseF = CompletableFuture.completedFuture(base);
            return loadWithExecutor(entityId, command, baseF, executor);
        });
    }

    private Character loadWithExecutor(
            EntityId entityId,
            GetCharacterDetailUseCase.Command command,
            CompletableFuture<Base> baseF,
            ExecutorService executor
    ) {
        CompletableFuture<Equipment> equipmentF     = async(() -> query.equipment(command), executor);
        CompletableFuture<Avatars> avatarsF         = async(() -> query.avatars(command), executor);
        CompletableFuture<BuffEquipment> buffEquipF = async(() -> query.buffEquipment(command), executor);
        CompletableFuture<Avatars> buffAvatarsF     = async(() -> query.buffAvatars(command), executor);
        CompletableFuture<Creature> creatureF       = async(() -> query.creature(command), executor);
        CompletableFuture<Creature> buffCreatureF   = async(() -> query.buffCreature(command), executor);
        CompletableFuture<Flag> flagF               = async(() -> query.flag(command), executor);

        CompletableFuture.allOf(
                baseF, equipmentF, avatarsF,
                buffEquipF, buffAvatarsF,
                creatureF, buffCreatureF, flagF
        ).join();

        return Character.builder()
                .id(entityId)
                .base(baseF.join())
                .equipment(equipmentF.join())
                .buffEquipment(buffEquipF.join())
                .avatars(avatarsF.join())
                .buffAvatars(buffAvatarsF.join())
                .creature(creatureF.join())
                .buffCreature(buffCreatureF.join())
                .flag(flagF.join())
                .build();
    }

    private <T> CompletableFuture<T> async(Supplier<T> supplier, Executor executor) {
        return CompletableFuture.supplyAsync(supplier, executor);
    }


    private <T> T executorAndLogging(String label, Function<ExecutorService, T> task) {
        long start = System.currentTimeMillis();
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            return task.apply(executor);
        } finally {
            long end = System.currentTimeMillis() - start;
            log.info("{} ({} ms)", label, end);
        }
    }
}
