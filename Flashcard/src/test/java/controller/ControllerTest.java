
package controller;

import database.Cards;
import database.Database;
import database.Deck;
import database.User;
import datastructures.Card;
import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author alex
 */
public class ControllerTest {
    Database db;
    Controller controll;
    Cards card;
    Deck deck;
    User user;
    
    public ControllerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws SQLException {
        Database db = new Database();
        db.dropTables();
    }
    
    @Before
    public void setUp() throws SQLException, Exception {
        this.db = new Database();
        this.db.createTables();
        this.controll = new Controller();
        this.user = new User();
        this.user.addUser("user");
        this.controll.setUser("user");
        this.deck = new Deck();
        this.card = new Cards();
        this.deck.addDeck(1, "deck");
        this.controll.setDeck("deck");
        card.addNewCard("t","e","s","t", 1);
        card.addNewCard("m","o","o","o", 1);
        card.addNewCard("w","o","o","f", 1);
        card.addCardToDatabase(new Card("tt","ee","ss","tt",true), 1, -1);
        card.addCardToDatabase(new Card("test","tst","ss","tt",true), 1, -1);
        card.addLearningCard(new Card("ttt","eee","sss","ttt",true), 1);
        
        this.controll.initSRS();
    }
    
    @After
    public void tearDown() {
        this.db.dropTables();
    }
    
    @Test
    public void closeTest() {
        this.controll.close();
        ArrayList<Card> list = this.card.getLearningCards(1);
        ArrayList<Card> lst = this.card.getNewCards(1);
        assertEquals(1, list.size());
        assertEquals(3, lst.size());
        
    }
    @Test
    public void closeAddCurrentCardToLearning() {
        this.controll.nextCard();
        
        this.controll.close();
        ArrayList<Card> list = this.card.getLearningCards(1);
        ArrayList<Card> lst = this.card.getNewCards(1);
        assertEquals(1, list.size());
        assertEquals(3, lst.size());
        
    }
    @Test
    public void closeReducesLearningCardCorrectly() {
        this.controll.nextCard();
        this.controll.nextCard();
        this.controll.nextCard();
        this.controll.nextCard();
        this.controll.nextCard();
        this.controll.close();
        assertEquals(1, this.card.getLearningCards(1).size());
        assertEquals(0, this.card.getLearningCards(1).size());
    }
    
}
