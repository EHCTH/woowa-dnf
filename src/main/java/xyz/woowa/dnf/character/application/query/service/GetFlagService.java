package xyz.woowa.dnf.character.application.query.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.woowa.dnf.character.application.query.assembler.flag.FlagMapper;
import xyz.woowa.dnf.character.application.query.port.inbound.GetCharacterDetailUseCase;
import xyz.woowa.dnf.character.application.query.port.inbound.GetFlagUseCase;
import xyz.woowa.dnf.character.application.query.port.outbound.equipment.ExternalAvatarPort;
import xyz.woowa.dnf.character.application.query.port.outbound.equipment.ExternalFlagPort;
import xyz.woowa.dnf.character.domain.equipment.flag.Flag;
import xyz.woowa.dnf.common.BoundedAsync;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetFlagService implements GetFlagUseCase {
    private final ExternalFlagPort port;
    private final FlagMapper mapper;
    private final BoundedAsync async;
    @Override
    public Flag flag(GetCharacterDetailUseCase.Command command) {
        var flag = port.flagRow(command.serverId(), command.id()).flag();
        if (flag == null) {
            return Flag.EMPTY;
        }
        var flagDetail = port.detail(flag.itemId());

        var newFlag = flag.withDetail(flagDetail);
        List<String> gemIds = newFlag.gems()
                .stream()
                .map(ExternalFlagPort.Gem::itemId)
                .toList();
        var gemDetailMap = async.toMap(idsMap(gemIds), port::detail);
        var merge = merge(newFlag, gemDetailMap);
        return mapper.toFlag(merge);
    }

    private LinkedHashSet<String> idsMap(List<String> ids) {
        return ids.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public ExternalFlagPort.Flag merge(ExternalFlagPort.Flag flag, Map<String, ExternalFlagPort.Detail> gemDetailMap) {
        var newGems = flag.gems()
                .stream()
                .map(gem -> {
                    var gemDetail = gemDetailMap.get(gem.itemId());
                    return gem.withDetail(gemDetail);
                })
                .toList();
        return flag.withGems(newGems);
    }
}
