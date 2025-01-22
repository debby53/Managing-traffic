



# Traffic Fine Management System
## Overview: 
This Traffic Fine Management System leverages object-oriented programming principles—encapsulation, inheritance, and abstraction—to efficiently manage traffic violations and fines.
The system ensures robust validation, user-friendly functionality, and meaningful exception handling.

## Key Features
Driver Management

Tracks drivers' details, total fines, and violation counts.
Provides options to reset violations and update fine information.
Traffic Violations

Handles multiple violation types such as Speeding, Parking, and Signal Violation.
Validates violation types and fine amounts.
Validation and Exception Handling

Ensures correct formatting for license numbers, violation types, and fine amounts.
Includes custom exceptions with descriptive error messages.
Design Highlights
Class Structure:

Person: Base class for all individuals in the system.
Driver: Derived class extending Person, with additional attributes like fines and violations.
TrafficViolation: Base class or interface for all violations, further extended by SpecificViolation.
Robust Validation Rules:

License numbers must be alphanumeric and 8-12 characters long.
Fines must be positive.
Violation types are restricted to predefined categories.
Ease of Use:

Add, view, and reset driver violations seamlessly.
Ensure validated input at every step.
