package xyz.woowa.dnf.character.application.query.port.outbound.equipment;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import lombok.With;

import java.util.List;

public interface ExternalFlagPort {
    Row flagRow(String serverId, String characterId);
    Detail detail(String itemId);
    @With
    record Row(Flag flag) {
    }

    @With
    record Flag(
            @JsonSetter(nulls = Nulls.AS_EMPTY) String itemId,
            @JsonSetter(nulls = Nulls.AS_EMPTY) String itemName,
            @JsonSetter(nulls = Nulls.AS_EMPTY) String itemRarity,
            @JsonSetter(nulls = Nulls.AS_EMPTY) String reinforce,
            @JsonSetter(nulls = Nulls.AS_EMPTY, contentNulls = Nulls.SKIP) List<Status> reinforceStatus,
            @JsonSetter(nulls = Nulls.AS_EMPTY, contentNulls = Nulls.SKIP) List<Gem> gems,
            Detail detail) {
    }

    record Status(
            @JsonSetter(nulls = Nulls.AS_EMPTY) String name,
            @JsonSetter(nulls = Nulls.AS_EMPTY) String value) {
    }

    @With
    record Gem(
            @JsonSetter(nulls = Nulls.AS_EMPTY) String itemId,
            @JsonSetter(nulls = Nulls.AS_EMPTY) String itemName,
            @JsonSetter(nulls = Nulls.AS_EMPTY) String itemRarity,
            Detail detail) {
    }

    record Detail(
            @JsonSetter(nulls = Nulls.AS_EMPTY) String itemTypeDetailId,
            @JsonSetter(nulls = Nulls.AS_EMPTY) String itemTypeDetail,
            @JsonSetter(nulls = Nulls.AS_EMPTY) String fame,
            @JsonSetter(nulls = Nulls.AS_EMPTY, contentNulls = Nulls.SKIP) List<Status> itemStatus) {

    }
}
