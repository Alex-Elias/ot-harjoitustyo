package Database;

import java.sql.*;
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
        this.createTables();
        
    } 
    public void createTables(){
        try{
            this.statement.execute("CREATE TABLE Cards (id INTEGER PRIMARY KEY, front TEXT UNIQUE, sentence TEXT, back TEXT,"
            + " backSentence TEXT, deckID INTEGER REFERENCES Decks)");
            this.statement.execute("CREATE TABLE Decks (id INTEGER PRIMARY KEY, name TEXT UNIQUE, user TEXT)");
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
            PreparedStatement pre = this.database.prepareStatement("INSERT INTO Cards (front, sentence, back, backSentence, deckID) VALUES(?,?,?,?,?)");
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
    public void addDeck(String name){
        try{
            PreparedStatement pre = this.database.prepareStatement("INSERT INTO Decks (name) VALUES(?)");
            pre.setString(1, name);
            
            pre.executeUpdate();
            System.out.println("Added deck");
            pre.close();
        }catch(SQLException e){
            System.out.println("Error!");
        }
    }
    public ArrayList<String> getDecks(){
        try{
            PreparedStatement pre = this.database.prepareStatement("SELECT name FROM Decks");
            ResultSet resultset = pre.executeQuery();
            ArrayList<String> list = new ArrayList<>();
            while(resultset.next()){
                list.add(resultset.getString("name"));
            }
            System.out.println("returned Deck");
            return list;
        }catch(SQLException e){
            System.out.println("Error: getDecks()");
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
    
    
}
