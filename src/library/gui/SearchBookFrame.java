/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package library.gui;

import library.data.Database;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class SearchBookFrame extends JFrame {

    public SearchBookFrame(String bookTitle) {
        setTitle("Search Results for: " + bookTitle);
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JTextArea resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        add(scrollPane, BorderLayout.CENTER);

        try (Connection conn = Database.getConnection()) {
            String sql = "SELECT b.*, a.name AS authorName, a.surname AS authorSurname " +
                         "FROM books b JOIN authors a ON b.authorId = a.authorId " +
                         "WHERE b.title LIKE ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + bookTitle + "%");
            ResultSet rs = stmt.executeQuery();

            StringBuilder sb = new StringBuilder();
            boolean found = false;

            while (rs.next()) {
                found = true;
                sb.append("Title: ").append(rs.getString("title")).append("\n");
                sb.append("Author: ").append(rs.getString("authorName"))
                  .append(" ").append(rs.getString("authorSurname")).append("\n");
                sb.append("Year: ").append(rs.getInt("year")).append("\n");
                sb.append("Pages: ").append(rs.getInt("numberOfPages")).append("\n");
                sb.append("Rating: ").append(rs.getInt("rating")).append("\n");
                sb.append("Read Status: ").append(getReadStatus(rs.getInt("read"))).append("\n");
                sb.append("Comments: ").append(rs.getString("comments")).append("\n");
                sb.append("-----------\n");
            }

            if (!found) {
                sb.append("No books found with title containing '").append(bookTitle).append("'.");
            }

            resultArea.setText(sb.toString());

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage());
        }

        setVisible(true);
    }

    private String getReadStatus(int read) {
        return switch (read) {
            case 1 -> "Read";
            case 2 -> "Not Read";
            case 3 -> "Wishlist";
            default -> "Unknown";
        };
    }
}
