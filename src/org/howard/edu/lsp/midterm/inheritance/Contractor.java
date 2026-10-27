package org.howard.edu.lsp.midterm.inheritance;

/**
 * Receives hourly pay with a 15 percent premium on every hour.
 * @author Nyla Crespo
 */
public class Contractor extends Employee {
    /**
     * Creates this employee with weekly hours and an hourly rate.
     * @param hoursWorked nonnegative hours worked this week
     * @param hourlyRate nonnegative hourly rate
     */
    public Contractor(double hoursWorked, double hourlyRate) {
        super(hoursWorked, hourlyRate);
    }

    /**
     * Calculates weekly pay. Receives hourly pay with a 15 percent premium on every hour.
     * @return weekly pay
     */
    @Override
    public double calculatePay() {
        return getHoursWorked() * getHourlyRate() * 1.15;
    }
}
