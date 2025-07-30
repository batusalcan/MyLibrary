/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package library.model;

public class Author {
    private int authorId;
    private String name;
    private String surname;
    private String website;

    
    public Author(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    
    public int getAuthorId() { return authorId; }
    public void setAuthorId(int authorId) { this.authorId = authorId; }

    public String getName() { return name; }
    public String getSurname() { return surname; }
    public String getWebsite() { return website; }
    public void setWebsite(String website) { this.website = website; }
}
