/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package library.gui;

import library.data.Database;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ViewUnreadBooksFrame extends JFrame {

    public ViewUnreadBooksFrame() {
        setTitle("Unread Books");
        setSize(900, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columns = {
            "Title", "Author", "Year", "Pages", "Cover", "About",
            "Read", "Rating", "Comments", "Release Date"
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        try (Connection conn = Database.getConnection()) {
            String sql = """
                SELECT b.*, a.name AS author_name, a.surname AS author_surname
                FROM books b
                JOIN authors a ON b.authorId = a.authorId
                WHERE b.read = 2
            """;

            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String title = rs.getString("title");
                String author = rs.getString("author_name") + " " + rs.getString("author_surname");
                int year = rs.getInt("year");
                int pages = rs.getInt("numberOfPages");
                String cover = rs.getString("cover");
                String about = rs.getString("about");
                int read = rs.getInt("read");
                int rating = rs.getInt("rating");
                String comments = rs.getString("comments");
                Date releaseDate = rs.getDate("releaseDate");

                model.addRow(new Object[]{
                    title, author, year, pages, cover, about,
                    read, rating, comments, releaseDate
                });
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage());
        }

        add(scrollPane, BorderLayout.CENTER);
        setVisible(true);
    }
}
