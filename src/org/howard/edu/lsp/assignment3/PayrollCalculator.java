package org.howard.edu.lsp.assignment3;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** Owns payroll rules independently of CSV parsing and file handling. */
public final class PayrollCalculator {
    private static final BigDecimal FORTY = new BigDecimal("40");
    private static final BigDecimal OVERTIME = new BigDecimal("1.5");
    private static final BigDecimal IT_BONUS = new BigDecimal("1.05");
    private static final BigDecimal FULL_TIME_HOURS = new BigDecimal("30");
    private static final BigDecimal STANDARD = new BigDecimal("500");
    private static final BigDecimal HIGH = new BigDecimal("1000");
    private static final BigDecimal EXECUTIVE = new BigDecimal("2000");

    public PayrollRecord calculate(Employee employee) {
        BigDecimal hours = employee.getHours();
        BigDecimal rate = employee.getRate();
        BigDecimal gross = hours.min(FORTY).multiply(rate);
        if (hours.compareTo(FORTY) > 0) {
            gross = gross.add(hours.subtract(FORTY).multiply(rate).multiply(OVERTIME));
        }
        if (employee.getDepartment().equals("IT")) {
            gross = gross.multiply(IT_BONUS);
        }
        gross = gross.setScale(2, RoundingMode.HALF_UP);
        String level;
        if (gross.compareTo(STANDARD) < 0) {
            level = "Low";
        } else if (gross.compareTo(HIGH) < 0) {
            level = "Standard";
        } else if (gross.compareTo(EXECUTIVE) < 0) {
            level = "High";
        } else {
            level = "Executive";
        }
        String status = hours.compareTo(FULL_TIME_HOURS) < 0 ? "Part-Time" : "Full-Time";
        return new PayrollRecord(employee, gross, level, status);
    }
}
