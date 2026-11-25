package mn.edu.must.sqat;

// ЛАБ 11 — B2: B1 дээр ЗААГ УТГЫН тестүүд нэмсэн эцсийн хувилбар.
// Зорилго: changed conditional boundary, removed conditional,
// replaced return value, switch-ийн мутантуудыг бүгдийг нь алах.

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class ScholarshipCalculatorTest {

    private static final double EPS = 1e-9;

    ScholarshipCalculator calc;

    @BeforeEach
    void setUp() { calc = new ScholarshipCalculator(); }

    // =========================== letterGrade ===========================

    // B1: яг утгын шалгалт
    @Test
    void letterGrade_returnsExactGradeForAllRanges() {
        assertEquals("A", calc.letterGrade(95));
        assertEquals("B", calc.letterGrade(85));
        assertEquals("C", calc.letterGrade(75));
        assertEquals("D", calc.letterGrade(65));
        assertEquals("F", calc.letterGrade(30));
    }

    // B2: зааг утгууд (>= → > мутантыг алах)
    @ParameterizedTest(name = "letterGrade({0}) = {1}")
    @CsvSource({
            "100,   A",
            "90,    A",
            "89.99, B",
            "80,    B",
            "79.99, C",
            "70,    C",
            "69.99, D",
            "60,    D",
            "59.99, F",
            "0,     F"
    })
    void letterGrade_boundaries(double score, String expected) {
        assertEquals(expected, calc.letterGrade(score));
    }

    @Test
    void letterGrade_invalidInputs_throw() {
        assertThrows(IllegalArgumentException.class, () -> calc.letterGrade(-1));
        assertThrows(IllegalArgumentException.class, () -> calc.letterGrade(101));
        assertThrows(IllegalArgumentException.class, () -> calc.letterGrade(-0.01));
        assertThrows(IllegalArgumentException.class, () -> calc.letterGrade(100.01));
    }

    // =============================== gpa ===============================

    // B1: яг утгын шалгалт
    @Test
    void gpa_returnsExactAverage() {
        assertEquals(3.5, calc.gpa(new double[]{95, 85}), EPS); // (4+3)/2
        assertEquals(1.5, calc.gpa(new double[]{75, 65}), EPS); // (2+1)/2
        assertEquals(0.0, calc.gpa(new double[]{40}), EPS);     // F = 0
    }

    // B2: switch-ийн үсгэн дүн бүрийг дангаар нь
    @ParameterizedTest(name = "gpa([{0}]) = {1}")
    @CsvSource({
            "95, 4.0",
            "85, 3.0",
            "75, 2.0",
            "65, 1.0",
            "30, 0.0"
    })
    void gpa_singleGrade_eachLetter(double score, double expected) {
        assertEquals(expected, calc.gpa(new double[]{score}), EPS);
    }

    @Test
    void gpa_allLetters_mixedAverage() {
        // (4 + 3 + 2 + 1 + 0) / 5 = 2.0
        assertEquals(2.0, calc.gpa(new double[]{95, 85, 75, 65, 30}), EPS);
    }

    @Test
    void gpa_boundaryScores() {
        // (4 + 3 + 2 + 1 + 0) / 5 — заагийн оноонуудаар
        assertEquals(2.0, calc.gpa(new double[]{90, 80, 70, 60, 59.99}), EPS);
        // (4 + 4 + 4) / 3 — урт > 1 үед хуваалт шалгагдана
        assertEquals(4.0, calc.gpa(new double[]{100, 90, 95}), EPS);
    }

    @Test
    void gpa_invalidInputs_throw() {
        assertThrows(IllegalArgumentException.class, () -> calc.gpa(null));
        assertThrows(IllegalArgumentException.class, () -> calc.gpa(new double[]{}));
        assertThrows(IllegalArgumentException.class, () -> calc.gpa(new double[]{95, 101}));
    }

    // =========================== scholarship ===========================

    // B1: яг утгын шалгалт
    @Test
    void scholarship_returnsExactPercent() {
        assertEquals(100, calc.scholarship(3.9, 16, false));
        assertEquals(70, calc.scholarship(3.5, 13, false));
        assertEquals(50, calc.scholarship(3.1, 5, false));
        assertEquals(0, calc.scholarship(2.0, 1, false));
        assertEquals(0, calc.scholarship(4.0, 20, true));
    }

    // B2: дүрэм бүрийн заагийн дотор/гадна талууд
    @ParameterizedTest(name = "scholarship({0}, {1}, false) = {2}")
    @CsvSource({
            // 3.8 / 15 заагууд
            "3.8,  15, 100",
            "3.79, 15, 70",
            "3.8,  14, 70",
            // 3.4 / 12 заагууд
            "3.4,  12, 70",
            "3.39, 12, 50",
            "3.4,  11, 50",
            // 3.0 зааг
            "3.0,  0,  50",
            "2.99, 20, 0",
            // оролтын хязгаар: gpa 0 ба 4, credits 0
            "0.0,  0,  0",
            "4.0,  15, 100",
            "4.0,  0,  50"
    })
    void scholarship_boundaries(double gpa, int credits, int expected) {
        assertEquals(expected, calc.scholarship(gpa, credits, false));
    }

    @Test
    void scholarship_violation_alwaysZero() {
        assertEquals(0, calc.scholarship(4.0, 20, true));
        assertEquals(0, calc.scholarship(3.8, 15, true));
        assertEquals(0, calc.scholarship(3.0, 0, true));
    }

    @Test
    void scholarship_invalidInputs_throw() {
        assertThrows(IllegalArgumentException.class, () -> calc.scholarship(4.1, 10, false));
        assertThrows(IllegalArgumentException.class, () -> calc.scholarship(3.0, -1, false));
        assertThrows(IllegalArgumentException.class, () -> calc.scholarship(-0.01, 10, false));
        assertThrows(IllegalArgumentException.class, () -> calc.scholarship(4.01, 10, false));
    }
}