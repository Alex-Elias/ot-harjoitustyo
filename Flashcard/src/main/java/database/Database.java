package database;

import datastructures.Card;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * The class creates the required tables to run the application
 * The class offers the following methods:
 *  createTables()
 *  dropTables()
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

            this.statement.execute("CREATE TABLE Decks (id INTEGER PRIMARY KEY, name TEXT, userID INTEGER REFERENCES Users)");
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
     * The method drops all the tables created by createTable() method
     * the method drops the following tables:
     *  Cards
     *  Decks
     *  Users
     *  NewCards
     *  Learning
     */
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
