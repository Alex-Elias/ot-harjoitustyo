package GUI;

import Database.Database;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author alex
 */
public class GUI extends Application{
    
    Database db;

    @Override
    public void start(Stage stage) throws Exception {
        this.db = new Database();
        
        
        GridPane mainScene = new GridPane();
        mainScene.setPadding(new Insets(50,10,10,50));
        mainScene.setMinSize(750,750);
        
        ComboBox deckBox = new ComboBox();
        ArrayList<String> deckList = db.getDecks();
        deckBox.setItems(FXCollections.observableArrayList(deckList));
        deckBox.getSelectionModel().selectFirst();
        
        
        HBox DecksAddButtonsHBox = new HBox();
        VBox scene1VBox = new VBox();
        VBox DecksVBox = new VBox();
        
        Button AddDeckButton = new Button("Add Deck");
        Button AddButton = new Button("Add Card");
        DecksAddButtonsHBox.setSpacing(20);
        Label DecksLabel = new Label("Decks");
        DecksLabel.setFont(new Font(30));
        
        scene1VBox.setSpacing(50);
        
        DecksAddButtonsHBox.getChildren().addAll(AddButton, AddDeckButton);
        DecksVBox.getChildren().addAll(DecksLabel, deckBox);
        scene1VBox.getChildren().addAll(DecksAddButtonsHBox, DecksVBox);
        mainScene.add(scene1VBox,1,1);
        
        
        
        
        
        GridPane secondScene = new GridPane();
        secondScene.setMinSize(750,750);
        
        HBox topHBox = new HBox();
        topHBox.setSpacing(20);
        VBox secondSceneVBox = new VBox();
        
        Button DecksButton = new Button("Decks");
        Label topLabel = new Label("Deck:");
        ComboBox topCombo = new ComboBox(FXCollections.observableArrayList(deckList));
        topCombo.getSelectionModel().selectFirst();
        Label reminderLabel = new Label("<--- remember to choose the Deck!");
        reminderLabel.setTextFill(Color.RED);
        reminderLabel.setFont(new Font(20));
        
        topHBox.getChildren().addAll(DecksButton, topLabel, topCombo, reminderLabel);
        Label frontLabel = new Label("Front:");
        TextArea frontText = new TextArea();
        frontText.setMaxHeight(20);
        frontText.setFont(new Font(20));
        
        Label sentenceLabel = new Label("Sentence:");
        TextArea sentenceText = new TextArea();
        sentenceText.setFont(new Font(20));
        
        Label backLabel = new Label("Back:");
        TextArea backText = new TextArea();
        backText.setMaxHeight(20);
        backText.setFont(new Font(20));
        
        Label backsentenceLabel = new Label("Back Sentence:");
        TextArea backsentenceText = new TextArea();
        backsentenceText.setFont(new Font(20));
        
        Button AddCard = new Button("Add");
        
        secondSceneVBox.getChildren().addAll(topHBox,frontLabel,frontText,sentenceLabel,sentenceText,backLabel,
                backText,backsentenceLabel,backsentenceText, AddCard);
        secondScene.add(secondSceneVBox, 1, 1);
        
        
        AddCard.setOnAction((event -> {
        
            String front = frontText.getText();
            String sentence = sentenceText.getText();
            String back = backText.getText();
            String backSentence = backsentenceText.getText();
            String deck = (String) topCombo.getValue();
            this.db.addCard(front, sentence, back, backSentence, deck);
            frontText.clear();
            sentenceText.clear();
            backText.clear();
            backsentenceText.clear();
        
        }));
        
        
        GridPane deckAddScene = new GridPane();
        
        Button deckReturnToDecksButton = new Button("Decks");
        VBox deckAddVBox = new VBox();
        deckAddVBox.getChildren().add(new Label("Deck name"));
        TextField deckNameField = new TextField();
        Button addDeckButton = new Button("Add");
        deckAddVBox.getChildren().addAll(deckNameField, addDeckButton);
        
        deckAddScene.add(deckReturnToDecksButton, 0, 0);
        deckAddScene.add(deckAddVBox, 1, 1);
        
        
        addDeckButton.setOnAction((event -> {
        
            String deckName = deckNameField.getText();
            db.addDeck(deckName);
            deckNameField.clear();
        
        }));
        
        
        deckReturnToDecksButton.setOnAction((event -> {
        
            stage.getScene().setRoot(mainScene);
            
            
            
        }));
        
        
        AddDeckButton.setOnAction((event -> {
            
            stage.getScene().setRoot(deckAddScene);
        
        }));
        
        AddButton.setOnAction((event -> {
        
            stage.getScene().setRoot(secondScene);
        
        }));
        
        DecksButton.setOnAction((event ->{
        
            stage.getScene().setRoot(mainScene);
        
        }));
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        FirstScene firstscene = new FirstScene();
        
        Scene scene = new Scene(mainScene);
        stage.setScene(scene);
        stage.setMinHeight(250);
        stage.setMinWidth(250);
        stage.show();
        
    }
    public void runGUI(){
        launch(GUI.class);
    }
    
}
