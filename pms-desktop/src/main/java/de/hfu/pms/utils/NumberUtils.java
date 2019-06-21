package de.hfu.pms.utils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class NumberUtils {

    /**
     * This util method parses the specified string to a BigDecimal object
     * using the default locale number format.
     * @param numberString the number string to parse
     * @return the BigDecimal object containing the specified number string, or null if a ParseException occurred
     */
    public static BigDecimal parseToBigDecimal(String numberString) {
        NumberFormat nf = NumberFormat.getInstance(Locale.getDefault());
        try {
            return new BigDecimal(nf.parse(numberString).toString());
        } catch (ParseException e) {
            return null;
        }
    }

}
