package org.howard.edu.lsp.assignment3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

/** Coordinates extraction, transformation, loading, and the original console summary. */
public final class ETLPipeline {
    private static final Path INPUT = Paths.get("data/employees.csv");
    private static final Path OUTPUT = Paths.get("data/transformed_employees.csv");
    private final EmployeeParser parser;
    private final PayrollCalculator calculator;

    public ETLPipeline() {
        parser = new EmployeeParser();
        calculator = new PayrollCalculator();
    }

    public static void main(String[] args) {
        try {
            new ETLPipeline().run();
        } catch (IOException e) {
            System.err.println("Unable to complete payroll ETL: " + e.getMessage());
            System.exit(1);
        }
    }

    public void run() throws IOException {
        int rowsRead = 0;
        int rowsTransformed = 0;
        int rowsSkipped = 0;
        try (BufferedReader reader = Files.newBufferedReader(INPUT, StandardCharsets.UTF_8);
             BufferedWriter writer = Files.newBufferedWriter(OUTPUT, StandardCharsets.UTF_8)) {
            writer.write(PayrollRecord.HEADER);
            writer.newLine();
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                rowsRead++;
                Optional<Employee> employee = parser.parse(line);
                if (!employee.isPresent()) {
                    rowsSkipped++;
                } else {
                    writer.write(calculator.calculate(employee.get()).toCsv());
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
}
