# 📚 MyLibrary - Java Desktop Book Management System

> **A comprehensive Library Management System developed with Java Swing & MySQL.**
> *Designed with a focus on Software System Analysis and Engineering Standards.*

## 🎓 Project Info

- **Course**: SE 2232 - Software System Analysis (Spring 2024–2025)
- **Institution**: Yaşar University
- **Instructor**: Asst. Prof. Dr. Deniz Özsoyeller
- **Developers**: Batuhan Salcan, Beril Filibelioğlu
- **Tech Stack**: Java (NetBeans), MySQL, Java Swing (GUI)
- **Analysis Tools**: Visual Paradigm (UML/DFD)

---

## 📘 Documentation & Analysis (SRS)

This project is not just about coding; it follows strict **Software Engineering principles**. A complete **Software Requirements Specification (SRS)** document, prepared based on **IEEE Std 830-1998**, is included in this repository.

**The documentation covers:**
* **Use Case Analysis:** Detailed scenarios for Advanced vs. Basic readers.
* **Behavioral Models:** Sequence Diagrams for key operations.
* **Structural Models:** Class Diagrams showing OOP architecture.
* **Process Modeling:** Data Flow Diagrams (DFD Level 0 & Context Diagram).

📄 **[View the Final Project Report (PDF)](SE2232_FinalProjectReport.pdf)**
*(⚠️ Note: If GitHub fails to render the preview, please **download** the file to view the full analysis.)*

---

## 🧩 Purpose

The application allows users to organize their personal library efficiently. It helps users track books they have **read**, **not read**, or **wish to read**, managed through a secure login system.

---

## 🔐 User Types

The system relies on a role-based access control system managed via the `userinfo` database table:

1.  **AdvancedReader (Type-1):**
    * Has full administrative privileges.
    * Can **Add, Delete, Update** books and Authors.
    * Can manage the library content fully.

2.  **BasicReader (Type-2):**
    * Has read-only access designed for standard users.
    * Can view book details, search authors, and view lists.
    * *Cannot* modify the database.

---

## 🧠 Key Features

* **CRUD Operations:** Full Create, Read, Update, Delete functionality for books (Advanced Users).
* **Smart Validation:** Checks if an author exists before adding a book; manages dependencies automatically.
* **Search Engine:** Filter books by name or search for authors to see their complete bibliography.
* **Wishlist & Notifications:** Automatically notifies the user if a book in their "Wishlist" (Read=3) is releasing within 1 week.
* **Rating System:** Rate books (1-5 stars) and view "Favorite Books" lists.
* **Visual Elements:** Support for adding and viewing Book Cover images.

---

## 🗃️ Database Structure

The project uses a relational **MySQL** database with the following structure:
* **`userinfo`**: Manages credentials and user roles (Type 1/Type 2).
* **`books`**: Stores metadata (Title, Year, Pages, Rating, Read Status, etc.).
* **`authors`**: Linked to books; stores author details.

---

## 🖼️ GUI & Interface

The application features a user-friendly **Graphical User Interface (GUI)** built with **Java Swing**.
* **Login Screen:** Secure entry point.
* **Dynamic Main Panels:** Interface changes based on User Type.
* **Pop-up Forms:** For adding/updating books and viewing specific details.

---

## 📜 License

This project is created for educational purposes as part of the SE 2232 course.

## 🙏 Acknowledgments

Special thanks to our instructor **Asst. Prof. Dr. Deniz Özsoyeller** for her guidance on system analysis standards.
