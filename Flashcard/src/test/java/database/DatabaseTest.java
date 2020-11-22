
package database;

import datastructures.Card;
import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author alex
 */
public class DatabaseTest {
    
    Database database;
    
    
    public DatabaseTest() {
    }
    
    @Before
    public void setUp(){
        try{
            this.database = new Database();
            this.database.dropTables();
            this.database.createTables();
            this.database.addUser("t");
        }catch (SQLException e){
            System.out.println("Test set up error");
        }
    }
    
    @Test
    public void testCanAddDecks(){
        this.database.addDeck("t","Test0");
        this.database.addDeck("t","Test1");
        this.database.addDeck("t","Test2");
        
        assertEquals(3, this.database.getDecks("t").size());
    }
    @After
    public void tearDown(){
        this.database.dropTables();
    }
    
    
    @Test
    public void testCanReturnDecks(){
        this.database.addDeck("t","Test0");
        this.database.addDeck("t","Test1");
        this.database.addDeck("t","Test2");
        
        ArrayList<String> decks = this.database.getDecks("t");
        assertTrue(decks.contains("Test0"));
        assertTrue(decks.contains("Test1"));
        assertTrue(decks.contains("Test2"));
    }
    
    @Test
    public void testReturnsNoDecksWhenEmpty(){
        assertTrue(this.database.getDecks("t").isEmpty());
    }
    
    @Test
    public void testCanAddCards(){
        this.database.addDeck("t","Lamb");
        this.database.addCard("Mary", "Had", "A", "Little", "Lamb");
        ArrayList<Card> cardlist = this.database.getNewCards("Lamb");
        assertEquals(1, cardlist.size());
    }
    @Test
    public void testReturnsNoCardsWhenEmpty(){
        this.database.addDeck("t","Test");
        assertTrue(this.database.getCards("Test").isEmpty());
    }
    
    
    
}
