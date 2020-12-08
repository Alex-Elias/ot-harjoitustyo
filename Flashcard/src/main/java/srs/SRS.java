package srs;

import datastructures.Card;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * A class which implements the Spaced Repetition 
 * This class offers the following methods:
 *  getNextCard()
 *  getLearningCards()
 *  addCard()
 *  getNewCards()
 * @author alex
 */
public class SRS {
    private PriorityQueue<Card> queue;
    private ArrayList<Card> cardList;
    private ArrayList<Card> newCardList;
    private ArrayList<Card> learningList;
    
   
    private double size;
    /**
     * Initialized SRS class with three ArrayLists containing different lists of cards
     * @param cardList the list of cards that are due and have already been studied
     * @param newCardList the list of cards that are new
     * @param learningList the list of cards that are still being learned
     */
    public SRS(ArrayList<Card> cardList, ArrayList<Card> newCardList, ArrayList<Card> learningList) {
        this.queue = new PriorityQueue<>(new CustomCardComparator());
        this.cardList = cardList;
        this.newCardList = newCardList;
        this.learningList = learningList;
        
        if (!this.learningList.isEmpty()) {
            for (Card card : this.learningList) {
                this.addCard(card, card.getInterval());
            }
        }
        
        if (!newCardList.isEmpty()) {
            this.size = cardList.size() / newCardList.size();

        } else { 
            this.size = -1;
        }
        
    }
    /**
     * Returns the next card to be studied
     * @return a card or null
     */
    public Card getNextCard() {
        if (this.queue.isEmpty()) {
            if (!this.cardList.isEmpty()) {
                Card card = cardList.get(0);
                cardList.remove(0);
                return card;
            }
            if (!newCardList.isEmpty()) {
                Card card = newCardList.get(0);
                newCardList.remove(0);
                return card;
            }
            return null;
        }
        LocalTime time = LocalTime.now();
        int value = time.compareTo(this.queue.peek().getPriority());
        if (value > 0) {
            return this.queue.poll();
        }
        if (this.size == -1 && !this.cardList.isEmpty()) {
            Card card = cardList.get(0);
            cardList.remove(0);
            return card;
        }
        if (!this.newCardList.isEmpty()) {
            double ratio = this.cardList.size() / this.newCardList.size();
            
            if (ratio > this.size) {
                Card card = cardList.get(0);
                cardList.remove(0);
                return card;
            } else {
                Card card = newCardList.get(0);
                newCardList.remove(0);
                return card;  
            }
        }
        return this.queue.poll();
        
    }
    /**
     * Returns all the cards currently being studied
     * @return an ArrayList of cards
     */
    public ArrayList<Card> getLearningCards() {
        ArrayList<Card> list = new ArrayList<>();
        while (!queue.isEmpty()) {
            list.add(queue.poll());
        }
        return list;
    }
    /**
     * Adds a card to the queue of learning cards
     * @param card the card to be added to the queue
     * @param interval the interval of which the card will be reviewed
     */
    public void addCard(Card card, int interval) {
        LocalTime lt = LocalTime.now();
        lt = lt.plusMinutes(interval);
        card.setPriority(lt);
        this.queue.add(card);
        
        
    }
    public ArrayList<Card> getNewCards() {
        return this.newCardList;
    }
    
}
/**
 * A Comparator class which compares different cards by their priority
 * @author alex
 */
class CustomCardComparator implements Comparator<Card> {
    
    @Override
    public int compare(Card o1, Card o2) {
        int value = o1.getPriority().compareTo(o2.getPriority());
        return value;
        
    }
    
}
