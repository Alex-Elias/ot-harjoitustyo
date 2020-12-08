package database;

import datastructures.Card;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author alex
 */
public class Database {
    private Connection database;
    private Statement statement;
     
    public Database() throws SQLException {
        this.database = DriverManager.getConnection("jdbc:sqlite:flashcard.db");
        this.statement = database.createStatement();
        //this.createTables();
        
    } 
    /**
     * Creates all the tables used in the database
     * Creates the following tables:
     *  Cards
     *  Decks
     *  Users
     *  NewCards
     *  Learning
     */
    public void createTables() {
        try {
            this.statement.execute("CREATE TABLE Cards (id INTEGER PRIMARY KEY, front TEXT UNIQUE, sentence TEXT, back TEXT,"
                + " backSentence TEXT, deckID INTEGER REFERENCES Decks, review DATE, interval INTEGER)");

            this.statement.execute("CREATE TABLE Decks (id INTEGER PRIMARY KEY, name TEXT UNIQUE, userID INTEGER REFERENCES Users)");
            this.statement.execute("CREATE TABLE Users (id INTEGER PRIMARY KEY, name TEXT UNIQUE)");
            this.statement.execute("CREATE TABLE NewCards (id INTEGER PRIMARY KEY, front TEXT UNIQUE, sentence TEXT, back TEXT,"
                + " backSentence TEXT, deckID INTEGER REFERENCES Decks)");
            this.statement.execute("CREATE TABLE Learning (id INTEGER PRIMARY KEY, front TEXT UNIQUE, sentence TEXT, back TEXT,"
                + " backSentence TEXT, deckID INTEGER REFERENCES Decks, interval INTEGER)");
            System.out.println("Tables created");
        } catch (SQLException e) {
            System.out.println("Tables already exist");
            System.out.println(e.toString());
        }
    }
    /**
     * Adds a new card to the NewCards table
     * @param front the front word
     * @param sentence the sentence 
     * @param back the translation of the front word
     * @param backSentence the translation of the sentence
     * @param deck the deck which contains the word
     */
    public void addNewCard(String front, String sentence, String back, String backSentence, String deck) {
        int deckID;
        try {
            deckID = this.getDeckID(deck);
        } catch (SQLException e) {
            return;
        }
        try {
            PreparedStatement pre = this.database.prepareStatement("INSERT INTO NewCards (front, sentence, back, backSentence, deckID) VALUES(?,?,?,?,?)");
            pre.setString(1, front);
            pre.setString(2, sentence);
            pre.setString(3, back);
            pre.setString(4, backSentence);
            pre.setInt(5, deckID);
            pre.executeUpdate();
            pre.close();
        } catch (SQLException e) { }
        
    }
    /**
     * Adds a card to the Cards table
     * @param card the card to be added
     * @param deck the deck which contains the card
     * @param interval the number of days when the card will be reviewed
     */
    public void addCardToDatabase(Card card, String deck, int interval) {
        int deckID;
        try {
            deckID = this.getDeckID(deck);
        } catch (SQLException e) { 
            return;
        }
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
        } catch (SQLException e) { }      
    }
    /**
     * adds a new deck
     * @param user the user who owns the deck
     * @param name the name of the new deck
     */
    public void addDeck(String user, String name) {
        int userID;
        try {
            userID = this.getUserID(user);
        } catch (SQLException e) {
            System.out.println("Could not get user ID");
            return;
        }
        try {
            PreparedStatement pre = this.database.prepareStatement("INSERT INTO Decks (name, userID) VALUES(?, ?)");
            pre.setString(1, name);
            pre.setInt(2, userID);
            
            
            pre.execute();
            System.out.println("Added deck");
            pre.close();
        } catch (SQLException e) {
            System.out.println("Error!");
        }
    }
    /**
     * adds a new user
     * @param user the new user to be added
     */
    public void addUser(String user) {
        try {
            PreparedStatement pre = this.database.prepareStatement("INSERT INTO Users (name) VALUES(?)");
            pre.setString(1, user);
            
            pre.execute();
            System.out.println("Added User");
            pre.close();
        } catch (SQLException e) {
            System.out.println("Error: add user");
        }
    }
    /**
     * returns a list of decks owned by a certain user
     * @param user the user which decks will be returned
     * @return an ArrayList of the names of the decks
     */
    public ArrayList<String> getDecks(String user) {
        int userID;
        try {
            userID = this.getUserID(user);
        } catch (SQLException e) {
            return new ArrayList<>();
        } 
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
        } catch (SQLException e) { }
        return null;
    }
    /**
     * returns all the users
     * @return an ArrayList of strings of the usernames
     */
    public ArrayList<String> getUsers() {
        try {
            PreparedStatement pre = this.database.prepareStatement("SELECT name FROM Users");
            ResultSet resultset = pre.executeQuery();
            ArrayList<String> list = new ArrayList<>();
            while (resultset.next()) {
                list.add(resultset.getString("name"));
            }
            System.out.println("returned Users");
            pre.close();
            return list;
        } catch (SQLException e) {
            System.out.println("Error: get users");
        }
        return null;
    }
    /**
     * returns the id associated with a certain deck
     * @param deck the deck which the id will be returned
     * @return an integer of the id associated with the deck
     * @throws SQLException 
     */
    private int getDeckID(String deck) throws SQLException {
        PreparedStatement ps = this.database.prepareStatement("SELECT id FROM Decks WHERE name=?");
        ps.setString(1, deck);
        ResultSet resultset = ps.executeQuery();
        int deckID = resultset.getInt("id");
        ps.close();
        return deckID;
    }
    private int getUserID(String user) throws SQLException {
        PreparedStatement ps = this.database.prepareStatement("SELECT id FROM Users WHERE name=?");
        ps.setString(1, user);
        ResultSet resultset = ps.executeQuery();
        int userID = resultset.getInt("id");
        ps.close();
        return userID;
        
    }
    public ArrayList<Card> getCards(String deck) {
        int deckID;
        try {
            deckID = getDeckID(deck);
        } catch (SQLException e) {
            return new ArrayList<>();
        }
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
        } catch (SQLException e) { }
        return list;
    }
    public ArrayList<Card> getNewCards(String deck) {
        int deckID;
        try {
            deckID = getDeckID(deck);
        } catch (SQLException e) {
            System.out.println("Deck does not exist");
            System.out.println(e.toString());
            return new ArrayList<>();
        }
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
    public ArrayList<Card> getLearningCards(String deck) {
        int deckID;
        try {
            deckID = getDeckID(deck);
        } catch (SQLException e) {
            return new ArrayList<>();
        }
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
        } catch (SQLException e) { }
        return list;
        
    }
    
    
    public void updateCard(Card card, String deck, int interval) {
        int deckID;
        try {
            deckID = getDeckID(deck);
        } catch (SQLException e) {
            return;
        }
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
            ps.close();
        } catch (SQLException e) { }
    }
    
    public boolean isDeckEmpty(String deck) {
        int deckID;
        try {
            deckID = getDeckID(deck);
        } catch (SQLException e) {
            System.out.println("Deck does not exist: is DeckEmpty");
            System.out.println(e.toString());
            return true;
        }
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
        } catch (SQLException e) { }
        return true;
    }
    
    public boolean doesCardExist(Card card, String deck) {
        int deckID;
        try {
            deckID = getDeckID(deck);
        } catch (SQLException e) {
            System.out.println("could not get deck: doesCardExist");
            return false;
        }
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
    
    public void addCard(Card card, String deck, int interval) {
        if (this.doesCardExist(card, deck)) {
            this.updateCard(card, deck, interval);
        } else {
            this.addCardToDatabase(card, deck, interval);
        }
    }
    
    public void addLearningCard(Card card, String deck) {
        int deckID;
        try {
            deckID = getDeckID(deck);
        } catch (SQLException e) {
            System.out.println("could not get deck: doesCardExist");
            return;
        }
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
    
    
    
    
    public void dropTables() {
        try {
            PreparedStatement ps = this.database.prepareStatement("DROP TABLE IF EXISTS Cards");
            ps.execute();
            ps = this.database.prepareStatement("DROP TABLE IF EXISTS Decks");
            ps.execute();
            ps = this.database.prepareStatement("DROP TABLE IF EXISTS Users");
            ps.execute();
            ps = this.database.prepareStatement("DROP TABLE IF EXISTS NewCards");
            ps.execute();
            ps = this.database.prepareStatement("DROP TABLE IF EXISTS Learning");
            ps.execute();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error: error at dropTables method");
            System.out.println(e.toString());
        }
    }
    
    
    
}
