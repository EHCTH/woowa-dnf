package xyz.woowa.dnf.character.domain.equipment.common;

import java.util.*;
import java.util.stream.Collectors;

public record Enchant(List<Status> status) {
    private static final Set<String> STAT_NAMES = Set.of("힘", "지능", "체력", "정신력");
    private static final Set<String> ATTACK_NAMES = Set.of("물리 공격력", "마법 공격력", "독립 공격력");

    public List<Status> getStatus() {
        List<Status> result = new ArrayList<>();
        if (status == null || status.isEmpty()) {
            return result;
        }

        Map<String, String> map = getEnchantMap();

        collapseIfAllEqual(map, result, STAT_NAMES, "스탯");
        collapseIfAllEqual(map, result, ATTACK_NAMES, "공격력");

        status.stream()
                .filter(s -> map.containsKey(s.name()))
                .forEach(result::add);
        return List.copyOf(result);
    }

    private Map<String, String> getEnchantMap() {
        return status.stream()
                .collect(Collectors.toMap(
                        Status::name,
                        Status::value,
                        (a, b) -> a,
                        LinkedHashMap::new));
    }

    private void collapseIfAllEqual(Map<String, String> map,
                                           List<Status> result,
                                           Set<String> group,
                                           String mergedName) {
        Set<String> values = group.stream()
                .map(map::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        if (values.size() == 1) {
            group.forEach(map::remove);
            result.add(new Status(mergedName, values.iterator().next()));
        }
    }
}
