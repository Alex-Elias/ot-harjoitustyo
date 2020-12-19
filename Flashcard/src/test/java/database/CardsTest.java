/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import datastructures.Card;
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
public class CardsTest {
    private Database db;
    private User user;
    private Deck deck;
    private Cards card;
    
    public CardsTest() {
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
            this.db = new Database();
            this.db.createTables();
            this.user = new User();
            this.deck = new Deck();
            this.card = new Cards();
            this.user.addUser("user");
            this.deck.addDeck(1,"deck");
        } catch (SQLException e) {
            
        }
    }
    
    @After
    public void tearDown() {
        this.db.dropTables();
    }
    
    @Test
    public void testCanAddCards(){
        
        this.card.addNewCard("Mary", "Had", "A", "Little",1);
        ArrayList<Card> cardlist = this.card.getNewCards(1);
        assertEquals(1, cardlist.size());
    }
    @Test
    public void testReturnsNoCardsWhenEmpty(){
        
        assertTrue(this.card.getCards(1).isEmpty());
    }
    @Test
    public void testIsDeckEmpty2(){
        
        this.card.addNewCard("t", "e", "s", "t", 1);
        assertFalse(this.deck.isDeckEmpty(1));
    }
    @Test
    public void testIsDeckEmptyLearning(){
        this.card.addLearningCard(new Card("t", "e", "s", "t", true, 10), 1);
        assertFalse(this.deck.isDeckEmpty(1));
    }
    @Test
    public void testIsDeckEmptyOld(){
        this.card.addCardToDatabase(new Card("t", "e", "s", "t", true, 10), 1, 1);
        assertFalse(this.deck.isDeckEmpty(1));
    }
    @Test
    public void addCardToDatabaseTest() {
        this.card.addCardToDatabase(new Card("t", "e", "s", "t", true, 10), 1, -1);
        this.card.addCardToDatabase(new Card ("m","o","o","cow",false,10), 1, -1);
        assertEquals(2, this.card.getCards(1).size());
    }
    @Test
    public void getCorrectNewCards() {
        this.card.addNewCard("t", "e", "s", "t", 1);
        
        ArrayList<Card> list = this.card.getNewCards(1);
        assertEquals("t", list.get(0).getFront());
        assertEquals("e", list.get(0).getSentence());
        assertEquals("s", list.get(0).getBack());
        assertEquals("t", list.get(0).getBackSentence());
    }
    @Test
    public void getCorrectLearningCards() {
        this.card.addLearningCard(new Card("t", "e", "s", "t", true, 10), 1);
        
        ArrayList<Card> list = this.card.getLearningCards(1);
        assertEquals("t", list.get(0).getFront());
        assertEquals("e", list.get(0).getSentence());
        assertEquals("s", list.get(0).getBack());
        assertEquals("t", list.get(0).getBackSentence());
        assertEquals(10, list.get(0).getInterval());
    }
    @Test
    public void doesCardExistTestTrue() {
        this.card.addCardToDatabase(new Card("t", "e", "s", "t", true, 10), 1, 0);
        assertTrue(this.card.doesCardExist(new Card("t", "e", "s", "t", true, 10), 1));
    }
    @Test
    public void doesCardExistTestFalse() {
        this.card.addCardToDatabase(new Card("t", "e", "s", "t", true, 10), 1, 0);
        assertFalse(this.card.doesCardExist(new Card("t", "ee", "s", "t", true, 10), 1));
    }
    @Test
    public void canAddLearningCards() {
        this.card.addLearningCard(new Card("f","s","b","bs",true,10), 1);
        assertTrue(this.card.getLearningCards(1).size() > 0);
    }
    @Test
    public void getCorrectCards() {
        this.card.addCardToDatabase(new Card("t", "e", "s", "t", true, 10), 1, -1);
        this.card.addCardToDatabase(new Card ("m","o","o","cow",false,10), 1, 1);
        assertTrue(this.card.getCards(1).size() == 1);
        ArrayList<Card> list = this.card.getCards(1);
        assertEquals("t", list.get(0).getFront());
        assertEquals("e", list.get(0).getSentence());
        assertEquals("s", list.get(0).getBack());
        assertEquals("t", list.get(0).getBackSentence());
    }
    @Test
    public void updateCardTest() {
        this.card.addCardToDatabase(new Card("t", "e", "s", "t", true, 10), 1, -1);
        this.card.updateCard(new Card("t", "e", "s", "t", true, 10), 1, -10);
        ArrayList<Card> list = this.card.getCards(1);
        assertEquals(-10, list.get(0).getInterval());
    }
    
}
