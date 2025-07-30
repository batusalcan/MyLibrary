/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package library.gui;

import library.data.Database;
import library.model.AdvancedReader;
import library.model.Reader;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MainFrame extends JFrame {

    private Reader reader;
    private JTextField searchField;
    private JButton searchButton;

    public MainFrame(Reader reader) {
        this.reader = reader;

        setTitle("MyLibrary - Main Screen");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

       
        if (reader instanceof AdvancedReader) {
            try (Connection conn = Database.getConnection()) {
                String sql = """
                    SELECT title, releaseDate FROM books
                    WHERE `read` = 3
                      AND releaseDate BETWEEN CURRENT_DATE AND DATE_ADD(CURRENT_DATE, INTERVAL 7 DAY)
                """;

                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery();

                StringBuilder upcomingBooks = new StringBuilder();
                while (rs.next()) {
                    String title = rs.getString("title");
                    Date date = rs.getDate("releaseDate");
                    upcomingBooks.append("• ").append(title).append(" (").append(date).append(")\n");
                }

                if (upcomingBooks.length() > 0) {
                    JOptionPane.showMessageDialog(
                            this,
                            "📚 The following wishlist books will be released soon:\n\n" + upcomingBooks,
                            "Upcoming Wishlist Notification",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Wishlist check failed: " + e.getMessage());
            }
        }

       
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));

       
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        searchField = new JTextField(25);
        searchButton = new JButton("Search Book");

        searchPanel.add(new JLabel("Search Book:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JLabel welcomeLabel = new JLabel("Welcome, " + reader.getUsername() + "!");
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.add(welcomeLabel);
        buttonPanel.add(Box.createVerticalStrut(20));

        buttonPanel.add(createCenteredButton("View Book Info"));
        buttonPanel.add(createCenteredButton("Search Author"));
        buttonPanel.add(createCenteredButton("View Favorite Books"));
        buttonPanel.add(createCenteredButton("View Favorite Authors"));
        buttonPanel.add(createCenteredButton("View Unread Books"));
        buttonPanel.add(createCenteredButton("View Book Cover"));

        if (reader instanceof AdvancedReader) {
            buttonPanel.add(Box.createVerticalStrut(10));
            buttonPanel.add(createCenteredButton("Add Book"));
            buttonPanel.add(createCenteredButton("Delete Book"));
            buttonPanel.add(createCenteredButton("Update Book"));
        }

        addActionListeners(buttonPanel);

        mainPanel.add(searchPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        add(mainPanel);
        setVisible(true);
    }

    private JButton createCenteredButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(200, 30));
        button.setFocusable(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void addActionListeners(JPanel panel) {
        for (Component c : panel.getComponents()) {
            if (c instanceof JButton btn) {
                switch (btn.getText()) {
                    case "Add Book" -> btn.addActionListener(e -> new AddBookFrame());
                    case "Delete Book" -> btn.addActionListener(e -> new DeleteBookFrame());
                    case "Update Book" -> btn.addActionListener(e -> new UpdateBookFrame());
                    case "View Book Info" -> btn.addActionListener(e -> new ViewBookInfo());
                    case "Search Author" -> btn.addActionListener(e -> new SearchAuthorFrame());
                    case "View Favorite Books" -> btn.addActionListener(e -> new ViewFavoriteBooksFrame());
                    case "View Favorite Authors" -> btn.addActionListener(e -> new ViewFavoriteAuthorsFrame());
                    case "View Unread Books" -> btn.addActionListener(e -> new ViewUnreadBooksFrame());
                    case "View Book Cover" -> btn.addActionListener(e -> new ViewBookCoverFrame());
                }
            }
        }

        searchButton.addActionListener(e -> {
            String query = searchField.getText().trim();
            if (!query.isEmpty()) {
                new SearchBookFrame(query);
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a book title to search.");
            }
        });
    }
}
