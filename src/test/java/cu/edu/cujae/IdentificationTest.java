package cu.edu.cujae;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

class IdentificationTest {
    private Identification identification;

    @AfterEach
    void tearDown() {
        identification = null;
    }
    @Test
    void idLength12() {
        assertThrows(InvalidParameterException.class,
                () -> {
                    givenId("990916677632");
                });
    }
    @Test
    void idLength10() {
        assertThrows(InvalidParameterException.class,
                () -> {
                    givenId("9909166776");
                });
    }
    @Test
    void notNumberCharacter() {
        assertThrows(InvalidParameterException.class,
                () -> {
                    givenId("0109166b763");
                });
    }

    @Test
    void leapYear2004() {
        givenId("04091667763");
        assertTrue(identification.leapYear());
    }
    @Test
    void leapYear2003(){
        givenId("03091667763");
        assertFalse(identification.leapYear());
    }

    @Test
    void getYear2000() {
        givenId("00091667763");
        assertEquals(2000, identification.getYear());
    }
    @Test
    void getYear2020() {
        givenId("20091667763");
        assertEquals(2020, identification.getYear());
    }
    @Test
    void getYear1999MeaningIsNotBorn() {
        givenId("99091607763");
        assertEquals(1999, identification.getYear());
    }
    @Test
    void getYear2099MeaningIsNotBorn() {
        assertThrows(InvalidParameterException.class,
                () -> {
                    givenId("99091667763");
                });
    }

    @Test
    void centuryNum9() {
        givenId("01091697763");
        assertEquals(19, identification.century());
    }
    @Test
    void centuryNum0() {
        givenId("01091607763");
        assertEquals(20, identification.century());
    }
    @Test
    void centuryNum3() {
        givenId("01091637763");
        assertEquals(20, identification.century());
    }
    @Test
    void centuryNum5() {
        givenId("01091657763");
        assertEquals(20, identification.century());
    }
    @Test
    void centuryNum6() {
        givenId("01091667763");
        assertEquals(21, identification.century());
    }
    @Test
    void centuryNum7() {
        givenId("01091677763");
        assertEquals(21, identification.century());
    }
    @Test
    void centuryNum8() {
        givenId("01091687763");
        assertEquals(21, identification.century());
    }

    @Test
    void month1() {
        givenId("01011687763");
        assertEquals(1, identification.getMonth());
    }
    @Test
    void month0() {
        assertThrows(InvalidParameterException.class,
                () -> {
                    givenId("01001687763");
                });
    }
    @Test
    void month13() {
        assertThrows(InvalidParameterException.class,
                () -> {
                    givenId("01131687763");
                });
    }

    @Test
    void day3() {
        givenId("01090367763");
        assertEquals(3, identification.getDay());
    }
    @Test
    void day0() {
        assertThrows(InvalidParameterException.class,
                () -> {
                    givenId("01090087763");
                });
    }
    @Test
    void day29ForFebruaryNonLeapYear() {
        assertThrows(InvalidParameterException.class,
                () -> {
                    givenId("01022987763");
                });
    }
    @Test
    void day29ForFebruaryLeapYear() {
        givenId("04022987763");
        assertEquals(29, identification.getDay());
    }
    @Test
    void day31ForNovember() {
        assertThrows(InvalidParameterException.class,
                () -> {
                    givenId("01113187763");
                });
    }
    @Test
    void day31ForDecember() {
        givenId("04123187763");
        assertEquals(31, identification.getDay());
    }
    @Test
    void day31ForApril() {
        assertThrows(InvalidParameterException.class,
                () -> {
                    givenId("01043187763");
                });
    }
    @Test
    void day31ForJanuary() {
        givenId("04013187763");
        assertEquals(31, identification.getDay());
    }

    @Test
    void birthday() {
        givenId("01091667763");
        Calendar birthday = identification.birthday();
        testCalendar(birthday, 16, 9, 2001);
    }

    @Test
    void gender6() {
        givenId("01091667763");
        assertEquals(Identification.Gender.MALE, identification.gender());
    }
    @Test
    void gender5() {
        givenId("01091667753");
        assertEquals(Identification.Gender.FEMALE, identification.gender());
    }

    @Test
    void age() {
        givenId("01091667763");
        //21 is the age at the moment I'm testing this could change
        assertEquals(21, identification.age());
    }
    @Test
    void ageSameMonthAsActual() {
        givenId("01011667763");
        assertEquals(21, identification.age());
    }
    @Test
    void ageSameMonthAsActualGreater() {
        givenId("01011267763");
        assertEquals(22, identification.age());
    }

    @Test
    void testToString() {
        String id = "01091667763";
        givenId(id);
        assertEquals(id, identification.toString());
    }
    void givenId(String id){
        identification = new Identification(id);
    }
    void testCalendar(Calendar calendar, int day, int month, int year){
        assertEquals(day, calendar.get(Calendar.DAY_OF_MONTH));
        assertEquals(month, calendar.get(Calendar.MONTH) + 1);
        assertEquals(year, calendar.get(Calendar.YEAR));
    }
}