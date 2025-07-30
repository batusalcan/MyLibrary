/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package library.gui;

import library.data.Database;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class DeleteBookFrame extends JFrame {

    private JTextField bookIdField;
    private JButton deleteButton;

    public DeleteBookFrame() {
        setTitle("Delete Book");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 1, 10, 10));

        
        bookIdField = new JTextField();
        deleteButton = new JButton("Delete");

        add(new JLabel("Enter Book ID to delete:"));
        add(bookIdField);
        add(deleteButton);

        deleteButton.addActionListener(e -> deleteBook());

        setVisible(true);
    }

    private void deleteBook() {
        String idText = bookIdField.getText().trim();

        if (idText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Book ID cannot be empty.");
            return;
        }

        try {
            int bookId = Integer.parseInt(idText);

            try (Connection conn = Database.getConnection()) {
                String sql = "DELETE FROM books WHERE bookId = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, bookId);
                int affected = stmt.executeUpdate();

                if (affected > 0) {
                    JOptionPane.showMessageDialog(this, "Book deleted successfully.");
                    dispose(); 
                } else {
                    JOptionPane.showMessageDialog(this, "No book found with that ID.");
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid Book ID. Please enter a number.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error deleting book: " + ex.getMessage());
        }
    }
}
