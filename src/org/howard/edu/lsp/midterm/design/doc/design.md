# Proposed course registration CRC cards

I would keep student, course, and registration information separate from storage and delivery. RegistrationService would coordinate the use case through small collaborator interfaces. In these cards, TuitionCalculator charges the selected course's credits, while the discount is based on the student's total credits after registration, supplied by the registration caller.

Class: Student
Responsibilities: Encapsulate and validate student ID, name, email, and an optional phone number; provide contact information through a limited public interface.
Collaborators: None.

Class: Course
Responsibilities: Encapsulate and validate course code and nonnegative course credit hours; provide course information.
Collaborators: None.

Class: Registration
Responsibilities: Represent a student's registration in a course; retain a stable registration ID, assessed tuition, resulting total credit load, and registration time; preserve the assessed amount even if tuition rules change later.
Collaborators: Student, Course.

Class: TuitionCalculator
Responsibilities: Calculate the selected course's tuition using its credits and the configured per-credit rate; apply the configured heavy-load discount when the resulting total load reaches the threshold; use decimal currency arithmetic and a defined rounding rule.
Collaborators: Course.

Class: RegistrationService
Responsibilities: Coordinate registration; obtain the tuition amount; create a Registration; save it before attempting confirmation; return the saved registration and delivery status to the caller; propagate storage failures and report notification failures without claiming the saved registration was rolled back.
Collaborators: Student, Course, Registration, TuitionCalculator, RegistrationRepository, ConfirmationSender, RegistrationLogger.

Class: RegistrationRepository interface
Responsibilities: Define the save operation for a registration; report success or storage failure without exposing storage details.
Collaborators: Registration.

Class: FileRegistrationRepository
Responsibilities: Implement RegistrationRepository; append registration records to a configured local file; escape record fields, close resources reliably, and report write failures.
Collaborators: Registration, RegistrationRepository interface.

Class: ConfirmationSender interface
Responsibilities: Define confirmation delivery for a saved registration; report delivery success or failure without exposing the delivery channel.
Collaborators: Registration.

Class: EmailConfirmationSender
Responsibilities: Implement ConfirmationSender; format and deliver email to the registered student's email address; report delivery failures.
Collaborators: ConfirmationSender interface, Registration, Student, Course.

Class: RegistrationLogger
Responsibilities: Record saved registrations and delivery outcomes with timestamps; distinguish failed storage, failed delivery, and successful completion; avoid recording unnecessary personal information.
Collaborators: Registration.

Class: RegistrationApplication
Responsibilities: Read and validate user input and the resulting total credit load; construct the configured collaborators; invoke RegistrationService; display its returned result or error through the user interface.
Collaborators: Student, Course, TuitionCalculator, FileRegistrationRepository, EmailConfirmationSender, RegistrationLogger, RegistrationService, Registration.
