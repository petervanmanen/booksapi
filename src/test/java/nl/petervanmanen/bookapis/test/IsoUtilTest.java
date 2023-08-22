package nl.petervanmanen.bookapis.test;

import nl.petervanmanen.bookapis.util.IsoUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IsoUtilTest {

    @Test
    void validIso() {
        assertEquals(true, IsoUtil.validateValidIsoLanguage("nl"));
    }

    @Test
    void inValidIso() {
        assertEquals(false, IsoUtil.validateValidIsoLanguage("df"));
    }
}
