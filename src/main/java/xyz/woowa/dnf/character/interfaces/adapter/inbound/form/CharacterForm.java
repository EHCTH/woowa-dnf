package xyz.woowa.dnf.character.interfaces.adapter.inbound.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CharacterForm(String server, @NotBlank @Size(max = 16) String name) {
}
