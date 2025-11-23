package xyz.woowa.dnf.character.application.query.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.woowa.dnf.character.application.query.assembler.avatar.AvatarMapper;
import xyz.woowa.dnf.character.application.query.port.inbound.GetAvatarUseCase;
import xyz.woowa.dnf.character.application.query.port.inbound.GetCharacterDetailUseCase;
import xyz.woowa.dnf.character.application.query.port.outbound.equipment.ExternalAvatarPort;
import xyz.woowa.dnf.character.domain.equipment.avatar.Avatar;
import xyz.woowa.dnf.character.domain.equipment.avatar.Avatars;
import xyz.woowa.dnf.character.domain.equipment.avatar.vo.AvatarSlot;
import xyz.woowa.dnf.common.BoundedAsync;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetAvatarService implements GetAvatarUseCase {
    private static final Function<Avatar, AvatarSlot> AVATAR_SLOT = avatar ->
            avatar.getBase().avatarSlot();
    private static final Predicate<Avatar> IS_BUFF_AVATAR = avatar ->
            AvatarSlot.isBuffAvatar(AVATAR_SLOT.apply(avatar));
    private static final Predicate<Avatar> IS_NORMAL_AVATAR = avatar -> true;
    private final ExternalAvatarPort port;
    private final AvatarMapper mapper;
    private final BoundedAsync async;

    @Override
    public Avatars avatars(GetCharacterDetailUseCase.Command command) {
        var avatars = port.itemAvatar(command.serverId(), command.id());
        return toAvatars(avatars, IS_NORMAL_AVATAR);
    }

    @Override
    public Avatars buffAvatars(GetCharacterDetailUseCase.Command command) {
        var avatars = new ExternalAvatarPort.Avatars(port.buffAvatarRow(command.serverId(), command.id()).avatars());
        return toAvatars(avatars, IS_BUFF_AVATAR);
    }

    private Avatars toAvatars(ExternalAvatarPort.Avatars avatars, Predicate<Avatar> predicate) {
        var itemIds = baseIdsMap(avatars.avatar());
        Map<String, ExternalAvatarPort.Detail> detailMap = async.toMap(itemIds, port::detail);
        return merge(avatars, detailMap).stream()
                .map(mapper::toAvatar)
                .filter(predicate)
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
