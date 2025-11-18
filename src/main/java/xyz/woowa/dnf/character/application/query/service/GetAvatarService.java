package xyz.woowa.dnf.character.application.query.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.woowa.dnf.character.application.query.assembler.avatar.AvatarMapper;
import xyz.woowa.dnf.character.application.query.port.inbound.GetAvatarUseCase;
import xyz.woowa.dnf.character.application.query.port.inbound.GetCharacterDetailUseCase;
import xyz.woowa.dnf.character.application.query.port.outbound.equipment.ExternalAvatarPort;
import xyz.woowa.dnf.character.domain.equipment.avatar.Avatars;
import xyz.woowa.dnf.common.BoundedAsync;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetAvatarService implements GetAvatarUseCase {
    private final ExternalAvatarPort port;
    private final AvatarMapper mapper;
    private final BoundedAsync async;

    @Override
    public Avatars avatars(GetCharacterDetailUseCase.Command command) {
        var avatars = port.itemAvatar(command.serverId(), command.id());
        return toAvatars(avatars);
    }

    @Override
    public Avatars buffAvatars(GetCharacterDetailUseCase.Command command) {
        var avatars = new ExternalAvatarPort.Avatars(port.buffAvatarRow(command.serverId(), command.id()).avatars());
        return toAvatars(avatars);
    }

    private Avatars toAvatars(ExternalAvatarPort.Avatars avatars) {
        var itemIds = baseIdsMap(avatars.avatar());
        Map<String, ExternalAvatarPort.Detail> detailMap = async.toMap(itemIds, port::detail);
        return merge(avatars, detailMap).stream()
                .map(mapper::toAvatar)
                .collect(Collectors.collectingAndThen(Collectors.toList(), Avatars::new));
    }


    private LinkedHashSet<String> baseIdsMap(List<ExternalAvatarPort.Avatar> avatars) {
        return avatars.stream()
                .map(ExternalAvatarPort.Avatar::itemId)
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private List<ExternalAvatarPort.Avatar> merge(ExternalAvatarPort.Avatars avatars,
                                                  Map<String, ExternalAvatarPort.Detail> detailMap) {

        return avatars.avatar()
                .stream()
                .map(avatar -> Optional.ofNullable(detailMap.get(avatar.itemId()))
                        .map(avatar::withDetail).
                        orElse(avatar)
                )
                .toList();
    }

}
