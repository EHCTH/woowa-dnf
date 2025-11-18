package xyz.woowa.dnf.character.application.query.dto.equipment.creature;


public record ArtifactDto(String color,
                          ProfileDto base,
                          String detailType,
                          String baseStatusHtml,
                          String detailStatusHtml,
                          String otherStatusHtml) {
}
