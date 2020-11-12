package Database;

import java.sql.*;

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
            this.statement.execute("CREATE TABLE Decks (id INTEGER PRIMARY KEY, name TEXT, user TEXT)");
            System.out.println("Tables created");
        }catch (SQLException e){
            System.out.println("Tables already exist");
        }
    }
    
    
}
