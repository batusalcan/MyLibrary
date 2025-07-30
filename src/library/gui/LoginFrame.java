/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package library.gui;

import library.data.Database;
import library.model.AdvancedReader;
import library.model.BasicReader;
import library.model.Reader;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginFrame() {
        setTitle("Login");
        setSize(300, 180);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2, 10, 10));

        
        add(new JLabel("Username:"));
        usernameField = new JTextField();
        add(usernameField);

        
        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        
        loginButton = new JButton("Login");
        add(new JLabel()); 
        add(loginButton);

        
        loginButton.addActionListener(e -> attemptLogin());

        setVisible(true);
    }

    private void attemptLogin() {
        String username = usernameField.getText().trim();
        String password = String.valueOf(passwordField.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both username and password.");
            return;
        }

        try (Connection conn = Database.getConnection()) {
            String sql = "SELECT * FROM userinfo WHERE username=? AND password=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("userId");         
                int userType = rs.getInt("userType");

                Reader reader;
                if (userType == 1) {
                    reader = new AdvancedReader(userId, username, password);
                    JOptionPane.showMessageDialog(this, "Welcome, Advanced Reader!");
                } else {
                    reader = new BasicReader(userId, username, password);
                    JOptionPane.showMessageDialog(this, "Welcome, Basic Reader!");
                }

                new MainFrame(reader);
                dispose();

            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password.");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }

    
}
