package xyz.woowa.dnf.character.domain.equipment.common;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class EnchantTest {
    @Test
    @DisplayName("status가 비어있으면 빈 리스트 반환")
    void status가_비어있으면_빈_리스트_반환() {
        // give
        Enchant enchant = new Enchant(List.of());

        // when
        List<Status> result = enchant.getStatus();

        // then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("힘/지능/체력/정신력이 모두 같은 값이면 스탯 하나로 합친다")
    void merge_stat_group_when_all_equal() {
        Enchant enchant = enchant(
                s("힘", "10"),
                s("지능", "10"),
                s("체력", "10"),
                s("정신력", "10")
        );

        List<Status> result = enchant.getStatus();

        assertThat(result).hasSize(1)
                .first()
                .satisfies(stat -> {
                    assertThat(stat.name()).isEqualTo("스탯");
                    assertThat(stat.value()).isEqualTo("10");
                });
    }

    @Test
    @DisplayName("물공/마공/독공이 모두 같은 값이면 공격력 하나로 합친다")
    void merge_attack_group_when_all_equal() {
        Enchant enchant = enchant(
                s("물리 공격력", "30"),
                s("마법 공격력", "30"),
                s("독립 공격력", "30")
        );

        List<Status> result = enchant.getStatus();

        assertThat(result).hasSize(1)
                .first()
                .satisfies(stat -> {
                    assertThat(stat.name()).isEqualTo("공격력");
                    assertThat(stat.value()).isEqualTo("30");
                });
    }

    @Test
    @DisplayName("스탯과 공격력 모두 합치고 나머지 스탯은 기존 순서를 유지한다")
    void merge_stat_and_attack_and_keep_others_order() {
        Enchant enchant = enchant(
                s("모든 속성 강화", "15"),
                s("힘", "10"),
                s("지능", "10"),
                s("체력", "10"),
                s("정신력", "10"),
                s("물리 공격력", "30"),
                s("마법 공격력", "30"),
                s("독립 공격력", "30"),
                s("크리티컬 히트", "5")
        );

        List<Status> result = enchant.getStatus();

        assertThat(result)
                .extracting(Status::name)
                .containsExactly("스탯", "공격력", "모든 속성 강화", "크리티컬 히트");
    }

    private Status s(String name, String value) {
        return new Status(name, value);
    }

    private Enchant enchant(Status... args) {
        return Arrays.stream(args)
                .collect(Collectors.collectingAndThen(Collectors.toList(), Enchant::new));
    }

}
