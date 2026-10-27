package org.howard.edu.lsp.midterm.inheritance;

/**
 * Receives straight hourly pay for every hour worked.
 * @author Nyla Crespo
 */
public class PartTimeEmployee extends Employee {
    /**
     * Creates this employee with weekly hours and an hourly rate.
     * @param hoursWorked nonnegative hours worked this week
     * @param hourlyRate nonnegative hourly rate
     */
    public PartTimeEmployee(double hoursWorked, double hourlyRate) {
        super(hoursWorked, hourlyRate);
    }

    /**
     * Calculates weekly pay. Receives straight hourly pay for every hour worked.
     * @return weekly pay
     */
    @Override
    public double calculatePay() {
        return getHoursWorked() * getHourlyRate();
    }
}
