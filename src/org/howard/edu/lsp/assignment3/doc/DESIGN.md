# Assignment 3 Design Discussion

## Assignment 2 organization
Assignment 2 used one `ETLPipeline` class with static methods. `runPipeline()` handled
file access, row counts, and console reporting. `transform()` combined CSV validation,
name normalization, payroll calculations, classification, and output formatting.

## Assignment 3 design
Assignment 3 uses composition and five classes with distinct responsibilities:

- `Employee` encapsulates validated input in private final fields. Its immutable
  `BigDecimal` values retain full input precision for calculations.
- `EmployeeParser` validates the five CSV fields and normalizes names. It returns
  `Optional.empty()` for invalid rows, making the skip decision explicit.
- `PayrollCalculator` applies overtime, the IT bonus, rounding, pay levels, and
  employment status. It accepts an employee and returns a payroll result without
  knowing about files or CSV syntax.
- `PayrollRecord` holds the calculated result and owns the output header and CSV
  formatting. Display rounding does not change the employee's original values.
- `ETLPipeline` owns a parser and calculator, coordinates file processing, counts
  outcomes, and prints the original summary. Its `main()` remains the entry point.

The pipeline passes each parsed employee to the calculator, then writes the returned
record. This replaces one large transformation method with collaborating objects.
Inheritance is unnecessary because these responsibilities have no natural is-a
relationship. Encapsulation and composition provide the useful separation here.

## Why this improves the design
Parsing changes can be made in the parser, payroll rules in the calculator, and
output-format changes in the record. Calculations can be exercised without opening
files, and immutable employee data cannot be accidentally changed during processing.
The small coordinator makes the overall workflow easier to read and explain.

## Preserved behavior and verification
Input and output remain `data/employees.csv` and `data/transformed_employees.csv`.
The first input line is always treated as a header. Validation, row ordering,
case-sensitive IT matching, overtime before bonus, half-up rounding, classification
after gross-pay rounding, and console/error messages match Assignment 2. The simple
comma splitting is preserved; quoted CSV fields are not newly supported.

Both programs were compiled targeting Java 8 and run with OpenJDK 26. Their exit
statuses, stdout, stderr, and output CSV bytes matched in eight scenarios: the
provided dataset, rounding and classification boundaries, invalid rows, empty input,
header-only input, 1,000 deterministic generated rows, missing input, and an output
path occupied by a directory. The supplied dataset produces 14 rows read, 7
transformed, and 7 skipped.
