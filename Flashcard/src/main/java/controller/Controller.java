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
 * The class acts as an interface between the GUI class and the rest of the classes
 * The class includes the following methods:
 *  initSRS()
 *  setUP()
 *  setUser()
 *  setDeck()
 *  getUser()
 *  getDeck()
 *  setCardNull()
 *  getUsers()
 *  addUser()
 *  hard()
 *  good()
 *  easy()
 *  again()
 *  nextCard()
 *  addNewCard()
 *  getDecks()
 *  isDeckEmpty()
 *  addDeck()
 *  setIntervals()
 *  deleteUser()
 *  deleteDeck()
 *  close()
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
    /**
     * Initializes the SRS class 
     */
    public void initSRS() {
        this.srs = new SRS(this.card.getCards(this.deckID), this.card.getNewCards(this.deckID), this.card.getLearningCards(this.deckID));
        
    }
    /**
     * Creates the tables needed in the application
     */
    public void setUP() {
        this.database.createTables();
    }
    /**
     * sets the userID of the user
     * @param user 
     */
    public void setUser(String user) {
        this.userID = this.user.getUserID(user);
        
    }
    /**
     * sets the deckID of the deck
     * @param deck 
     */
    public void setDeck(String deck) {
        this.deckID = this.deck.getDeckID(deck, this.userID);
        
    }
    /**
     * returns the userID
     * @return the ID of the user as an int
     */
    public int getUser() {
        return this.userID;
    }
    /**
     * returns the deckID
     * @return the ID of the deck as an int
     */
    public int getDeck() {
        return this.deckID;
    }
    /**
     * sets the card to null
     */
    public void setCardNull() {
        this.currentCard = null;
    }
    /**
     * returns the all the users
     * @return an ArrayList of String of the names of the users
     */
    public ArrayList<String> getUsers() {
        return this.user.getUsers();
    }
    /**
     * adds user to the Users table
     * @param user the user to be added
     */
    public void addUser(String user) {
        this.user.addUser(user);
    }
    /**
     * 
     */
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
    /**
     * returns the next card to be studied
     * @return a Card
     */
    public Card nextCard() {
        this.currentCard = this.srs.getNextCard();
        return this.currentCard;
    }
    /**
     * adds a new card to the NewCard table
     * @param front the front word
     * @param sentence the front sentence
     * @param back the back word
     * @param backSentence the back sentence
     */
    public void addNewCard(String front, String sentence, String back, String backSentence) {
        this.card.addNewCard(front, sentence, back, backSentence, deckID);
    }
    /**
     * returns all the decks owned by a certain user
     * @return an ArrayList of the deck names as a String
     */
    public ArrayList<String> getDecks() {
        return this.deck.getDecks(userID);
    }
    /**
     * returns whether the deck is empty or not
     * @param deck the deck
     * @return a boolean value whether the deck is empty or not
     */
    public boolean isDeckEmpty(String deck) {
        int deckId = this.deck.getDeckID(deck, userID);
        return this.deck.isDeckEmpty(deckId);
    }
    /**
     * adds a new deck to the selected user
     * @param deckName the new deck name
     */
    public void addDeck(String deckName) {
        this.deck.addDeck(this.userID, deckName);
    }
    /**
     * sets the intervals for the following buttons: hard, good, easy
     * @param hard the new interval for the hard button
     * @param good the new interval for the good button
     * @param easy the new interval for the easy button
     */
    public void setIntervals(int hard, int good, int easy) {
        this.hard = hard;
        this.good = good;
        this.easy = easy;
    }
    /**
     * deletes a user from the Users table
     * @param user the user to be deleted
     */
    public void deleteUser(String user) {
        this.user.deleteUser(user);
    }
    /**
     * deletes a deck from the Decks table
     * @param deck the deck to be deleted
     */
    public void deleteDeck(String deck) {
        this.deck.deleteDeck(deck, userID);
    }
    /**
     * The method saves all the cards being learned to the Learning table
     * The method returns all the cards not yet studied to the appropriate table
     */
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
