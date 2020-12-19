/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author alex
 */
public class UserTest {
    
    private User user;
    private Database db;
    
    public UserTest() {
    }
    
    @BeforeClass
    public static void startUp() throws SQLException{
       Database db = new Database(); 
       db.dropTables();
       
    }
    
    @Before
    public void setUp() {
        try {
            db = new Database();
            user = new User();
        } catch (SQLException e) {
            
        }
        db.createTables();
    }
    @After
    public void tearDown() {
        db.dropTables();
    }
    @Test
    public void getUserIdTest() {
        this.user.addUser("test");
        assertEquals(1, this.user.getUserID("test"));
    }
    
    @Test
    public void returnsCorrectUsers() {
        this.user.addUser("test");
        this.user.addUser("tst");
        ArrayList<String> list = this.user.getUsers();
        assertTrue(list.contains("test"));
        assertTrue(list.contains("tst"));
    }
    @Test
    public void testCanAddUser(){
        this.user.addUser("test");
        assertEquals(1, this.user.getUsers().size());
    }
    @Test
    public void deleteUserTest() {
        this.user.addUser("user");
        this.user.deleteUser("user");
        assertEquals(0, this.user.getUsers().size());
    }
    
}
