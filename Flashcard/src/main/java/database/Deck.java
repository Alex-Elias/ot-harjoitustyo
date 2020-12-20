
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * The class offers all the methods related to the Deck table
 * The included methods are:
 *  getDeckID
 *  getDecks
 *  addDeck
 *  isDeckEmpty
 *  deleteDeck
 * @author alex
 */
public class Deck {
    private Connection database;
    private Statement statement;
    
    public Deck() throws SQLException {
        this.database = DriverManager.getConnection("jdbc:sqlite:flashcard.db");
        this.statement = database.createStatement();
    }
    /**
     * returns the id associated with a certain deck
     * @param deck the deck which the id will be returned
     * @param userID the ID of the user
     * @return an integer of the id associated with the deck
      
     */
    public int getDeckID(String deck, int userID) {
        try {
            PreparedStatement ps = this.database.prepareStatement("SELECT id FROM Decks WHERE name=? AND userID=?");
            ps.setString(1, deck);
            ps.setInt(2, userID);
            ResultSet resultset = ps.executeQuery();
            int deckID = resultset.getInt("id");
            ps.close();
            return deckID;
        } catch (SQLException e) {
            return 0;
        }
            
    }
    /**
     * returns a list of decks owned by a certain user
     * @param userID the user ID which decks will be returned
     * @return an ArrayList of the names of the decks
     */
    public ArrayList<String> getDecks(int userID) {
        
        try {
            PreparedStatement pre = this.database.prepareStatement("SELECT name FROM Decks WHERE userID=?");
            pre.setInt(1, userID);
            ResultSet resultset = pre.executeQuery();
            ArrayList<String> list = new ArrayList<>();
            while (resultset.next()) {
                list.add(resultset.getString("name"));
            }
            pre.close();
            return list;
        } catch (SQLException e) { 
        }
        return null;
    }
    /**
    * adds a new deck
    * @param userID the userID who owns the deck
    * @param name the name of the new deck
    */
    public void addDeck(int userID, String name) {
        
        
        try {
            PreparedStatement pre = this.database.prepareStatement("INSERT INTO Decks (name, userID) VALUES(?, ?)");
            pre.setString(1, name);
            pre.setInt(2, userID);
            
            
            pre.execute();
            pre.close();
        } catch (SQLException e) {
        }
    }
    /**
     * Returns whether a certain deck is empty or not
     * @param deckID The ID pointing to a certain deck
     * @return a boolean value whether the deck is empty or not
     */
    public boolean isDeckEmpty(int deckID) {
        try {
            PreparedStatement ps = this.database.prepareStatement("SELECT COUNT(*)a FROM Cards WHERE deckID=?");
            ps.setInt(1, deckID);
            ResultSet resultset = ps.executeQuery();
            ps = this.database.prepareStatement("SELECT COUNT(*)a FROM NewCards WHERE deckID=?");
            ps.setInt(1, deckID);
            ResultSet resultset2 = ps.executeQuery();
            ps = this.database.prepareStatement("SELECT COUNT (*)a FROM Learning WHERE deckID=?");
            ps.setInt(1, deckID);
            ResultSet resultset3 = ps.executeQuery();
            if (resultset.getInt("a") > 0 || resultset2.getInt("a") > 0 || resultset3.getInt("a") > 0) {
                ps.close();
                resultset.close();
                resultset2.close();
                resultset3.close();
                return false;
            }
            ps.close();
            resultset.close();
            resultset2.close();
            resultset3.close();
        } catch (SQLException e) { 
        }
        return true;
    }
    /**
     * deletes a certain deck in the Decks table
     * @param deck the deck name to be deleted
     * @param userID the user ID which the deck belongs to
     */
    public void deleteDeck(String deck, int userID) {
        try {
            PreparedStatement pre = this.database.prepareStatement("DELETE FROM Decks WHERE name=? AND userID=?");
            pre.setString(1, deck);
            pre.setInt(2, userID);
            pre.execute();
            pre.close();
                    
        } catch (SQLException e) {
        }
    }
}
