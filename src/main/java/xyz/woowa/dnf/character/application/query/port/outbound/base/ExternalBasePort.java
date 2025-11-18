package xyz.woowa.dnf.character.application.query.port.outbound.base;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;

import java.util.List;

public interface ExternalBasePort {
    BasicDto basic(String serverId, String id);

    StatusRow status(String serverId, String id);

    record BasicDto(
            @JsonSetter(nulls = Nulls.AS_EMPTY) String serverId,
            @JsonSetter(nulls = Nulls.AS_EMPTY) String characterId,
            @JsonSetter(nulls = Nulls.AS_EMPTY) String characterName,
            @JsonSetter(nulls = Nulls.AS_EMPTY) Integer level,
            @JsonSetter(nulls = Nulls.AS_EMPTY) String jobGrowName,
            @JsonSetter(nulls = Nulls.AS_EMPTY) int fame,
            @JsonSetter(nulls = Nulls.AS_EMPTY) String adventureName,
            @JsonSetter(nulls = Nulls.AS_EMPTY) String guildName
    ) {
    }


    record StatusRow(
            List<Buff> buff,
            List<Status> status) {

        public record Buff(
                @JsonSetter(nulls = Nulls.AS_EMPTY) String name,
                @JsonSetter(nulls = Nulls.AS_EMPTY) Integer level,
                @JsonSetter(nulls = Nulls.AS_EMPTY, contentNulls = Nulls.SKIP) List<Status> status) {
        }

        public record Status(
                @JsonSetter(nulls = Nulls.AS_EMPTY) String name,
                @JsonSetter(nulls = Nulls.AS_EMPTY) String value) {
        }
    }
}
