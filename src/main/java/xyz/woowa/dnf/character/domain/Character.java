package xyz.woowa.dnf.character.domain;

import lombok.Builder;
import lombok.Getter;
import org.apache.logging.log4j.util.Strings;
import xyz.woowa.dnf.character.domain.base.Base;
import xyz.woowa.dnf.character.domain.base.EntityId;
import xyz.woowa.dnf.character.domain.equipment.avatar.Avatars;
import xyz.woowa.dnf.character.domain.equipment.buff.equipment.BuffEquipment;
import xyz.woowa.dnf.character.domain.equipment.creature.Creature;
import xyz.woowa.dnf.character.domain.equipment.equipment.Equipment;
import xyz.woowa.dnf.character.domain.equipment.flag.Flag;

@Builder
@Getter
public class Character {
    private final EntityId id;
    private final Base base;
    private final Equipment equipment;
    private final Avatars avatars;
    private final Avatars buffAvatars;
    private final BuffEquipment buffEquipment;
    private final Creature creature;
    private final Creature buffCreature;
    private final Flag flag;


    public String name() {
        return base.characterName();
    }

    public String server() {
        return base.serverName();
    }

    public String guildName() {
        return base.guildName();
    }

    public String adventureName() {
        return base.adventureName();
    }

    public boolean equalsToBase(Base other) {
        return this.base.equals(other);
    }

    public boolean equalsToGuildName(String guildName) {
        return guildName().equalsIgnoreCase(guildName);
    }

    public boolean equalsToAdventureName(String adventureName) {
        return adventureName().equalsIgnoreCase(adventureName);
    }

}
