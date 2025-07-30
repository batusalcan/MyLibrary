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

public class ViewFavoriteBooksFrame extends JFrame {

    public ViewFavoriteBooksFrame() {
        setTitle("Favorite Books");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        
        String[] columns = {"Title", "Author", "Year", "Rating"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        try (Connection conn = Database.getConnection()) {
            String sql = """
                SELECT b.title, a.name, b.year, b.rating
                FROM books b
                JOIN authors a ON b.authorId = a.authorId
                WHERE b.read = 1 AND b.rating IN (4, 5)
            """;

            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String title = rs.getString("title");
                String author = rs.getString("name");
                int year = rs.getInt("year");
                int rating = rs.getInt("rating");
                model.addRow(new Object[]{title, author, year, rating});
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage());
        }

        add(scrollPane, BorderLayout.CENTER);
        setVisible(true);
    }
}
