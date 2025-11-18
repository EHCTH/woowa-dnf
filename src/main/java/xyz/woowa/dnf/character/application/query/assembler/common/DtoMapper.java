package xyz.woowa.dnf.character.application.query.assembler.common;

public interface DtoMapper <T, R>{
    R toMap(T t);
}
