/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package library.model;

/**
 *
 * @author batusalcan
 */
public class BasicReader extends Reader {
    
     public BasicReader(int userId, String username, String password) {
        super(userId, username, password);
    }

    @Override
    public int getReaderType() {
        return 2;
    }

    
}
