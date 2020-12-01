
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
        this.database.addNewCard("Mary", "Had", "A", "Little", "Lamb");
        ArrayList<Card> cardlist = this.database.getNewCards("Lamb");
        assertEquals(1, cardlist.size());
    }
    @Test
    public void testReturnsNoCardsWhenEmpty(){
        this.database.addDeck("t","Test");
        assertTrue(this.database.getCards("Test").isEmpty());
    }
    @Test
    public void testIsDeckEmpty(){
        this.database.addDeck("t","test");
        assertTrue(this.database.isDeckEmpty("test"));
    }
    @Test
    public void testCanAddUser(){
        this.database.addUser("test");
        assertEquals(2, this.database.getUsers().size());
    }
    @Test
    public void testIsDeckEmpty2(){
        this.database.addDeck("t", "Test");
        this.database.addNewCard("t", "e", "s", "t", "Test");
        assertFalse(this.database.isDeckEmpty("Test"));
    }
    @Test
    public void testIsDeckEmptyLearning(){
        this.database.addDeck("t", "Test");
        this.database.addLearningCard(new Card("t", "e", "s", "t", true, 10), "Test");
        assertFalse(this.database.isDeckEmpty("Test"));
    }
    @Test
    public void testIsDeckEmptyOld(){
        this.database.addDeck("t", "Test");
        this.database.addCardToDatabase(new Card("t", "e", "s", "t", true, 10), "Test", 1);
        assertFalse(this.database.isDeckEmpty("Test"));
    }
    @Test
    public void addCardToDatabaseTest() {
        this.database.addDeck("t", "Test");
        this.database.addCardToDatabase(new Card("t", "e", "s", "t", true, 10), "Test", -1);
        this.database.addCardToDatabase(new Card ("m","o","o","cow",false,10), "Test", -1);
        assertEquals(2, this.database.getCards("Test").size());
    }
    @Test
    public void returnsCorrectUsers() {
        this.database.addUser("test");
        this.database.addUser("tst");
        ArrayList<String> list = this.database.getUsers();
        assertTrue(list.contains("test"));
        assertTrue(list.contains("tst"));
    }
    @Test
    public void getCorrectCards() {
        
    }
    @Test
    public void getCorrectNewCards() {
        this.database.addDeck("t", "test");
        this.database.addNewCard("t", "e", "s", "t", "test");
        
        ArrayList<Card> list = this.database.getNewCards("test");
        assertEquals("t", list.get(0).getFront());
        assertEquals("e", list.get(0).getSentence());
        assertEquals("s", list.get(0).getBack());
        assertEquals("t", list.get(0).getBackSentence());
    }
    @Test
    public void getCorrectLearningCards() {
        this.database.addDeck("t", "test");
        this.database.addLearningCard(new Card("t", "e", "s", "t", true, 10), "test");
        
        ArrayList<Card> list = this.database.getLearningCards("test");
        assertEquals("t", list.get(0).getFront());
        assertEquals("e", list.get(0).getSentence());
        assertEquals("s", list.get(0).getBack());
        assertEquals("t", list.get(0).getBackSentence());
        assertEquals(10, list.get(0).getInterval());
    }
    @Test
    public void updateCardsCorrectly() {
        
    }
    @Test
    public void doesCardExistTestTrue() {
        this.database.addDeck("t", "test");
        this.database.addCardToDatabase(new Card("t", "e", "s", "t", true, 10), "test", 0);
        assertTrue(this.database.doesCardExist(new Card("t", "e", "s", "t", true, 10), "test"));
    }
    @Test
    public void doesCardExistTestFalse() {
        this.database.addDeck("t", "test");
        this.database.addCardToDatabase(new Card("t", "e", "s", "t", true, 10), "test", 0);
        assertFalse(this.database.doesCardExist(new Card("t", "ee", "s", "t", true, 10), "test"));
    }
    @Test
    public void canAddLearningCards() {
        this.database.addDeck("t", "test");
        this.database.addLearningCard(new Card("f","s","b","bs",true,10), "test");
        assertTrue(this.database.getLearningCards("test").size() > 0);
    }
    
    
    
}
