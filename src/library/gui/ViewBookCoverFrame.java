/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package library.gui;

import library.data.Database;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.sql.*;

public class ViewBookCoverFrame extends JFrame {

    private JTextField bookIdField;
    private JLabel imageLabel;

    public ViewBookCoverFrame() {
        setTitle("View Book Cover");
        setSize(500, 500);
        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

       
        JPanel topPanel = new JPanel();
        bookIdField = new JTextField(10);
        JButton searchButton = new JButton("Show Cover");

        topPanel.add(new JLabel("Enter Book ID:"));
        topPanel.add(bookIdField);
        topPanel.add(searchButton);
        add(topPanel, BorderLayout.NORTH);

       
        imageLabel = new JLabel("Cover image will appear here", SwingConstants.CENTER);
        imageLabel.setPreferredSize(new Dimension(400, 400));
        add(imageLabel, BorderLayout.CENTER);

       
        searchButton.addActionListener(e -> displayCover());

        setVisible(true);
    }

    private void displayCover() {
        String idText = bookIdField.getText().trim();
        if (idText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a book ID.");
            return;
        }

        try (Connection conn = Database.getConnection()) {
            String sql = "SELECT cover FROM books WHERE bookId = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(idText));
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String coverPath = rs.getString("cover"); // Example: "/resources/images/Book1.jpg"
                URL imageUrl = getClass().getResource(coverPath);

                if (imageUrl != null) {
                    ImageIcon icon = new ImageIcon(imageUrl);
                    Image scaledImg = icon.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH);
                    imageLabel.setIcon(new ImageIcon(scaledImg));
                    imageLabel.setText("");
                } else {
                    imageLabel.setIcon(null);
                    imageLabel.setText("Image not found at: " + coverPath);
                }
            } else {
                JOptionPane.showMessageDialog(this, "No book found with this ID.");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}
