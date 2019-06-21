package de.hfu.pms.utils;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class NumberUtilsTest {

    @Test
    public void testParseNumberStringToBigDecimal() {
        assertNotNull(NumberUtils.parseToBigDecimal("0"));
        assertNotNull(NumberUtils.parseToBigDecimal("0,0"));
        assertNotNull(NumberUtils.parseToBigDecimal("12,15"));
        assertNotNull(NumberUtils.parseToBigDecimal("20.00"));
        assertNotNull(NumberUtils.parseToBigDecimal("-2.15"));

        assertNull(NumberUtils.parseToBigDecimal(""));
    }

}
