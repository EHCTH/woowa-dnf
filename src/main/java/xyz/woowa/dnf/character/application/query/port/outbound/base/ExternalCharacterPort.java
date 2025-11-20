package xyz.woowa.dnf.character.application.query.port.outbound.base;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import xyz.woowa.dnf.character.domain.base.Name;
import xyz.woowa.dnf.character.domain.base.Server;

import java.util.Arrays;
import java.util.List;

public interface ExternalCharacterPort {

    @RequiredArgsConstructor
    @Getter
    enum WordType {
        MATCH(1, "match"),
        FULL(2, "full");
        private final int length;
        private final String code;


        public static WordType findByNameLength(String name) {
            return Arrays.stream(values())
                    .filter(wordType -> wordType.length == name.length())
                    .findFirst()
                    .orElse(FULL);
        }
    }
    List<Row> search(Server server, Name name);

    record Row(String serverId, String characterId) {
    }
    record SearchResponse(List<Row> rows) {
    }

}


