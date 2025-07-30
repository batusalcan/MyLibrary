# 📚 MyLibrary - Java Desktop Book Management System

A **Java Swing-based desktop application** developed as part of the **SE 2232 - Software System Analysis** course at **Yaşar University (Spring 2024–2025)**.

## 🎓 Project Info

- **Course**: SE 2232 - Software System Analysis  
- **Instructor**: Asst. Prof. Dr. Deniz Özsoyeller  
- **Students**:  
  - Batuhan Salcan 
  - Beril Filibelioğlu
- **IDE**: NetBeans  
- **Database**: MySQL  
- **Diagrams**: Visual Paradigm (.vpp file included)  


---

## 🧩 Purpose

The application allows users to manage books they have **read**, **not read**, or **wish to read**. It supports user login, book & author management, rating, commenting, and viewing book cover images.

---

## 🔐 User Types

1. **AdvancedReader (Type-1)**  
   Can use all functions: add, delete, edit, view, and rate books.

2. **BasicReader (Type-2)**  
   Limited access: can view book/author information and search.

---

## 🧠 Features / Functions

1. Add a book  
2. Delete a book  
3. Display book details  
4. Search for an author  
5. Edit/update book information  
6. List favorite books (rating 4 or 5)  
7. List favorite authors (≥ 3 books)  
8. List unread books  
9. Show upcoming releases (read=3 & releaseDate ≤ 1 week)  
10. Display book cover images

---

## 🗃️ Database Tables

- `userinfo`: Stores user credentials and type  
- `books`: Stores book information  
- `authors`: Stores author info (only if they have books)

---

## 🖼️ GUI and Screenshots

The app uses **Java Swing (JFrame)** for UI design. Screens include:

- Login Screen  
- Type-1 and Type-2 Main Panels  
- Book Detail Forms  
- Search & Filter Views

---

## 📜 License

This project is for educational use only as part of SE 2232.  
Not licensed for commercial use.

## 🙏 Acknowledgments

Thanks to our instructor and peers for support and feedback during the project.
