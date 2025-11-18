package xyz.woowa.dnf.character.application.query.port.outbound.base;

import xyz.woowa.dnf.character.domain.base.Name;
import xyz.woowa.dnf.character.domain.base.Server;

import java.util.List;

public interface ExternalCharacterPort {
    enum WordType {
        full, match
    }
    List<Row> search(Server server, Name name);

    record Row(String serverId, String characterId) {
    }
    record SearchResponse(List<Row> rows) {
    }
}


