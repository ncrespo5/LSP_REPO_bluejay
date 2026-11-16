package org.howard.edu.lsp.assignment3;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** Immutable calculated payroll result and its output CSV representation. */
public final class PayrollRecord {
    public static final String HEADER =
            "EmployeeID,Name,Department,HoursWorked,HourlyRate,GrossPay,PayLevel,EmploymentStatus";
    private final Employee employee;
    private final BigDecimal grossPay;
    private final String payLevel;
    private final String employmentStatus;

    public PayrollRecord(Employee employee, BigDecimal grossPay, String payLevel,
                         String employmentStatus) {
        this.employee = employee;
        this.grossPay = grossPay;
        this.payLevel = payLevel;
        this.employmentStatus = employmentStatus;
    }

    public String toCsv() {
        return employee.getId() + "," + employee.getName() + "," + employee.getDepartment()
                + "," + formatDecimal(employee.getHours()) + "," + formatDecimal(employee.getRate())
                + "," + grossPay.toPlainString() + "," + payLevel + "," + employmentStatus;
    }

    private String formatDecimal(BigDecimal value) {
        return value.setScale(2, RoundingMode.HALF_UP).toPlainString();
    }
}
