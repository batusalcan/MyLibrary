/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package library.gui;

import library.data.Database;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class SearchAuthorFrame extends JFrame {

    private JTextField nameField;
    private JTextArea resultArea;
    private JButton searchButton;

    public SearchAuthorFrame() {
        setTitle("Search Author");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(new JLabel("Author Name:"));
        nameField = new JTextField(15);
        inputPanel.add(nameField);
        searchButton = new JButton("Search");
        inputPanel.add(searchButton);
        add(inputPanel, BorderLayout.NORTH);

        
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        add(scrollPane, BorderLayout.CENTER);


        searchButton.addActionListener(e -> searchAuthor());

        setVisible(true);
    }

    private void searchAuthor() {
    String nameInput = nameField.getText().trim();

    if (nameInput.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please enter an author name.");
        return;
    }

    try (Connection conn = Database.getConnection()) {
        String sql = "SELECT * FROM authors WHERE name LIKE ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, "%" + nameInput + "%");

        ResultSet rs = stmt.executeQuery();
        StringBuilder results = new StringBuilder();

        while (rs.next()) {
            int authorId = rs.getInt("authorId");
            String name = rs.getString("name");
            String surname = rs.getString("surname");
            String website = rs.getString("website");

            results.append("Author ID: ").append(authorId).append("\n")
                   .append("Name: ").append(name).append("\n")
                   .append("Surname: ").append(surname).append("\n")
                   .append("Website: ").append(website).append("\n");

            
            PreparedStatement booksStmt = conn.prepareStatement(
                "SELECT title, year, rating, `read` FROM books WHERE authorId = ?"
            );
            booksStmt.setInt(1, authorId);
            ResultSet bookRs = booksStmt.executeQuery();

            results.append("Books:\n");

            boolean hasBooks = false;
            while (bookRs.next()) {
                hasBooks = true;
                results.append("  - Title: ").append(bookRs.getString("title")).append("\n")
                       .append("    Year: ").append(bookRs.getInt("year")).append("\n")
                       .append("    Rating: ").append(bookRs.getInt("rating")).append("\n")
                       .append("    Read Status: ").append(bookRs.getInt("read")).append("\n\n");
            }

            if (!hasBooks) {
                results.append("  No books found for this author.\n");
            }

            results.append("-------------------------------\n");
        }

        if (results.length() == 0) {
            resultArea.setText("No author found with that name.");
        } else {
            resultArea.setText(results.toString());
        }

    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
    }
}

}
