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

public class ViewBookInfo extends JFrame {

    private JTextField bookIdField;
    private JButton displayButton;
    private JTable resultTable;
    private DefaultTableModel tableModel;

    public ViewBookInfo() {
        setTitle("View Book Info");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

     
        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(new JLabel("Enter Book ID:"));

        bookIdField = new JTextField(10);
        topPanel.add(bookIdField);

        displayButton = new JButton("Display");
        topPanel.add(displayButton);

        add(topPanel, BorderLayout.NORTH);

       
        String[] columns = {"Title", "Author", "Year", "Pages", "Read Status", "Rating"};
        tableModel = new DefaultTableModel(columns, 0);
        resultTable = new JTable(tableModel);
        add(new JScrollPane(resultTable), BorderLayout.CENTER);

        
        displayButton.addActionListener(e -> displayBook());

        setVisible(true);
    }

    private void displayBook() {
        tableModel.setRowCount(0);  
        String input = bookIdField.getText().trim();

        if (input.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a Book ID.");
            return;
        }

        try {
            int bookId = Integer.parseInt(input);

            try (Connection conn = Database.getConnection()) {
                String sql = """
                    SELECT b.title, a.name AS author_name, a.surname AS author_surname,
                           b.year, b.numberOfPages, b.read, b.rating
                    FROM books b
                    JOIN authors a ON b.authorId = a.authorId
                    WHERE b.bookId = ?
                """;

                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, bookId);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    String title = rs.getString("title");
                    String author = rs.getString("author_name") + " " + rs.getString("author_surname");
                    int year = rs.getInt("year");
                    int pages = rs.getInt("numberOfPages");
                    int read = rs.getInt("read");
                    String readStatus = switch (read) {
                        case 1 -> "Read";
                        case 2 -> "Not Read";
                        case 3 -> "Wishlist";
                        default -> "Unknown";
                    };
                    int rating = rs.getInt("rating");

                    tableModel.addRow(new Object[]{title, author, year, pages, readStatus, rating});
                } else {
                    JOptionPane.showMessageDialog(this, "No book found with ID " + bookId);
                }

            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Book ID must be a number.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }
}
