package xyz.woowa.dnf.character.application.query.assembler.avatar;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import xyz.woowa.dnf.character.application.query.assembler.HtmlMapper;
import xyz.woowa.dnf.character.application.query.assembler.common.CommonValueDtoMapper;
import xyz.woowa.dnf.character.application.query.assembler.common.EmblemColorMapper;
import xyz.woowa.dnf.character.application.query.dto.equipment.avatar.AvatarDto;
import xyz.woowa.dnf.character.application.query.dto.equipment.avatar.EmblemDto;
import xyz.woowa.dnf.character.application.query.dto.equipment.avatar.ProfileDto;
import xyz.woowa.dnf.character.application.query.dto.equipment.equipment.ItemDetailDto;
import xyz.woowa.dnf.character.domain.equipment.avatar.Avatar;
import xyz.woowa.dnf.character.domain.equipment.avatar.vo.*;
import xyz.woowa.dnf.character.domain.equipment.common.ItemName;
import xyz.woowa.dnf.character.domain.equipment.common.Rarity;
import xyz.woowa.dnf.character.domain.equipment.common.Status;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AvatarDtoMapperTest {
    private final CommonValueDtoMapper commonValueDtoMapper = new CommonValueDtoMapper();
    private final HtmlMapper htmlMapper = new HtmlMapper();
    private final EmblemColorMapper emblemColorMapper = new EmblemColorMapper();

    private final AvatarDtoMapper avatarDtoMapper = new AvatarDtoMapper(commonValueDtoMapper, emblemColorMapper, htmlMapper);
    @Test
    @DisplayName("DTO 변환 테스트")
    void DTO_변환_테스트() {
        // give
        Avatar avatar = createAvatar();

        // when
        AvatarDto dto = avatarDtoMapper.toMap(avatar);

        // then
        ProfileDto baseDto = dto.getBase();
        Detail detail = avatar.getDetail();

        assertThat(baseDto.slot()).isEqualTo("얼굴 아바타");
        assertThat(baseDto.rarity()).isEqualTo(Rarity.언커먼.name());
        assertThat(baseDto.itemName().id()).isEqualTo("FACE_ID");
        assertThat(baseDto.itemName().name()).isEqualTo("얼굴 아바타");
        assertThat(baseDto.fame()).isEqualTo(detail.fame());

        assertThat(dto.getClone().id()).isEqualTo("FACE_ID");
        assertThat(dto.getClone().name()).isEqualTo("얼굴 스킨 아바타");

        assertThat(dto.getOptionAbility()).isEqualTo("공격 속도 6.0% 증가");

        List<EmblemDto> emblems = dto.getEmblems();
        assertThat(emblems).hasSize(1);

        EmblemDto emblemDto = emblems.getFirst();
        assertThat(emblemDto.color()).isEqualTo("노란빛");
        assertThat(emblemDto.rarity()).isEqualTo("레어");
        assertThat(emblemDto.base().id()).isEqualTo("EMBLEM_ID");
        assertThat(emblemDto.base().name()).isEqualTo("찬란한 노란빛 엠블렘");

        ItemDetailDto detailDto = dto.getDetail();
        assertThat(detailDto.fame()).isEqualTo("10");
        assertThat(detailDto.itemExplainHtml()).isEqualTo("설명");
        assertThat(detailDto.baseStatusHtml()).isEqualTo("힘 10");
    }
    private Avatar createAvatar() {
        return Avatar.builder()
                .base(new ItemProfile(AvatarSlot.FACE, new ItemName("FACE_ID", "얼굴 아바타"), Rarity.언커먼))
                .clone(new ItemName("FACE_ID", "얼굴 스킨 아바타"))
                .emblems(new Emblems(List.of(new Emblem(EmblemSlot.YELLOW,new ItemName("EMBLEM_ID", "찬란한 노란빛 엠블렘"),Rarity.레어 ))))
                .optionAbility("공격 속도 6.0% 증가")
                .detail(new Detail("10", "설명", List.of(new Status("힘", "10"))))
                .build();
    }
}
