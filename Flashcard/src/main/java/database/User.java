
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author alex
 */
public class User {
    private Connection database;
    private Statement statement;
    
    public User() throws SQLException{
        this.database = DriverManager.getConnection("jdbc:sqlite:flashcard.db");
        this.statement = database.createStatement();
    }
    public int getUserID(String user) {
        try {
            PreparedStatement ps = this.database.prepareStatement("SELECT id FROM Users WHERE name=?");
            ps.setString(1, user);
            ResultSet resultset = ps.executeQuery();
            int userID = resultset.getInt("id");
            ps.close();
            return userID;
        } catch (SQLException e) {
            System.out.println("error: getUserID()");
            return 0;
            
        }
        
        
    }
    /**
     * returns all the users
     * @return an ArrayList of strings of the usernames
     */
    public ArrayList<String> getUsers() {
        try {
            PreparedStatement pre = this.database.prepareStatement("SELECT name FROM Users");
            ResultSet resultset = pre.executeQuery();
            ArrayList<String> list = new ArrayList<>();
            while (resultset.next()) {
                list.add(resultset.getString("name"));
            }
            System.out.println("returned Users");
            pre.close();
            return list;
        } catch (SQLException e) {
            System.out.println("Error: get users");
        }
        return null;
    }
    /**
     * adds a new user
     * @param user the new user to be added
     */
    public void addUser(String user) {
        try {
            PreparedStatement pre = this.database.prepareStatement("INSERT INTO Users (name) VALUES(?)");
            pre.setString(1, user);
            
            pre.execute();
            System.out.println("Added User");
            pre.close();
        } catch (SQLException e) {
            System.out.println("Error: add user");
        }
    }
    
    public void deleteUser(String user) {
        try {
            PreparedStatement pre = this.database.prepareStatement("DELETE FROM Users WHERE name=?");
            pre.setString(1,user);
            pre.execute();
            pre.close();
        } catch (SQLException e) {
            System.out.println("deleted user");
        }
    }
}
