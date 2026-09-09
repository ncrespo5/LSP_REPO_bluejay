# Assignment 2: Employee Payroll ETL

Uses plain Java to read `data/employees.csv`, validate and transform payroll rows,
and write `data/transformed_employees.csv`. Run commands from this project root.

Compile and run (JDK 8 or newer):

```sh
javac -d out src/org/howard/edu/lsp/assignment2/ETLPipeline.java
java -cp out org.howard.edu.lsp.assignment2.ETLPipeline
```

The included grading dataset produces 14 rows read, 7 transformed, and 7 skipped.
The program uses BigDecimal to preserve decimal precision, applies overtime before
the IT bonus, and determines pay level after rounding gross pay half-up.

Submit the source and data files through your GitHub repository. Do not upload
compiled classes, the local `out` folder, or IDE files.

AI disclosure: OpenAI Codex generated this implementation and README and verified
its output against the assignment's grading dataset. No Internet sources were used.
