package xyz.woowa.dnf.character.domain.base;

import xyz.woowa.dnf.common.exception.DomainException;

public record Name(String value) {
    public Name {
        if (value == null || value.isBlank())
            throw new DomainException("name.required");
        value = value.trim();
        if (value.length() > 12)
            throw new DomainException("name.too-long", value.length());
    }
}
