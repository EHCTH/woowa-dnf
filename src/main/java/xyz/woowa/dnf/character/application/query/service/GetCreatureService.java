package xyz.woowa.dnf.character.application.query.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.woowa.dnf.character.application.query.assembler.creature.CreatureMapper;
import xyz.woowa.dnf.character.application.query.port.inbound.GetCharacterDetailUseCase;
import xyz.woowa.dnf.character.application.query.port.inbound.GetCreatureUseCase;
import xyz.woowa.dnf.character.application.query.port.outbound.equipment.ExternalCreaturePort;
import xyz.woowa.dnf.character.domain.equipment.creature.Creature;
import xyz.woowa.dnf.common.BoundedAsync;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetCreatureService implements GetCreatureUseCase {
    private final ExternalCreaturePort port;
    private final CreatureMapper mapper;
    private final BoundedAsync async;


    @Override
    public Creature creature(GetCharacterDetailUseCase.Command command) {
        var creature = port.row(command.serverId(), command.id()).creature();
        if (creature == null) {
            return mapper.toCreature(ExternalCreaturePort.Creature.EMPTY);
        }
        var detail = port.detail(creature.itemId());
        var artifactDetail = getArtifactDetail(creature.artifact(), creature);
        var merge = merge(creature, detail, artifactDetail);
        return mapper.toCreature(merge);
    }

    @Override
    public Creature buffCreature(GetCharacterDetailUseCase.Command command) {
        var buffRow = port.buffRow(command.serverId(), command.id());
        if (!buffRow.hasCreature()) {
            return mapper.toCreature(ExternalCreaturePort.Creature.EMPTY);
        }
        var creature = buffRow.creature();
        var detail = port.detail(creature.itemId());
        var merge = merge(creature, detail);
        return mapper.toCreature(merge);


    }

    private List<ExternalCreaturePort.Artifact> getArtifactDetail(
            List<ExternalCreaturePort.Artifact> artifacts,
            ExternalCreaturePort.Creature creature) {
        var creatureIdsMap = baseIdsMap(artifacts);
        var artifactDetailMap = async.toMap(creatureIdsMap, port::artifactDetail);
        return artifactMerge(creature.artifact(), artifactDetailMap);
    }

    private ExternalCreaturePort.Creature merge(ExternalCreaturePort.Creature creature,
                           ExternalCreaturePort.Detail creatureDetail,
                           List<ExternalCreaturePort.Artifact> artifactDetail) {
        return creature.withArtifact(artifactDetail).withDetail(creatureDetail);
    }

    private ExternalCreaturePort.Creature merge(ExternalCreaturePort.Creature creature,
                                                ExternalCreaturePort.Detail creatureDetail) {
        return creature.withDetail(creatureDetail);
    }

    private List<ExternalCreaturePort.Artifact> artifactMerge(
            List<ExternalCreaturePort.Artifact> artifacts,
            Map<String, ExternalCreaturePort.Detail> detailMap) {

        return artifacts.stream().map(it -> Optional.ofNullable(detailMap.get(it.itemId()))
                .map(it::withDetail)
                .orElse(it)
        ).toList();
    }

    private LinkedHashSet<String> baseIdsMap(List<ExternalCreaturePort.Artifact> artifact) {
        return artifact.stream().map(ExternalCreaturePort.Artifact::itemId)
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

}
