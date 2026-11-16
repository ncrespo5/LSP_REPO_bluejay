package org.howard.edu.lsp.assignment3;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.Optional;

/** Parses the same simple, five-column CSV format accepted by Assignment 2. */
public final class EmployeeParser {
    /** Empty means the row is invalid and must be skipped. */
    public Optional<Employee> parse(String line) {
        if (line.trim().isEmpty()) {
            return Optional.empty();
        }
        String[] fields = line.split(",", -1);
        if (fields.length != 5) {
            return Optional.empty();
        }
        for (int i = 0; i < fields.length; i++) {
            fields[i] = fields[i].trim();
        }
        try {
            int id = Integer.parseInt(fields[0]);
            BigDecimal hours = new BigDecimal(fields[3]);
            BigDecimal rate = new BigDecimal(fields[4]);
            if (hours.signum() < 0 || rate.signum() < 0) {
                return Optional.empty();
            }
            return Optional.of(new Employee(id, fields[1].toUpperCase(Locale.ROOT),
                    fields[2], hours, rate));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
}
