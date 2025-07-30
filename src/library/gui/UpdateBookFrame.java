/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package library.gui;

import library.data.Database;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class UpdateBookFrame extends JFrame {

    private JTextField bookIdField, titleField, yearField, pagesField, coverField, releaseDateField;
    private JTextArea aboutArea, commentsArea;
    private JComboBox<String> readCombo;
    private JComboBox<Integer> ratingCombo;
    private JButton loadButton, updateButton;

    public UpdateBookFrame() {
        setTitle("Update Book");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(12, 2, 5, 5));

        bookIdField = new JTextField();
        titleField = new JTextField();
        yearField = new JTextField();
        pagesField = new JTextField();
        coverField = new JTextField();
        releaseDateField = new JTextField();
        aboutArea = new JTextArea();
        commentsArea = new JTextArea();
        readCombo = new JComboBox<>(new String[]{"1 - Read", "2 - Not Read", "3 - Wishlist"});
        ratingCombo = new JComboBox<>(new Integer[]{0, 1, 2, 3, 4, 5});

        loadButton = new JButton("Load Book");
        updateButton = new JButton("Update Book");

        loadButton.addActionListener(e -> loadBook());
        updateButton.addActionListener(e -> updateBook());

        add(new JLabel("Book ID:")); add(bookIdField);
        add(loadButton); add(new JLabel());
        add(new JLabel("Title:")); add(titleField);
        add(new JLabel("Year:")); add(yearField);
        add(new JLabel("Pages:")); add(pagesField);
        add(new JLabel("Cover Path:")); add(coverField);
        add(new JLabel("About:")); add(aboutArea);
        add(new JLabel("Read Status:")); add(readCombo);
        add(new JLabel("Rating:")); add(ratingCombo);
        add(new JLabel("Comments:")); add(commentsArea);
        add(new JLabel("Release Date (yyyy-mm-dd):")); add(releaseDateField);
        add(new JLabel()); add(updateButton);

        setVisible(true);
    }

    private void loadBook() {
        try (Connection conn = Database.getConnection()) {
            int bookId = Integer.parseInt(bookIdField.getText().trim());
            String sql = "SELECT * FROM books WHERE bookId = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, bookId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                titleField.setText(rs.getString("title"));
                yearField.setText(String.valueOf(rs.getInt("year")));
                pagesField.setText(String.valueOf(rs.getInt("numberOfPages")));
                coverField.setText(rs.getString("cover"));
                aboutArea.setText(rs.getString("about"));
                commentsArea.setText(rs.getString("comments"));
                readCombo.setSelectedIndex(rs.getInt("read") - 1);
                ratingCombo.setSelectedItem(rs.getInt("rating"));
                Date d = rs.getDate("releaseDate");
                releaseDateField.setText(d != null ? d.toString() : "");
            } else {
                JOptionPane.showMessageDialog(this, "Book not found!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void updateBook() {
        try (Connection conn = Database.getConnection()) {
            int bookId = Integer.parseInt(bookIdField.getText().trim());
            String title = titleField.getText().trim();
            int year = Integer.parseInt(yearField.getText().trim());
            int pages = Integer.parseInt(pagesField.getText().trim());
            String cover = coverField.getText().trim();
            String about = aboutArea.getText().trim();
            int read = readCombo.getSelectedIndex() + 1;
            int rating = (int) ratingCombo.getSelectedItem();
            String comments = commentsArea.getText().trim();
            String releaseDate = releaseDateField.getText().trim();
            Date sqlDate = releaseDate.isEmpty() ? null : Date.valueOf(releaseDate);

           
            if (read != 3 && sqlDate != null) {
                JOptionPane.showMessageDialog(this, "Release date should only be set for wishlist books.");
                return;
            }
            if (read != 1 && rating > 0) {
                JOptionPane.showMessageDialog(this, "Rating is only allowed for read books.");
                return;
            }

            String sql = "UPDATE books SET title=?, year=?, numberOfPages=?, cover=?, about=?, `read`=?, rating=?, comments=?, releaseDate=? WHERE bookId=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, title);
            stmt.setInt(2, year);
            stmt.setInt(3, pages);
            stmt.setString(4, cover);
            stmt.setString(5, about);
            stmt.setInt(6, read);
            stmt.setInt(7, rating);
            stmt.setString(8, comments.isEmpty() ? null : comments);
            if (sqlDate != null) {
                stmt.setDate(9, sqlDate);
            } else {
                stmt.setNull(9, Types.DATE);
            }
            stmt.setInt(10, bookId);

            int result = stmt.executeUpdate();
            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Book updated successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Update failed.");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}
