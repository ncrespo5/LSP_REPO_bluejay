package org.howard.edu.lsp.assignment3;

import java.math.BigDecimal;

/** Immutable validated input data; numeric values retain their original precision. */
public final class Employee {
    private final int id;
    private final String name;
    private final String department;
    private final BigDecimal hours;
    private final BigDecimal rate;

    public Employee(int id, String name, String department, BigDecimal hours, BigDecimal rate) {
        if (name == null || department == null || hours == null || rate == null
                || hours.signum() < 0 || rate.signum() < 0) {
            throw new IllegalArgumentException("Invalid employee data");
        }
        this.id = id;
        this.name = name;
        this.department = department;
        this.hours = hours;
        this.rate = rate;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public BigDecimal getHours() { return hours; }
    public BigDecimal getRate() { return rate; }
}
