package org.martinmeer.otkassistant.core.utils;

import org.martinmeer.otkassistant.ost22.web.InvalidScaleException;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DecimalFormatter {
/**This utility method prepares a number for SQL queries when searching for the numrange type is required.
 * The method converts String into BigDecimal.
 * If the fractional part of number is absent, it adds a scale of 1 (10 -> 10.0),
 * otherwise it returns BigDecimal in its original scale (constraint: scale of 3).*/
    public static BigDecimal scaleDecimal(String dimension) {
        BigDecimal bigDecimal = new BigDecimal(dimension);
        boolean isInteger = bigDecimal.compareTo(bigDecimal.setScale(0, RoundingMode.DOWN)) == 0;
        if (isInteger) {
            return bigDecimal.setScale(1, RoundingMode.UNNECESSARY);
        }
        if (bigDecimal.scale() > 3) {
            throw new InvalidScaleException(
                    "Число должно содержать не более 3 знаков после точки. Введено: "
                            + dimension);
        }
        return bigDecimal;
    }
}
