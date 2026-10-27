# Course registration design evaluation

I would refactor CourseRegistration because registerStudent combines tuition rules, console output, file storage, notification, and logging. Each concern can change independently, but the current method forces those changes into one class. For example, changing the record format should not require editing the same method that decides a tuition discount.

## Encapsulation

Arthur Riel's heuristic 2.1 calls for keeping a class's data hidden [1]. Here, all four String fields and creditHours are public, so any caller can bypass validation or leave a partially initialized registration. A caller could assign a negative credit load, change the student ID after saving, or omit the email. I would use private state initialized through constructors and expose only the behavior each object needs. Student would own student identity and contact information, Course would own its code and credits, and Registration would preserve the student, course, and assessed tuition together.

## Responsibility and cohesion

The class concentrates unrelated behavior in one place, illustrating the behavioral god-class problem discussed by Riel [2]. Tuition calculation belongs with tuition rules; writing records belongs with storage; delivering a confirmation belongs with notification. I would keep a small registration service to coordinate these objects while letting them perform their own work. Otherwise, adding a discount or replacing the file with a database creates a reason to modify this same class, and a mistake can affect the entire registration workflow.

## Dependencies and testing

Riel's heuristic 2.2 puts dependencies on a class's public interface rather than making the class depend on its users [1]. I would apply that direction by keeping the registration model independent of the console application using it. The direct System.out calls mix presentation into registration processing, while creating FileWriter inside the method fixes the storage implementation and path. These choices make it difficult to test tuition alone or reuse the workflow in a web application. Passing collaborators through their interfaces would allow tests to use a temporary store or a recording notification sender.

## Correctness and failure handling

The method prints confirmation before it knows whether storage succeeded. It then catches every Exception, prints a stack trace, and continues to announce an email and a processed registration even if the file write failed. I would propagate a storage failure and send a confirmation only after successful persistence. Notification failure should be reported separately because it does not undo a saved registration. File resources should be closed reliably with try-with-resources, and a file implementation should escape delimiters in stored values.

The method also uses double for currency, embeds the rate and discount as literals, and does not clarify whether creditHours means one course's credits or the student's total load. Applying the heavy-load discount to one course's credits could charge the wrong amount. I would distinguish those quantities explicitly, keep the tuition policy configurable, and use decimal money values with an explicit rounding rule. Finally, printing a message about email does not actually send an email; a real notification collaborator must implement delivery.

## References

[1] Arthur J. Riel, *Object-Oriented Design Heuristics*, Addison-Wesley, 1996, Appendix A, heuristics 2.1 and 2.2. https://www.oreilly.com/library/view/object-oriented-design-heuristics/020163385X/apa.html

[2] Arthur J. Riel, *Object-Oriented Design Heuristics*, Chapter 3, section 3.3, The God Class Problem Behavioral Form. https://www.oreilly.com/library/view/object-oriented-design-heuristics/020163385X/ch03.html
