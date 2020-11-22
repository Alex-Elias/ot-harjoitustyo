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
     
    public Database() throws SQLException{
        this.database = DriverManager.getConnection("jdbc:sqlite:flashcard.db");
        this.statement = database.createStatement();
        //this.createTables();
        
    } 
    public void createTables(){
        try{
            this.statement.execute("CREATE TABLE Cards (id INTEGER PRIMARY KEY, front TEXT UNIQUE, sentence TEXT, back TEXT,"
            + " backSentence TEXT, deckID INTEGER REFERENCES Decks, review DATE, interval INTEGER)");

            this.statement.execute("CREATE TABLE Decks (id INTEGER PRIMARY KEY, name TEXT UNIQUE, userID INTEGER REFERENCES Users)");
            this.statement.execute("CREATE TABLE Users (id INTEGER PRIMARY KEY, name TEXT UNIQUE)");
            this.statement.execute("CREATE TABLE NewCards (id INTEGER PRIMARY KEY, front TEXT UNIQUE, sentence TEXT, back TEXT,"
            + " backSentence TEXT, deckID INTEGER REFERENCES Decks)");
            System.out.println("Tables created");
        }catch (SQLException e){
            System.out.println("Tables already exist");
        }
    }
    public void addCard(String front, String sentence, String back, String backSentence, String deck){
        int deckID;
        try{
            deckID = this.getDeckID(deck);
        }catch (SQLException e){
            System.out.println("deck does not exist");
            return;
        }
        try{
            PreparedStatement pre = this.database.prepareStatement("INSERT INTO NewCards (front, sentence, back, backSentence, deckID) VALUES(?,?,?,?,?)");
            pre.setString(1,front);
            pre.setString(2, sentence);
            pre.setString(3, back);
            pre.setString(4, backSentence);
            pre.setInt(5, deckID);
            
            pre.executeUpdate();
            System.out.println("Added card");
            pre.close();
        }catch(SQLException e){
            System.out.println("Error!");
        }
        
    }
    public void addCardToDatabase(Card card, String deck, int interval){
        int deckID;
        try{
            deckID = this.getDeckID(deck);
        }catch (SQLException e){
            System.out.println("deck does not exist");
            return;
        }
        try{
            PreparedStatement pre = this.database.prepareStatement("INSERT INTO Cards (front, sentence, back, backSentence, deckID, review, interval) VALUES(?,?,?,?,?,?,?)");
            pre.setString(1,card.getFront());
            pre.setString(2, card.getSentence());
            pre.setString(3, card.getBack());
            pre.setString(4, card.getBackSentence());
            pre.setInt(5, deckID);
            LocalDate ld = LocalDate.now();
            ld = ld.plusDays(interval);
            pre.setDate(6, Date.valueOf(ld));
            pre.setInt(7, interval);
            
            pre.executeUpdate();
            System.out.println("Added card");
            pre.close();
        }catch(SQLException e){
            System.out.println("Error!");
        }
        
    }
    public void addDeck(String user, String name){
        int userID;
        try{
            userID = this.getUserID(user);
        }catch (SQLException e){
            System.out.println("Could not get user ID");
            return;
        }
        try{
            PreparedStatement pre = this.database.prepareStatement("INSERT INTO Decks (name, userID) VALUES(?, ?)");
            pre.setString(1, name);
            pre.setInt(2, userID);
            
            
            pre.executeUpdate();
            System.out.println("Added deck");
            pre.close();
        }catch(SQLException e){
            System.out.println("Error!");
        }
    }
    public void addUser(String user){
        try{
            PreparedStatement pre = this.database.prepareStatement("INSERT INTO Users (name) VALUES(?)");
            pre.setString(1, user);
            
            pre.executeUpdate();
            System.out.println("Added User");
            pre.close();
        }catch (SQLException e){
            System.out.println("Error: add user");
        }
    }
    public ArrayList<String> getDecks(String user){
        int userID;
        try{
            userID = this.getUserID(user);
        }catch (SQLException e){
            System.out.println("Could not get user: get decks");
            return new ArrayList<>();
        } 
        try{
            PreparedStatement pre = this.database.prepareStatement("SELECT name FROM Decks WHERE userID=?");
            pre.setInt(1,userID);
            ResultSet resultset = pre.executeQuery();
            ArrayList<String> list = new ArrayList<>();
            while(resultset.next()){
                list.add(resultset.getString("name"));
            }
            System.out.println("returned Deck");
            pre.close();
            return list;
        }catch(SQLException e){
            System.out.println("Error: getDecks()");
        }
        return null;
    }
    public ArrayList<String> getUsers(){
        try{
            PreparedStatement pre = this.database.prepareStatement("SELECT name FROM Users");
            ResultSet resultset = pre.executeQuery();
            ArrayList<String> list = new ArrayList<>();
            while (resultset.next()){
                list.add(resultset.getString("name"));
            }
            System.out.println("returned Users");
            pre.close();
            return list;
        }catch (SQLException e){
            System.out.println("Error: get users");
        }
        return null;
    }
    
    private int getDeckID(String deck) throws SQLException{
        PreparedStatement ps = this.database.prepareStatement("SELECT id FROM Decks WHERE name=?");
        ps.setString(1, deck);
        ResultSet resultset = ps.executeQuery();
        int deckID = resultset.getInt("id");
        ps.close();
        return deckID;
    }
    private int getUserID(String user) throws SQLException{
        PreparedStatement ps = this.database.prepareStatement("SELECT id FROM Users WHERE name=?");
        ps.setString(1, user);
        ResultSet resultset = ps.executeQuery();
        int userID = resultset.getInt("id");
        ps.close();
        return userID;
        
    }
    public ArrayList<Card> getCards(String Deck){
        int deckID;
        try{
            deckID = getDeckID(Deck);
        }catch (SQLException e){
            System.out.println("Deck does not exist");
            System.out.println(e.toString());
            return new ArrayList<>();
        }
        ArrayList<Card> list = new ArrayList<>();
        try{
            PreparedStatement ps = this.database.prepareStatement("SELECT front, sentence, back, backSentence, interval FROM Cards WHERE deckID=? AND review < ?");
            ps.setInt(1, deckID);
            LocalDate ld = LocalDate.now();
            ps.setDate(2,Date.valueOf(ld));
            ResultSet resultset = ps.executeQuery();
            while(resultset.next()){
                list.add(new Card(resultset.getString("front"), resultset.getString("sentence"), resultset.getString("back"), resultset.getString("backSentence"), false, resultset.getInt("interval")));
            }
            ps.close();
        }catch (SQLException e){
            System.out.println("Could not get cards");
            System.out.println(e.toString());
        }
        return list;
    }
    public ArrayList<Card> getNewCards(String Deck){
        int deckID;
        try{
            deckID = getDeckID(Deck);
        }catch (SQLException e){
            System.out.println("Deck does not exist");
            System.out.println(e.toString());
            return new ArrayList<>();
        }
        ArrayList<Card> list = new ArrayList<>();
        
        try{
            PreparedStatement ps = this.database.prepareStatement("SELECT front, sentence, back, backSentence FROM NewCards WHERE deckID=? LIMIT 15");
            ps.setInt(1, deckID);
            ResultSet resultset = ps.executeQuery();
            while (resultset.next()){
                list.add(new Card(resultset.getString("front"), resultset.getString("sentence"), resultset.getString("back"), resultset.getString("backSentence"), true));
            }
            for (Card card : list){
                ps = this.database.prepareStatement("DELETE FROM NewCards WHERE deckID=? AND front=? AND sentence=? AND back=?");
                ps.setInt(1, deckID);
                ps.setString(2, card.getFront());
                ps.setString(3, card.getSentence());
                ps.setString(4, card.getBack());
                ps.execute();
            }
            
            ps.close();
        }catch (SQLException e){
            System.out.println("Could not get cards: new cards");
            System.out.println(e.toString());
        }
        return list;
        
    }
    
    /// finish this!!!!
    public void updateCard(Card card, String deck, int interval){
        int deckID;
        try{
            deckID = getDeckID(deck);
        }catch (SQLException e){
            System.out.println("could not get deck: updateCard");
            return;
        }
        try{
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
        }catch (SQLException e){
            System.out.println("Error: could not update card");
        }
        
    }
    public boolean isDeckEmpty(String deck){
        int deckID;
        try{
            deckID = getDeckID(deck);
        }catch (SQLException e){
            System.out.println("Deck does not exist: is DeckEmpty");
            System.out.println(e.toString());
            return true;
        }
        try{
            PreparedStatement ps = this.database.prepareStatement("SELECT COUNT(*)a FROM Cards WHERE deckID=?");
            ps.setInt(1, deckID);
            ResultSet resultset = ps.executeQuery();
            ps = this.database.prepareStatement("SELECT COUNT(*)a FROM NewCards WHERE deckID=?");
            ps.setInt(1, deckID);
            ResultSet resultset2 = ps.executeQuery();
            
            if (resultset.getInt("a") > 0 || resultset2.getInt("a") > 0){
                ps.close();
                return false;
            }
            ps.close();
        }catch (SQLException e){
            System.out.println("isDeckEmpty() error");
            System.out.println(e.toString());
        }
        
        return true;
        
    }
    public void dropTables(){
        try{
            PreparedStatement ps = this.database.prepareStatement("DROP TABLE IF EXISTS Cards");
            ps.execute();
            ps = this.database.prepareStatement("DROP TABLE IF EXISTS Decks");
            ps.execute();
            ps = this.database.prepareStatement("DROP TABLE IF EXISTS NewCards");
            ps.execute();
            ps = this.database.prepareStatement("DROP TABLE IF EXISTS Users");
            ps.execute();
            ps.close();
        }catch (SQLException e){
            System.out.println("Error: error at dropTables method");
            System.out.println(e.toString());
        }
    }
    
    
    
}
