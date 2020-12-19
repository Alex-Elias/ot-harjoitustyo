
package database;

import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 *
 * @author alex
 */
public class DeckTest {
    private Database db;
    private User user;
    private Deck deck;
    public DeckTest() {
    }
    @BeforeClass
    public static void startUp() {
        try {
            Database db = new Database();
            
            db.dropTables();
            
        } catch (SQLException e) {
            
        }
    }
    @Before
    public void setUp() {
        
        
        try {
            db = new Database();
            db.createTables();
            this.user = new User();
            this.user.addUser("user");
            this.deck = new Deck();
        } catch (SQLException e) {
            
        }
    }
    @After
    public void tearDown() {
        db.dropTables();
    }
    @Test
    public void getDeckIDTest() {
        this.deck.addDeck(1,"Test0");
        this.deck.addDeck(1,"Test1");
        this.deck.addDeck(1,"Test2");
        assertEquals(3, this.deck.getDeckID("Test2", 1));
    }
    @Test
    public void canAddDecks(){
        this.deck.addDeck(1,"Test0");
        this.deck.addDeck(1,"Test1");
        this.deck.addDeck(1,"Test2");
        
        assertEquals(3, this.deck.getDecks(1).size());
    }
    @Test
    public void testCanReturnDecks(){
        this.deck.addDeck(1,"Test0");
        this.deck.addDeck(1,"Test1");
        this.deck.addDeck(1,"Test2");
        
        ArrayList<String> decks = this.deck.getDecks(1);
        assertTrue(decks.contains("Test0"));
        assertTrue(decks.contains("Test1"));
        assertTrue(decks.contains("Test2"));
    }
    @Test
    public void testReturnsNoDecksWhenEmpty(){
        assertTrue(this.deck.getDecks(1).isEmpty());
    }
    @Test
    public void testIsDeckEmpty(){
        assertTrue(this.deck.isDeckEmpty(1));
    }
    @Test
    public void deleteDeckTest() {
        this.deck.addDeck(1, "test");
        this.deck.deleteDeck("test", 1);
        assertEquals(0, this.deck.getDecks(1).size());
    }
    @Test
    public void returnDeckExceptionTest() {
        this.deck.addDeck(1, "test");
        assertEquals(0,this.deck.getDecks(2).size());
    }
    
    
}
