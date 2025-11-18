package xyz.woowa.dnf.character.application.query.assembler.equipment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import xyz.woowa.dnf.character.application.query.assembler.HtmlMapper;
import xyz.woowa.dnf.character.application.query.assembler.common.CommonValueDtoMapper;

import static org.junit.jupiter.api.Assertions.*;

class EquipmentDtoMapperTest {
    private final CommonValueDtoMapper commonValueDtoMapper = new CommonValueDtoMapper();
    private final HtmlMapper htmlMapper = new HtmlMapper();
    private final EquipmentDtoMapper equipmentDtoMapper = new EquipmentDtoMapper(commonValueDtoMapper, htmlMapper);

    @Test
    @DisplayName("DTO 변환 테스트")
    void DTO_변환_테스트() {

    }


//    private Equip



}
