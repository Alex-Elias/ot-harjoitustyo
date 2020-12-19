
package srs;

import datastructures.Card;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author alex
 */
public class SRSTest {
    ArrayList<Card> newList;
    ArrayList<Card> list;
    ArrayList<Card> learning;
    SRS srs;
    
    public SRSTest() {
    }
    @Before
    public void setUp() {
        this.newList = new ArrayList<>();
        this.learning = new ArrayList<>();
        this.list = new ArrayList<>();
        newList.add(new Card("test","t","e","s", true));
        learning.add(new Card("tes","t","e","s", false));
        list.add(new Card("tt","ee","ss","tt",true));
        
        this.srs = new SRS(list, newList, learning);
    }
    
    @Test
    public void canGetNextCardTest() {
        assertTrue(this.srs.getNextCard() != null);
    }
    
    @Test
    public void canAddCardTest() {
        this.srs.getNextCard();
        this.srs.getNextCard();
        this.srs.addCard(new Card("t","e","s","t", true), 10);
        assertTrue(this.srs.getNextCard() != null);
    }
    
    @Test
    public void returnsNullWhenEmptyTest(){
        this.srs.getNextCard();
        this.srs.getNextCard();
        this.srs.getNextCard();
        assertEquals(null, this.srs.getNextCard());
    }
    @Test
    public void returnsCorrectLearningCards() {
        ArrayList<Card> list = this.srs.getLearningCards();
        assertEquals("tes", list.get(0).getFront());
        assertEquals("t", list.get(0).getSentence());
        assertEquals("e", list.get(0).getBack());
        assertEquals("s", list.get(0).getBackSentence());
    }
    @Test
    public void getNextCardReturnsCorrectSecondCard() {
        this.srs.getNextCard();
        Card card = this.srs.getNextCard();
        assertEquals("tt", card.getFront());
        assertEquals("ee", card.getSentence());
        assertEquals("ss", card.getBack());
        assertEquals("tt", card.getBackSentence());
    }
    @Test
    public void getNextCardReturnsCorrectThirdCard() {
        this.srs.getNextCard();
        this.srs.getNextCard();
        Card card = this.srs.getNextCard();
        assertEquals("test", card.getFront());
        assertEquals("t", card.getSentence());
        assertEquals("e", card.getBack());
        assertEquals("s", card.getBackSentence());
    }
    
    
    
    
}
