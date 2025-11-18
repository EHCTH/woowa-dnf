package xyz.woowa.dnf.character.application.query.dto.equipment.creature;

public record CreatureSkillDto(String skillName, String skillDescription, String overSkill, String overSkillDescription) {
    public boolean hasSkill() {
        return !skillName.isEmpty();
    }
    public boolean hasOverSkill() {
        return !overSkill.isEmpty();
    }
}
