package org.howard.edu.lsp.midterm.inheritance;

/**
 * Holds shared weekly work information for employees with different pay rules.
 * @author Nyla Crespo
 */
public abstract class Employee {
    private final double hoursWorked;
    private final double hourlyRate;

    /**
     * Initializes the shared work information.
     * @param hoursWorked nonnegative hours worked this week
     * @param hourlyRate nonnegative hourly rate
     */
    public Employee(double hoursWorked, double hourlyRate) {
        this.hoursWorked = hoursWorked;
        this.hourlyRate = hourlyRate;
    }

    /**
     * Supplies weekly hours to subclasses.
     * @return hours worked this week
     */
    protected double getHoursWorked() { return hoursWorked; }

    /**
     * Supplies the hourly rate to subclasses.
     * @return the hourly rate
     */
    protected double getHourlyRate() { return hourlyRate; }

    /**
     * Calculates weekly pay using the employee's specific pay rule.
     * @return weekly pay
     */
    public abstract double calculatePay();
}
