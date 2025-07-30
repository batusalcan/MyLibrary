/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package library.gui;

import library.data.Database;
import library.model.Book;
import library.model.Author;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class AddBookFrame extends JFrame {

    private JTextField titleField, yearField, pagesField, coverField, releaseDateField;
    private JTextArea aboutArea, commentsArea;
    private JComboBox<String> readCombo;
    private JComboBox<Integer> ratingCombo;
    private JTextField authorNameField, authorSurnameField;
    private JButton addButton;

    public AddBookFrame() {
        setTitle("Add New Book");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(12, 2, 5, 5));

        titleField = new JTextField();
        yearField = new JTextField();
        pagesField = new JTextField();
        coverField = new JTextField();
        releaseDateField = new JTextField();
        aboutArea = new JTextArea();
        commentsArea = new JTextArea();
        readCombo = new JComboBox<>(new String[]{"1 - Read", "2 - Not Read", "3 - Wishlist"});
        ratingCombo = new JComboBox<>(new Integer[]{0, 1, 2, 3, 4, 5});
        authorNameField = new JTextField();
        authorSurnameField = new JTextField();

        addButton = new JButton("Add Book");
        addButton.addActionListener(e -> addBook());

        add(new JLabel("Title:")); add(titleField);
        add(new JLabel("Year:")); add(yearField);
        add(new JLabel("Pages:")); add(pagesField);
        add(new JLabel("Cover Path:")); add(coverField);
        add(new JLabel("About:")); add(aboutArea);
        add(new JLabel("Read Status:")); add(readCombo);
        add(new JLabel("Rating:")); add(ratingCombo);
        add(new JLabel("Comments:")); add(commentsArea);
        add(new JLabel("Release Date (yyyy-mm-dd):")); add(releaseDateField);
        add(new JLabel("Author Name:")); add(authorNameField);
        add(new JLabel("Author Surname:")); add(authorSurnameField);
        add(new JLabel()); add(addButton);

        setVisible(true);
    }

    private void addBook() {
        try (Connection conn = Database.getConnection()) {
            String title = titleField.getText().trim();
            int year = Integer.parseInt(yearField.getText().trim());
            int pages = Integer.parseInt(pagesField.getText().trim());
            String cover = coverField.getText().trim();
            String about = aboutArea.getText().trim();
            int read = readCombo.getSelectedIndex() + 1;
            int rating = (int) ratingCombo.getSelectedItem();
            String comments = commentsArea.getText().trim();
            String releaseDate = releaseDateField.getText().trim();
            Date sqlReleaseDate = (!releaseDate.isEmpty()) ? Date.valueOf(releaseDate) : null;

            
            if (read != 3 && sqlReleaseDate != null) {
                JOptionPane.showMessageDialog(this, "Release date should only be entered for wishlist books.");
                return;
            }
            if (read != 1 && rating > 0) {
                JOptionPane.showMessageDialog(this, "Rating can only be given if the book is marked as read.");
                return;
            }

            String authorName = authorNameField.getText().trim();
            String authorSurname = authorSurnameField.getText().trim();
            Author author = new Author(authorName, authorSurname);
            int authorId = getOrCreateAuthor(conn, author);

            Book book = new Book(authorId, title, year, pages, cover, about, read, rating,
                    comments.isEmpty() ? null : comments, sqlReleaseDate);
            insertBook(conn, book);

            JOptionPane.showMessageDialog(this, "Book added successfully!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private int getOrCreateAuthor(Connection conn, Author author) throws SQLException {
        String sql = "SELECT authorId FROM authors WHERE name = ? AND surname = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, author.getName());
        stmt.setString(2, author.getSurname());
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) return rs.getInt("authorId");

        sql = "INSERT INTO authors (name, surname, website) VALUES (?, ?, ?)";
        PreparedStatement insert = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        insert.setString(1, author.getName());
        insert.setString(2, author.getSurname());
        insert.setString(3, "website-auto");
        insert.executeUpdate();
        ResultSet keys = insert.getGeneratedKeys();
        if (keys.next()) {
            int newId = keys.getInt(1);
            PreparedStatement update = conn.prepareStatement("UPDATE authors SET website=? WHERE authorId=?");
            update.setString(1, "website-" + newId);
            update.setInt(2, newId);
            update.executeUpdate();
            return newId;
        }
        throw new SQLException("Could not create author.");
    }

    private void insertBook(Connection conn, Book book) throws SQLException {
        String sql = "INSERT INTO books (authorId, title, year, numberOfPages, cover, about, `read`, rating, comments, releaseDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, book.getAuthorId());
        stmt.setString(2, book.getTitle());
        stmt.setInt(3, book.getYear());
        stmt.setInt(4, book.getNumberOfPages());
        stmt.setString(5, book.getCover());
        stmt.setString(6, book.getAbout());
        stmt.setInt(7, book.getRead());
        stmt.setInt(8, book.getRating());
        stmt.setString(9, book.getComments());
        if (book.getReleaseDate() != null) {
            stmt.setDate(10, book.getReleaseDate());
        } else {
            stmt.setNull(10, Types.DATE);
        }
        stmt.executeUpdate();
    }
}
