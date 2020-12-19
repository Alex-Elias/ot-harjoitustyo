
package database;

import datastructures.Card;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author alex
 */
public class Cards {
    private Connection database;
    private Statement statement;
     
    public Cards() throws SQLException {
        this.database = DriverManager.getConnection("jdbc:sqlite:flashcard.db");
        this.statement = database.createStatement();
        //this.createTables();
        
    }
    
    /**
     * Adds a new card to the NewCards table
     * @param front the front word
     * @param sentence the sentence 
     * @param back the translation of the front word
     * @param backSentence the translation of the sentence
     * @param deck the deck which contains the word
     */
    public void addNewCard(String front, String sentence, String back, String backSentence, int deckID) {
        
        try {
            PreparedStatement pre = this.database.prepareStatement("INSERT INTO NewCards (front, sentence, back, backSentence, deckID) VALUES(?,?,?,?,?)");
            pre.setString(1, front);
            pre.setString(2, sentence);
            pre.setString(3, back);
            pre.setString(4, backSentence);
            pre.setInt(5, deckID);
            pre.executeUpdate();
            pre.close();
        } catch (SQLException e) { 
            System.out.println("error");
        }
        
    }
    /**
     * Adds a card to the Cards table
     * @param card the card to be added
     * @param deck the deck which contains the card
     * @param interval the number of days when the card will be reviewed
     */
    public void addCardToDatabase(Card card, int deckID, int interval) {
        
        try {
            PreparedStatement pre = this.database.prepareStatement("INSERT INTO Cards (front, sentence, back, backSentence, deckID, review, interval) VALUES(?,?,?,?,?,?,?)");
            pre.setString(1, card.getFront());
            pre.setString(2, card.getSentence());
            pre.setString(3, card.getBack());
            pre.setString(4, card.getBackSentence());
            pre.setInt(5, deckID);
            LocalDate ld = LocalDate.now();
            ld = ld.plusDays(interval);
            pre.setDate(6, Date.valueOf(ld));
            pre.setInt(7, interval);
            pre.executeUpdate();
            pre.close();
        } catch (SQLException e) {
            System.out.println("error: addCardToDatabase()");
        }      
    }
    public ArrayList<Card> getCards(int deckID) {
        
        ArrayList<Card> list = new ArrayList<>();
        try {
            PreparedStatement ps = this.database.prepareStatement("SELECT front, sentence, back, backSentence, interval FROM Cards WHERE deckID=? AND review < ?");
            ps.setInt(1, deckID);
            LocalDate ld = LocalDate.now();
            ps.setDate(2, Date.valueOf(ld));
            ResultSet resultset = ps.executeQuery();
            while (resultset.next()) {
                list.add(new Card(resultset.getString("front"), resultset.getString("sentence"), resultset.getString("back"), resultset.getString("backSentence"), false, resultset.getInt("interval")));
            }
            ps.close();
        } catch (SQLException e) { 
            System.out.println("error: getCards()");
        }
        return list;
    }
    public ArrayList<Card> getNewCards(int deckID) {
        
        ArrayList<Card> list = new ArrayList<>();
        
        try {
            PreparedStatement ps = this.database.prepareStatement("SELECT front, sentence, back, backSentence FROM NewCards WHERE deckID=? LIMIT 15");
            ps.setInt(1, deckID);
            ResultSet resultset = ps.executeQuery();
            while (resultset.next()) {
                list.add(new Card(resultset.getString("front"), resultset.getString("sentence"), resultset.getString("back"), resultset.getString("backSentence"), true));
            }
            for (Card card : list) {
                ps = this.database.prepareStatement("DELETE FROM NewCards WHERE deckID=? AND front=? AND sentence=? AND back=?");
                ps.setInt(1, deckID);
                ps.setString(2, card.getFront());
                ps.setString(3, card.getSentence());
                ps.setString(4, card.getBack());
                ps.execute();
            }
            
            ps.close();
        } catch (SQLException e) {
            System.out.println("Could not get cards: new cards");
            System.out.println(e.toString());
        }
        return list;
        
    }
    public ArrayList<Card> getLearningCards(int deckID) {
        
        ArrayList<Card> list = new ArrayList<>();
        try {
            PreparedStatement ps = this.database.prepareStatement("SELECT front, sentence, back, backSentence, interval FROM Learning WHERE deckID=?");
            ps.setInt(1, deckID);
            ResultSet resultset = ps.executeQuery();
            while (resultset.next()) {
                list.add(new Card(resultset.getString("front"), resultset.getString("sentence"), resultset.getString("back"), resultset.getString("backSentence"), true, resultset.getInt("interval")));
            }
            for (Card card : list) {
                ps = this.database.prepareStatement("DELETE FROM Learning WHERE deckID=? AND front=? AND sentence=? AND back=?");
                ps.setInt(1, deckID);
                ps.setString(2, card.getFront());
                ps.setString(3, card.getSentence());
                ps.setString(4, card.getBack());
                ps.execute();
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("error: getLearningCards()");
        }
        return list;
        
    }
    public void updateCard(Card card, int deckID, int interval) {
        
        try {
            PreparedStatement ps = this.database.prepareStatement("UPDATE Cards SET "
                + "review=?, interval=? WHERE deckID=? AND front=? AND sentence=? AND back=? AND backSentence=?");
            LocalDate ld = LocalDate.now();
            ld = ld.plusDays(interval);
            ps.setDate(1, Date.valueOf(ld));
            ps.setInt(2, interval);
            ps.setInt(3, deckID);
            ps.setString(4, card.getFront());
            ps.setString(5, card.getSentence());
            ps.setString(6, card.getBack());
            ps.setString(7, card.getBackSentence());
            ps.execute();
            ps.close();
        } catch (SQLException e) { 
            System.out.println("error: updateCard()");
        }
    }
    public boolean doesCardExist(Card card, int deckID) {
        
        try {
            PreparedStatement ps = this.database.prepareStatement("SELECT COUNT(1)a FROM Cards WHERE deckID=? AND front=? AND sentence=? AND back=? AND backSentence=?");
            ps.setInt(1, deckID);
            ps.setString(2, card.getFront());
            ps.setString(3, card.getSentence());
            ps.setString(4, card.getBack());
            ps.setString(5, card.getBackSentence());
            ResultSet rs = ps.executeQuery();
            if (rs.getInt("a") == 1) {
                ps.close();
                rs.close();
                return true;
            }
            ps.close();
            rs.close();
            
        } catch (SQLException e) {
            System.out.println("Error: doesCardExist()");
        }
        
        return false;
    }
    public void addLearningCard(Card card, int deckID) {
        
        try {
            PreparedStatement ps = this.database.prepareStatement("INSERT INTO Learning (front, sentence, back, backSentence, deckID, interval) VALUES(?,?,?,?,?,?)");
            ps.setString(1, card.getFront());
            ps.setString(2, card.getSentence());
            ps.setString(3, card.getBack());
            ps.setString(4, card.getBackSentence());
            ps.setInt(5, deckID);
            ps.setInt(6, card.getInterval());
            ps.execute();
        } catch (SQLException e) {
            System.out.println("Error: addLearningCard()");
            System.out.println(e.toString());
        }
    }
    public void addCard(Card card, int deckID, int interval) {
        if (this.doesCardExist(card, deckID)) {
            this.updateCard(card, deckID, interval);
        } else {
            this.addCardToDatabase(card, deckID, interval);
        }
    }
}
