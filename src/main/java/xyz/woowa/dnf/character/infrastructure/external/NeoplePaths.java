package xyz.woowa.dnf.character.infrastructure.external;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NeoplePaths {

    DETAIL("/df/items/{itemId}"),
    BUFF_EQUIP("df/servers/{serverId}/characters/{characterId}/skill/buff/equip/equipment"),
    SEARCH("/df/servers/{serverId}/characters"),
    AVATAR("/df/servers/{serverId}/characters/{characterId}/equip/avatar"),
    BUFF_AVATAR("/df/servers/{serverId}/characters/{characterId}/skill/buff/equip/avatar"),
    STATUS("/df/servers/{serverId}/characters/{characterId}/status"),
    BASE("/df/servers/{serverId}/characters/{characterId}"),
    EQUIP("/df/servers/{serverId}/characters/{characterId}/equip/equipment"),
    CREATURE("/df/servers/{serverId}/characters/{characterId}/equip/creature"),
    BUFF_CREATURE("/df/servers/{serverId}/characters/{characterId}/skill/buff/equip/creature"),
    FLAG("/df/servers/{serverId}/characters/{characterId}/equip/flag");
    private final String path;
}
