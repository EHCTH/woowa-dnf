package xyz.woowa.dnf.character.application.query.assembler.base;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.woowa.dnf.character.application.query.assembler.common.CommonValueMapper;
import xyz.woowa.dnf.character.application.query.port.outbound.base.ExternalBasePort;
import xyz.woowa.dnf.character.domain.base.*;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BaseMapper {
    private final CommonValueMapper mapper;

    public Base toDomain(ExternalBasePort.BasicDto row, ExternalBasePort.StatusRow statusRow) {
        Server server = Server.fromEnglish(row.serverId());
        Profile profile = new Profile(row.characterId(), server, new Name(row.characterName()), row.level(), row.jobGrowName(), row.fame());
        Social social = new Social(row.adventureName(), row.guildName());
        return Base.builder()
                .profile(profile)
                .social(social)
                .baseStatus(toStatus(statusRow))
                .build();
    }

    private BaseStatus toStatus(ExternalBasePort.StatusRow statusRow) {
        return statusRow.status().stream()
                .map(stat -> mapper.toNameStatus(stat.name(), stat.value()))
                .collect(Collectors.collectingAndThen(Collectors.toList(), BaseStatus::new));
    }

    public Base toDomain(ExternalBasePort.BasicDto row) {
        Server server = Server.fromEnglish(row.serverId());
        Profile profile = new Profile(row.characterId(), server, new Name(row.characterName()), row.level(), row.jobGrowName(), row.fame());
        Social social = new Social(row.adventureName(), row.guildName());
        return Base.builder()
                .profile(profile)
                .social(social)
                .build();
    }
}
