package xyz.woowa.dnf.character.domain.equipment.avatar;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class Avatars {
    private final List<Avatar> avatars;
}
