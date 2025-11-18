package xyz.woowa.dnf.common.exception;

import lombok.Getter;

@Getter
public class DomainException extends RuntimeException {
    private final String code;
    private final Object[] args;

    public DomainException(String code, Object... args) {
        super(code);
        this.code = code;
        this.args = args;
    }
}
