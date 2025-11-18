package xyz.woowa.dnf.character.domain.equipment.equipment.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import xyz.woowa.dnf.character.domain.equipment.common.Rarity;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum RaritySetPoint {

    태초("태초",2_550, Rarity.태초),
    
    에픽_V("에픽V",2_440, Rarity.에픽),
    에픽_IV("에픽IV",2_355, Rarity.에픽),
    에픽_III("에픽III",2_270, Rarity.에픽),
    에픽_II("에픽II",2_185, Rarity.에픽),
    에픽_I("에픽I",2_100, Rarity.에픽),

    레전더리_V("레전더리V",1_990, Rarity.레전더리),
    레전더리_IV("레전더리IV",1_905, Rarity.레전더리),
    레전더리_III("레전더리III",1_820, Rarity.레전더리),
    레전더리_II("레전더리II",1_735, Rarity.레전더리),
    레전더리_I("레전더리I",1_650, Rarity.레전더리),

    유니크_V("유니크V",1_540, Rarity.유니크),
    유니크_IV("유니크IV",1_455, Rarity.유니크),
    유니크_III("유니크III",1_370, Rarity.유니크),
    유니크_II("유니크II",1_285, Rarity.유니크),
    유니크_I("유니크I",1_200, Rarity.유니크),

    레어_V("레어V", 1070 ,Rarity.레어),
    레어_IV("레어IV", 990 ,Rarity.레어),
    레어_III("레어III", 910 ,Rarity.레어),
    레어_II("레어II", 830 ,Rarity.레어),
    레어_I("레어I", 750 ,Rarity.레어),

    고유장비("고유장비",11, Rarity.언노운),
    NONE("없음",0, Rarity.언노운);

    private final String display;
    private final Integer setPoint;
    private final Rarity rarity;

    public static RaritySetPoint computeSetPoint(Integer setPoint) {
        return Arrays.stream(values())
                .filter(raritySetPoint -> raritySetPoint.setPoint <= setPoint)
                .findFirst()
                .orElse(NONE);
    }
    public String getRarityName() {
        return rarity.name();
    }

}
