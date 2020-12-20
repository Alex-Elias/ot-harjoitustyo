package datastructures;

import java.time.LocalTime;

/**
 *
 * @author alex
 */
public class Card {
    private String front;
    private String sentence;
    private String back;
    private String backSentence;
    private LocalTime priority;
    private int interval;
    private boolean newCard;
    
    
    public Card(String front, String sentence, String back, String backSentence, boolean newCard) {
        this.front = front;
        this.sentence = sentence;
        this.back = back;
        this.backSentence = backSentence;
        this.newCard = newCard;
        
    }
    
    
    public Card(String front, String sentence, String back, String backSentence, boolean newCard, int interval) {
        this(front, sentence, back, backSentence, newCard);
        this.interval = interval;
        
    }
    public String getFront() {
        return front;
    }
    public String getSentence() {
        return this.sentence;
    }
    public String getBack() {
        return this.back;
    }
    public String getBackSentence() {
        return this.backSentence;
    }
    public void setPriority(LocalTime priority) {
        this.priority = priority;
    }
    public LocalTime getPriority() {
        return this.priority;
    }
    public void setInterval(int interval) {
        this.interval = interval;
    }
    public int getInterval() {
        return this.interval;
    }
    public boolean isNew() {
        return this.newCard;
    }
}
