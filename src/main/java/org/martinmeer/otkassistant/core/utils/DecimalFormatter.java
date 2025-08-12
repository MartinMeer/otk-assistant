package org.martinmeer.otkassistant.core.utils;

import org.martinmeer.otkassistant.ost22.web.InvalidScaleException;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DecimalFormatter {
    /**
     * Prepares a decimal value for SQL numrange queries.
     * Converts the provided string to {@link BigDecimal}. If the value has no fractional
     * part, the method sets scale to 1 (e.g., 10 becomes 10.0). If the scale exceeds 3,
     * an {@link InvalidScaleException} is thrown.
     *
     * @param dimension input decimal as a string
     * @return normalized {@link BigDecimal}: scale 1 for integers, otherwise original scale (max 3)
     * @throws InvalidScaleException when the fractional part contains more than 3 digits
     */
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
