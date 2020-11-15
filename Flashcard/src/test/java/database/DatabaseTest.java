
package database;

import Card.Card;
import java.sql.SQLException;
import java.util.ArrayList;
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
        }catch (SQLException e){
            System.out.println("Test set up error");
        }
    }
    
    @Test
    public void testCanAddDecks(){
        this.database.addDeck("Test0");
        this.database.addDeck("Test1");
        this.database.addDeck("Test2");
        
        assertEquals(3, this.database.getDecks().size());
    }
    
    @Test
    public void testCanReturnDecks(){
        this.database.addDeck("Test0");
        this.database.addDeck("Test1");
        this.database.addDeck("Test2");
        
        ArrayList<String> decks = this.database.getDecks();
        assertTrue(decks.contains("Test0"));
        assertTrue(decks.contains("Test1"));
        assertTrue(decks.contains("Test2"));
    }
    
    @Test
    public void testReturnsNoDecksWhenEmpty(){
        assertTrue(this.database.getDecks().isEmpty());
    }
    
    @Test
    public void testCanAddCards(){
        this.database.addDeck("Lamb");
        this.database.addCard("Mary", "Had", "A", "Little", "Lamb");
        ArrayList<Card> cardlist = this.database.getCards("Lamb");
        assertEquals(1, cardlist.size());
    }
    @Test
    public void testReturnsNoCardsWhenEmpty(){
        this.database.addDeck("Test");
        assertTrue(this.database.getCards("Test").isEmpty());
    }
    
    
    
}
