/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package library.model;

/**
 *
 * @author batusalcan
 */
public abstract class Reader {
    
     private int userId;
    private String username;
    private String password;

    public Reader(int userId, String username, String password) {
        this.userId =userId;
        this.username = username;
        this.password = password;
    }

    public int getReaderId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public abstract int getReaderType();
    
}
