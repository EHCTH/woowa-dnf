package xyz.woowa.dnf.character.interfaces.adapter.inbound.form;

import jakarta.validation.constraints.NotBlank;

public record CharacterForm(@NotBlank (message = "{server.required}") String server,
                            @NotBlank (message = "{name.required}")   String name) {
}
