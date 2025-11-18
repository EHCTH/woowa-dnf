package xyz.woowa.dnf.character.application.query.port.outbound.equipment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import lombok.With;

import java.util.Collections;
import java.util.List;

public interface ExternalAvatarPort {

    Avatars itemAvatar(String serverId, String characterId);

    Detail detail(String itemId);
    BuffAvatarRow buffAvatarRow(String serverId, String characterId);

    record BuffAvatarRow(Skill skill) {
        public List<Avatar> avatars() {
            if (skill.buff == null) {
                return Collections.emptyList();
            }
            return skill.buff.avatar;
        }
    }

    @With
    record Skill(Buff buff) {

    }

    @With
    record Buff(
            @JsonSetter(nulls = Nulls.AS_EMPTY, contentNulls = Nulls.SKIP) List<Avatar> avatar) {
    }



    record Avatars(@JsonSetter(nulls = Nulls.AS_EMPTY, contentNulls = Nulls.SKIP) List<Avatar> avatar) {
    }


    @With
    record Avatar(String slotId,
                  String slotName,
                  String itemId,
                  String itemName,
                  String itemRarity,
                  @JsonProperty("clone") Clone cloneAvatar,
                  @JsonSetter(nulls = Nulls.AS_EMPTY) String optionAbility,
                  @JsonSetter(nulls = Nulls.AS_EMPTY, contentNulls = Nulls.SKIP) List<Emblem> emblems,
                  Detail detail) {
    }

    record Clone(@JsonSetter(nulls = Nulls.AS_EMPTY) String itemId,
                 @JsonSetter(nulls = Nulls.AS_EMPTY) String itemName) {
    }

    record Emblem(@JsonSetter(nulls = Nulls.AS_EMPTY) String slotNo,
                  @JsonSetter(nulls = Nulls.AS_EMPTY) String slotColor,
                  @JsonSetter(nulls = Nulls.AS_EMPTY) String itemId,
                  @JsonSetter(nulls = Nulls.AS_EMPTY) String itemName,
                  @JsonSetter(nulls = Nulls.AS_EMPTY) String itemRarity) {
    }

    record Detail(@JsonSetter(nulls = Nulls.AS_EMPTY) String fame,
                  @JsonSetter(nulls = Nulls.AS_EMPTY) String itemExplain,
                  @JsonSetter(nulls = Nulls.AS_EMPTY, contentNulls = Nulls.SKIP) List<Status> itemStatus) {
    }
    record Status( @JsonSetter(nulls = Nulls.AS_EMPTY) String name,  @JsonSetter(nulls = Nulls.AS_EMPTY) String value) {

    }
}


