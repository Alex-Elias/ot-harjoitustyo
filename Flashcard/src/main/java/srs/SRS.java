package srs;

import datastructures.Card;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 *
 * @author alex
 */
public class SRS {
    private PriorityQueue<Card> queue;
    private ArrayList<Card> cardList;
    private ArrayList<Card> newCardList;
    private ArrayList<Card> learningList;
    
    private double increaseAmount = 2.5;
    private double size;
    
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
    /// check!!!!!
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
    public ArrayList<Card> getLearningCards(){
        ArrayList<Card> list = new ArrayList<>();
        while (!queue.isEmpty()){
            list.add(queue.poll());
        }
        return list;
    }
    public void addCard(Card card, int interval) {
        LocalTime lt = LocalTime.now();
        lt = lt.plusMinutes(interval);
        card.setPriority(lt);
        this.queue.add(card);
        
        
    }
    public ArrayList<Card> getNewCards(){
        return this.newCardList;
    }
    
}
class CustomCardComparator implements Comparator<Card> {
    
    @Override
    public int compare(Card o1, Card o2) {
        int value = o1.getPriority().compareTo(o2.getPriority());
        return value;
        
    }
    
}
