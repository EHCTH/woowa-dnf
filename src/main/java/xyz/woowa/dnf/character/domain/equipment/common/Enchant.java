package xyz.woowa.dnf.character.domain.equipment.common;

import lombok.RequiredArgsConstructor;

import java.util.*;

public record Enchant(List<Status> status) {
    @RequiredArgsConstructor
    enum StatusType {
        BASE(List.of("힘", "지능", "체력", "정신력")) {
            @Override
            public Status convert(Status status) {
                return new Status("스탯", status.value());
            }
        },
        ATTACK(List.of("물리 공격력", "마법 공격력", "독립 공격력")) {
            @Override
            public Status convert(Status status) {
                return new Status("공격력", status.value());
            }
        },
        OTHER(Collections.emptyList());

        private final List<String> names;

        public Status convert(Status status) {
            return status;
        }

        public boolean contains(Status status) {
            return names.contains(status.name());
        }

        public static List<Status> computeStatus(List<Status> status) {
            return status.stream()
                    .map(StatusType::convertStatus)
                    .distinct()
                    .toList();
        }

        private static Status convertStatus(Status status) {
            return findType(status).convert(status);
        }

        private static StatusType findType(Status status) {
            return Arrays.stream(values())
                    .filter(type -> type.contains(status))
                    .findFirst()
                    .orElse(OTHER);
        }

    }

    public List<Status> getStatus() {
        if (status == null || status.isEmpty()) {
            return Collections.emptyList();
        }
        return StatusType.computeStatus(status);
    }
}

