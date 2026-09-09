package org.howard.edu.lsp.assignment2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;

/** Reads, transforms, and writes employee payroll using project-relative paths. */
public class ETLPipeline {
    private static final Path INPUT = Paths.get("data/employees.csv");
    private static final Path OUTPUT = Paths.get("data/transformed_employees.csv");
    private static final String HEADER =
            "EmployeeID,Name,Department,HoursWorked,HourlyRate,GrossPay,PayLevel,EmploymentStatus";
    private static final BigDecimal FORTY = new BigDecimal("40");

    public static void main(String[] args) {
        try {
            runPipeline();
        } catch (IOException e) {
            System.err.println("Unable to complete payroll ETL: " + e.getMessage());
            System.exit(1);
        }
    }

    private static void runPipeline() throws IOException {
        int rowsRead = 0;
        int rowsTransformed = 0;
        int rowsSkipped = 0;

        try (BufferedReader reader = Files.newBufferedReader(INPUT, StandardCharsets.UTF_8);
             BufferedWriter writer = Files.newBufferedWriter(OUTPUT, StandardCharsets.UTF_8)) {
            writer.write(HEADER);
            writer.newLine();
            reader.readLine(); // The first input line is the header.
            String line;
            while ((line = reader.readLine()) != null) {
                rowsRead++;
                String result = transform(line);
                if (result == null) {
                    rowsSkipped++;
                } else {
                    writer.write(result);
                    writer.newLine();
                    rowsTransformed++;
                }
            }
        }

        System.out.println("Rows read: " + rowsRead);
        System.out.println("Rows transformed: " + rowsTransformed);
        System.out.println("Rows skipped: " + rowsSkipped);
        System.out.println("Output file: data/transformed_employees.csv");
    }

    /** Returns a transformed CSV row, or null when the input row is invalid. */
    private static String transform(String line) {
        if (line.trim().isEmpty()) {
            return null;
        }
        // Preserve empty trailing fields so missing numeric values are validated.
        String[] fields = line.split(",", -1);
        if (fields.length != 5) {
            return null;
        }
        for (int i = 0; i < fields.length; i++) {
            fields[i] = fields[i].trim();
        }
        fields[1] = fields[1].toUpperCase(Locale.ROOT);

        int employeeId;
        BigDecimal hours;
        BigDecimal rate;
        try {
            employeeId = Integer.parseInt(fields[0]);
            hours = new BigDecimal(fields[3]);
            rate = new BigDecimal(fields[4]);
        } catch (NumberFormatException e) {
            return null;
        }
        if (hours.signum() < 0 || rate.signum() < 0) {
            return null;
        }

        // Keep full input precision until all pay calculations are complete.
        BigDecimal grossPay = hours.min(FORTY).multiply(rate);
        if (hours.compareTo(FORTY) > 0) {
            grossPay = grossPay.add(hours.subtract(FORTY)
                    .multiply(rate).multiply(new BigDecimal("1.5")));
        }
        if (fields[2].equals("IT")) {
            grossPay = grossPay.multiply(new BigDecimal("1.05"));
        }
        grossPay = grossPay.setScale(2, RoundingMode.HALF_UP);

        String payLevel;
        if (grossPay.compareTo(new BigDecimal("500")) < 0) {
            payLevel = "Low";
        } else if (grossPay.compareTo(new BigDecimal("1000")) < 0) {
            payLevel = "Standard";
        } else if (grossPay.compareTo(new BigDecimal("2000")) < 0) {
            payLevel = "High";
        } else {
            payLevel = "Executive";
        }
        String status = hours.compareTo(new BigDecimal("30")) < 0
                ? "Part-Time" : "Full-Time";
        return employeeId + "," + fields[1] + "," + fields[2] + ","
                + formatDecimal(hours) + "," + formatDecimal(rate) + ","
                + grossPay.toPlainString() + "," + payLevel + "," + status;
    }

    private static String formatDecimal(BigDecimal value) {
        return value.setScale(2, RoundingMode.HALF_UP).toPlainString();
    }
}
