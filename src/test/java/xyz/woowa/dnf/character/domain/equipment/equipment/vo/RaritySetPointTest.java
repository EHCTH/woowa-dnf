package xyz.woowa.dnf.character.domain.equipment.equipment.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class RaritySetPointTest {
    @DisplayName("computeSetPoint: 경계값/대표값 매핑 확인")
    @ParameterizedTest(name = "{index} => input={0} → expected={1}")
    @MethodSource("cases")
    void computeSetPoint_shouldReturnExpected(int input, RaritySetPoint expected) {
        assertEquals(expected, RaritySetPoint.computeSetPoint(input));
    }

    static Stream<Arguments> cases() {
        return Stream.of(
                Arguments.of(3000, RaritySetPoint.태초),
                Arguments.of(2550, RaritySetPoint.태초),
                Arguments.of(2549, RaritySetPoint.에픽_V),

                Arguments.of(2440, RaritySetPoint.에픽_V),
                Arguments.of(2439, RaritySetPoint.에픽_IV),
                Arguments.of(2355, RaritySetPoint.에픽_IV),
                Arguments.of(2354, RaritySetPoint.에픽_III),
                Arguments.of(2270, RaritySetPoint.에픽_III),
                Arguments.of(2269, RaritySetPoint.에픽_II),
                Arguments.of(2185, RaritySetPoint.에픽_II),
                Arguments.of(2184, RaritySetPoint.에픽_I),
                Arguments.of(2100, RaritySetPoint.에픽_I),
                Arguments.of(2099, RaritySetPoint.레전더리_V),

                Arguments.of(1990, RaritySetPoint.레전더리_V),
                Arguments.of(1989, RaritySetPoint.레전더리_IV),
                Arguments.of(1905, RaritySetPoint.레전더리_IV),
                Arguments.of(1904, RaritySetPoint.레전더리_III),
                Arguments.of(1820, RaritySetPoint.레전더리_III),
                Arguments.of(1819, RaritySetPoint.레전더리_II),
                Arguments.of(1735, RaritySetPoint.레전더리_II),
                Arguments.of(1734, RaritySetPoint.레전더리_I),
                Arguments.of(1650, RaritySetPoint.레전더리_I),
                Arguments.of(1649, RaritySetPoint.유니크_V),

                Arguments.of(1540, RaritySetPoint.유니크_V),
                Arguments.of(1539, RaritySetPoint.유니크_IV),
                Arguments.of(1455, RaritySetPoint.유니크_IV),
                Arguments.of(1454, RaritySetPoint.유니크_III),
                Arguments.of(1370, RaritySetPoint.유니크_III),
                Arguments.of(1369, RaritySetPoint.유니크_II),
                Arguments.of(1285, RaritySetPoint.유니크_II),
                Arguments.of(1284, RaritySetPoint.유니크_I),
                Arguments.of(1200, RaritySetPoint.유니크_I),

                Arguments.of(1199, RaritySetPoint.레어_V),
                Arguments.of(1070, RaritySetPoint.레어_V),
                Arguments.of(1069, RaritySetPoint.레어_IV),
                Arguments.of(990,  RaritySetPoint.레어_IV),
                Arguments.of(989,  RaritySetPoint.레어_III),
                Arguments.of(910,  RaritySetPoint.레어_III),
                Arguments.of(909,  RaritySetPoint.레어_II),
                Arguments.of(830,  RaritySetPoint.레어_II),
                Arguments.of(829,  RaritySetPoint.레어_I),
                Arguments.of(750,  RaritySetPoint.레어_I),


                Arguments.of(11,   RaritySetPoint.고유장비),
                Arguments.of(10,   RaritySetPoint.NONE),
                Arguments.of(0,    RaritySetPoint.NONE),
                Arguments.of(-100, RaritySetPoint.NONE)
        );
    }
}
