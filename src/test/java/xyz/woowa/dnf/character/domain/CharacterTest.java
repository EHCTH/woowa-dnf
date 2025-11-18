package xyz.woowa.dnf.character.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import xyz.woowa.dnf.character.domain.base.Base;
import xyz.woowa.dnf.character.domain.base.EntityId;
import xyz.woowa.dnf.character.domain.base.Server;
import xyz.woowa.dnf.character.domain.equipment.avatar.Avatars;
import xyz.woowa.dnf.character.domain.equipment.buff.equipment.BuffEquipment;
import xyz.woowa.dnf.character.domain.equipment.creature.Creature;
import xyz.woowa.dnf.character.domain.equipment.equipment.Equipment;
import xyz.woowa.dnf.character.domain.equipment.flag.Flag;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CharacterTest {

    @Test
    @DisplayName("캐릭터 비교 테스트")
    void 캐릭터_비교_테스트() {
        // give
        EntityId id = new EntityId("1234", Server.CAIN);
        Base base = mock(Base.class);
        Equipment equipment = mock(Equipment.class);
        Avatars avatars = mock(Avatars.class);
        Avatars buffAvatars = mock(Avatars.class);
        BuffEquipment buffEquipment = mock(BuffEquipment.class);
        Creature creature = mock(Creature.class);
        Creature buffCreature = mock(Creature.class);
        Flag flag = mock(Flag.class);

        // when
        when(base.characterName()).thenReturn("테스트캐릭터");
        when(base.serverName()).thenReturn("카인");
        when(base.guildName()).thenReturn("길드A");
        when(base.adventureName()).thenReturn("모험단A");

        Character character = Character.builder()
                .id(id)
                .base(base)
                .equipment(equipment)
                .avatars(avatars)
                .buffAvatars(buffAvatars)
                .buffEquipment(buffEquipment)
                .creature(creature)
                .buffCreature(buffCreature)
                .flag(flag)
                .build();

        // then
        assertThat(character.getId()).isEqualTo(id);
        assertThat(character.getBase()).isEqualTo(base);
        assertThat(character.getEquipment()).isEqualTo(equipment);
        assertThat(character.getAvatars()).isEqualTo(avatars);
        assertThat(character.getBuffAvatars()).isEqualTo(buffAvatars);
        assertThat(character.getBuffEquipment()).isEqualTo(buffEquipment);
        assertThat(character.getCreature()).isEqualTo(creature);
        assertThat(character.getBuffCreature()).isEqualTo(buffCreature);
        assertThat(character.getFlag()).isEqualTo(flag);

        assertThat(character.name()).isEqualTo("테스트캐릭터");
        assertThat(character.server()).isEqualTo("카인");
        assertThat(character.guildName()).isEqualTo("길드A");
        assertThat(character.adventureName()).isEqualTo("모험단A");
    }

    @Test
    @DisplayName("Base 비교 테스트")
    void Base_비교_테스트() {
        // give
        Base base = mock(Base.class);
        Base otherBase = mock(Base.class);

        // when
        Character character = Character.builder()
                .id(new EntityId("1234", Server.CAIN))
                .base(base)
                .build();

        // then
        assertThat(character.equalsToBase(base)).isTrue();
        assertThat(character.equalsToBase(otherBase)).isFalse();
    }

    @Test
    @DisplayName("길드명 비교 테스트")
    void 길드명_비교_테스트() {
        // give
        Base base = mock(Base.class);
        when(base.guildName()).thenReturn("길드A");

        // when
        Character character = Character.builder()
                .id(new EntityId("1234", Server.CAIN))
                .base(base)
                .build();

        // then
        assertThat(character.equalsToGuildName("길드A")).isTrue();
        assertThat(character.equalsToGuildName("길드a")).isTrue();
        assertThat(character.equalsToGuildName("OTHER")).isFalse();
    }

    @Test
    @DisplayName("모험단명 비교 테스트")
    void 모험단명_비교_테스트() {
        // give
        Base base = mock(Base.class);
        when(base.adventureName()).thenReturn("모험단A");

        // when
        Character character = Character.builder()
                .id(new EntityId("1234", Server.CAIN))
                .base(base)
                .build();

        // then
        assertThat(character.equalsToAdventureName("모험단A")).isTrue();
        assertThat(character.equalsToAdventureName("모험단a")).isTrue();
        assertThat(character.equalsToAdventureName("OTHER")).isFalse();
    }
}
