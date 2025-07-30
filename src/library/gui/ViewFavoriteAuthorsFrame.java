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

public class ViewFavoriteAuthorsFrame extends JFrame {

    public ViewFavoriteAuthorsFrame() {
        setTitle("Favorite Authors");
        setSize(500, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columns = {"Author Name", "Author Surname", "Book Count"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        try (Connection conn = Database.getConnection()) {
            String sql = """
                SELECT a.name, a.surname, COUNT(*) AS book_count
                FROM books b
                JOIN authors a ON b.authorId = a.authorId
                GROUP BY b.authorId
                HAVING COUNT(*) >= 3
            """;

            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                int count = rs.getInt("book_count");
                model.addRow(new Object[]{name, surname, count});
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage());
        }

        add(scrollPane, BorderLayout.CENTER);
        setVisible(true);
    }
}
