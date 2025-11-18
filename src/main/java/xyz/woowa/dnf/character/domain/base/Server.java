package xyz.woowa.dnf.character.domain.base;

import lombok.Getter;
import xyz.woowa.dnf.common.exception.DomainException;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
public enum Server {
    ALL("전체", "all"),
    CAIN("카인", "cain"),
    DIREGIE("디레지에", "diregie"),
    SIROCO("시로코", "siroco"),
    PREY("프레이", "prey"),
    CASILLAS("카시야스", "casillas"),
    HILDER("힐더", "hilder"),
    ANTON("안톤", "anton"),
    BAKAL("바칼", "bakal"),
    GUILD("길드", "guild"),
    ADVENTURE("모험단", "adventure");

    private final String korean;
    private final String english;
    private static final Map<String, Server> BY_ENGLISH =
            Arrays.stream(values())
                    .collect(Collectors.toMap(
                            server -> server.english.toLowerCase(Locale.ROOT),
                            Function.identity()));

    Server(String korean, String english) {
        this.korean = korean;
        this.english = english;
    }

    public static Server fromEnglish(String english) {
        if (english == null) {
            throw new DomainException("server.required");
        } ;
        Server server = BY_ENGLISH.get(english.toLowerCase(Locale.ROOT));
        if (server == null) {
            throw new DomainException("server.not-found", english);
        }
        return server;
    }
}
