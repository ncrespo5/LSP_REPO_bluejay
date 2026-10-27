# Evolving requirements and design rationale

My proposed design already gives RegistrationService a RegistrationRepository and a ConfirmationSender through their interfaces. I would retain those interfaces and their save and send responsibilities. I would add the following two CRC cards to the Part 2 design.

Class: DatabaseRegistrationRepository
Responsibilities: Implement RegistrationRepository; store registration records in the configured database using parameterized statements and a transaction; commit on success, roll back on failure, close resources, and report storage errors through the repository contract.
Collaborators: RegistrationRepository interface, Registration.

Class: TextMessageConfirmationSender
Responsibilities: Implement ConfirmationSender; format and send a text confirmation to the registered student's phone number; validate that a usable phone number exists and report delivery failure.
Collaborators: ConfirmationSender interface, Registration, Student, Course.

I would update RegistrationApplication's card to include reading deployment configuration and choosing either FileRegistrationRepository or DatabaseRegistrationRepository, then choosing EmailConfirmationSender or TextMessageConfirmationSender. Its collaborators would expand to include the two new classes. Student already owns an optional phone number in my Part 2 design; when text delivery is selected, the application must obtain that number before registration is processed. The selected objects would be passed to RegistrationService when the application starts. RegistrationService would continue to call the same interface operations without checking environment names or delivery types. The existing file and email cards would remain valid.

I would keep record persistence in the repository rather than putting it in Registration. A registration knows the student, course, and assessed amount, but a deployment's choice of file or database is not part of that domain information. Keeping this responsibility behind RegistrationRepository means a database change does not alter tuition calculation or require a Registration to open a database connection. I would also keep TuitionCalculator responsible for pricing rather than allowing RegistrationService to grow into another class that performs every task.

Both storage implementations must report a failed save rather than swallowing it. RegistrationService would request confirmation only after a successful save. If notification fails afterward, it would return the saved registration with a delivery failure status and log that outcome, so the caller does not incorrectly repeat the registration. This preserves the same workflow for either storage option and either confirmation channel.
