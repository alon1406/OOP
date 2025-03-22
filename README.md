# Submission 3 - ReadME

## Section 17 - Design Pattern: Command
For this section, we created a `Command` interface that defines the `execute` method. Additionally, we implemented specific classes for each of the cases **99-103**, all located in the `Commands` package. We also created `FactoryCommands`, responsible for creating the appropriate command class based on user input.

The `Command` pattern is executed through the function `actionByCommand`, located in `ManagerFacade` at **line 540**.

## Section 18 - Design Pattern: Memento
In this section, we created the `Memento` class inside `ManagerFacade` at **line 518**. This class contains a property that stores the `ArrayList`, along with a private constructor and a `toString` method.

- **Line 532**: A function that generates a `Memento` object, saving the `ArrayList` used in the project.
- **Line 536**: A function `SetMemento` that updates the `ArrayList` with the stored state.

### Implementation
- **Case 104** (`ManagerFacade`, line **472**):
  - Checks if the project’s `ArrayList` contains objects.
  - If yes, it creates an `ArrayList`, generates a `Memento`, and prints it.
  - If not, it prints an error message.

- **Case 105** (`ManagerFacade`, line **483**):
  - Checks if a `Memento` exists in the project.
  - If yes, it restores the stored `Memento` and prints it.
  - If not, it prints an error message.

## Section 19 - Design Pattern: Adapter
In this section, we created the `IteratorTarget` interface, which includes the following functions:
```java
Object myNext();
Object myPrevious();
boolean myHasPrevious();
```
We implemented the `ListIteratorAdapter` class, which implements `IteratorTarget` and adapts the required functions.

### Implementation
- The class contains a `listIterator` property received from `ManagerFacade`.
- We create an instance of `ListIteratorAdapter` in `ManagerFacade` at **line 117**.
- The adapted functions are used in a `while` loop starting from the next line.

