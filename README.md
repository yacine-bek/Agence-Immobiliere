# Immobilière Agency Management System

## Introduction
The **Immobilière Agency Management System** is a comprehensive desktop application designed to streamline the management of agents, clients, properties, and transactions within a real estate agency. Featuring a user-friendly interface and robust backend functionality, this system ensures data integrity and efficient operations.

## Features
The application is divided into four primary modules accessible via the dashboard:
- **Agents:** View, add, edit, or delete agents. Detailed agent information is displayed upon selection.
- **Clients:** Manage client data, including personal information and property preferences.
- **Properties:** Maintain property listings, including type, size, price, location, and assigned agent.
- **Transactions:** Record and manage sales and rental transactions.

## User Interface Overview
The dashboard features a clean and intuitive layout:
- **Navigation Menu:** Located on the left-hand side, allowing navigation between modules.
- **List View:** Displays a table listing all entries for the selected module (e.g., agent names and IDs).
- **Detail Panel:** Provides detailed information about the selected item or allows data input.
- **Interaction Buttons:**
  - **Add:** Opens a form to add a new entry.
  - **Edit:** Modifies selected entries.
  - **Context Menu:** Right-clicking on an item reveals additional options like "Edit" and "Delete."
  - **Clear:** Resets input fields.

The interface uses a **dark theme** for readability, with color-coded buttons (e.g., green for "Add" and red for "Clear") for intuitive interactions.

## Data Management
All application data is stored in serialized files to ensure persistence. This enables the program to load previously saved data upon startup and save new changes upon closure.

## Class Overview
- **Agent Class:** Represents an agent with details such as `id`, `name`, and `contactInfo`.
- **Client Class:** Stores client information, preferences, and filters clients by type.
- **Property Class:** Manages property details such as type, size, price, and assigned agent.
- **Transaction Class:** Records transaction details, including `property`, `client`, `transactionType`, `date`, `amount`, and `paymentStatus`.

## Manager Classes
Each module is supported by a corresponding manager class responsible for CRUD operations and data persistence:
- **AgentManager:** Handles agent data operations.
- **ClientManager:** Manages client data, including searching by name or type.
- **PropertyManager:** Handles property listings, allowing filtering by criteria such as price and location.
- **TransactionManager:** Records transactions and filters them based on payment status and type.

## File Handling
The application uses serialization for data persistence:
- **Save Methods:** Serialize objects to `.dat` files.
- **Load Methods:** Deserialize objects from `.dat` files.
- **File Names:** `agentsData.dat`, `clientsData.dat`, `propertyData.dat`, `transactionsData.dat`.

## Highlights
- **Modular design** for scalability.
- **User-friendly GUI** with intuitive interactions.
- **Persistent storage** for offline data access.
- **Flexible search and filtering capabilities**.
- **Integration with a relational database** for enhanced data handling.

## Conclusion
The **Immobilière Agency Management System** simplifies the operations of a real estate agency by automating the management of agents, clients, properties, and transactions. Its modular structure, persistent storage, and user-friendly interface provide a robust foundation for future expansions and enhancements.

---
### Developers:
- **Yacine Bekheddouma-Abdi
