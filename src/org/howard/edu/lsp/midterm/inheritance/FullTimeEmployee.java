package org.howard.edu.lsp.midterm.inheritance;

/**
 * Receives straight pay through 40 hours and time-and-a-half beyond 40.
 * @author Nyla Crespo
 */
public class FullTimeEmployee extends Employee {
    /**
     * Creates this employee with weekly hours and an hourly rate.
     * @param hoursWorked nonnegative hours worked this week
     * @param hourlyRate nonnegative hourly rate
     */
    public FullTimeEmployee(double hoursWorked, double hourlyRate) {
        super(hoursWorked, hourlyRate);
    }

    /**
     * Calculates weekly pay. Receives straight pay through 40 hours and time-and-a-half beyond 40.
     * @return weekly pay
     */
    @Override
    public double calculatePay() {
        double regularHours = Math.min(getHoursWorked(), 40.0);
        double overtimeHours = Math.max(0.0, getHoursWorked() - 40.0);
        return regularHours * getHourlyRate()
                + overtimeHours * getHourlyRate() * 1.5;
    }
}
