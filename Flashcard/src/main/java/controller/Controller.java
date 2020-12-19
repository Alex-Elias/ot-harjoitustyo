package controller;

import database.Cards;
import database.Database;
import database.Deck;
import database.User;
import datastructures.Card;
import java.sql.SQLException;
import java.util.ArrayList;
import srs.SRS;

/**
 *
 * @author alex
 */
public class Controller {
    
    private SRS srs;
    private Card currentCard;
    
    private int userID;
    private int deckID;
   
    private int hard;
    private int easy;
    private int good;
    
    private User user;
    private Deck deck;
    private Cards card;
    private Database database;
    
    public Controller() throws Exception {
        this.user = new User();
        this.deck = new Deck();
        this.card = new Cards();
        this.database = new Database();
        
    }
    public void initSRS() {
        this.srs = new SRS(this.card.getCards(this.deckID), this.card.getNewCards(this.deckID), this.card.getLearningCards(this.deckID));
        
    }
    public void setUP() {
        this.database.createTables();
    }
    
    public void setUser(String user) {
        this.userID = this.user.getUserID(user);
        
    }
    public void setDeck(String deck) {
        this.deckID = this.deck.getDeckID(deck, this.userID);
        
    }
    public int getUser() {
        return this.userID;
    }
    public int getDeck() {
        return this.deckID;
    }
    public void setCardNull() {
        this.currentCard = null;
    }
    public ArrayList<String> getUsers() {
        return this.user.getUsers();
    }
    public void addUser(String user) {
        this.user.addUser(user);
    }
    
    public void hard() {
        if (this.currentCard.isNew() && hard != 1) {
            this.currentCard.setInterval(hard);
            this.srs.addCard(currentCard, hard);
        } else {
            this.card.addCard(currentCard, this.deckID, hard);
        }
    }
    public void good() {
        if (this.currentCard.isNew() && good != 1) {
            this.currentCard.setInterval(good);
            this.srs.addCard(currentCard, good);
        } else {
            this.card.addCard(currentCard, this.deckID, good);
        }
    }
    public void easy() {
        this.card.addCard(currentCard, this.deckID, easy);
    }
    public void again() {
        this.currentCard.setInterval(1);
        this.srs.addCard(currentCard, 1);
    }
    public Card nextCard() {
        this.currentCard = this.srs.getNextCard();
        return this.currentCard;
    }
    public void addNewCard(String front, String sentence, String back, String backSentence) {
        this.card.addNewCard(front, sentence, back, backSentence, deckID);
    }
    public ArrayList<String> getDecks() {
        return this.deck.getDecks(userID);
    }
    public boolean isDeckEmpty(String deck) {
        int deckId = this.deck.getDeckID(deck, userID);
        return this.deck.isDeckEmpty(deckId);
    }
    public void addDeck(String deckName) {
        this.deck.addDeck(this.userID, deckName);
    }
    public void setIntervals(int hard, int good, int easy) {
        this.hard = hard;
        this.good = good;
        this.easy = easy;
    }
    public void deleteUser(String user) {
        this.user.deleteUser(user);
    }
    public void deleteDeck(String deck) {
        this.deck.deleteDeck(deck, userID);
    }
    public void close() {
        ArrayList<Card> list;
        try {
            list = this.srs.getLearningCards();
            for (Card tempCard : list) {
                this.card.addLearningCard(tempCard, this.deckID);
            }
            
        } catch (Exception e) { }
        try {
            list = this.srs.getLearningCards();
            for (Card card : list) {
                this.card.addNewCard(card.getFront(), card.getSentence(), card.getBack(), card.getBackSentence(), this.deckID);
            }
        } catch (Exception e) { }
        if (this.currentCard != null) {
            this.card.addLearningCard(this.currentCard, this.deckID);
        }
    }
    
    
}
