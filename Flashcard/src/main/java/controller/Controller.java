package controller;

import database.Database;
import datastructures.Card;
import java.sql.SQLException;
import java.util.ArrayList;
import srs.SRS;

/**
 *
 * @author alex
 */
public class Controller extends Database{
    
    private SRS srs;
    private String user;
    private String deck;
    private Card card;
    
    private int hard;
    private int easy;
    private int good;
    
    public Controller() throws Exception{
        super();
    }
    public void initSRS(){
        this.srs = new SRS(this.getCards(deck), this.getNewCards(deck), this.getLearningCards(deck));
        
    }
    
    public void setUser(String user) {
        this.user = user;
    }
    public void setDeck(String deck) {
        this.deck = deck;
    }
    public String getUser() {
        return this.user;
    }
    public String getDeck() {
        return this.deck;
    }
    public void setCardNull() {
        this.card = null;
    }
    
    public void hard() {
        if (this.card.isNew() && hard != 1) {
            this.card.setInterval(hard);
            this.srs.addCard(card, hard);
        } else {
            this.addCard(card, deck, hard);
        }
    }
    public void good() {
        if (this.card.isNew() && good != 1) {
            this.card.setInterval(good);
            this.srs.addCard(card, good);
        } else {
            this.addCard(card, deck, good);
        }
    }
    public void easy() {
        this.addCard(card, deck, easy);
    }
    public void again() {
        this.card.setInterval(1);
        this.srs.addCard(card, 1);
    }
    public Card nextCard() {
        this.card = this.srs.getNextCard();
        return this.card;
    }
    
    public void addDeck(String deckName) {
        super.addDeck(user, deckName);
    }
    public void setIntervals(int hard, int good, int easy) {
        this.hard = hard;
        this.good = good;
        this.easy = easy;
    }
    public void close() {
        ArrayList<Card> list;
            try {
                list = this.srs.getLearningCards();
                for (Card tempCard : list) {
                    this.addLearningCard(tempCard, deck);
                }
                System.out.println("no error");
            } catch (Exception e) {
                System.out.println(e.toString());
            }
            try {
                list = this.srs.getLearningCards();
                for (Card card : list) {
                    this.addNewCard(card.getFront(), card.getSentence(), card.getBack(), card.getBackSentence(), deck);
                }
            } catch (Exception e) {
                System.out.println(e.toString());
            }
            if (this.card != null) {
                this.addLearningCard(this.card, deck);
            }
    }
    
    
}
