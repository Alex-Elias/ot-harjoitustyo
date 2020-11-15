package GUI;

import Card.Card;
import database.Database;
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
    ArrayList<Card> cardList;
    
    private int place;
    
    private TextArea cardSceneFrontText;
    private TextArea cardSceneSentenceText;
    private TextArea cardSceneBackText;
    private TextArea cardSceneBackSentenceText;
    
    private ComboBox deckSelectionBox;
    private ComboBox addSceneDeckSelection;
    
    private Button AddButton;
    private Button studyDeckButton;
    
    private ArrayList<String> deckList;

    @Override
    public void start(Stage stage) throws Exception {
        
        this.db = new Database();
        this.db.createTables();
        addSceneDeckSelection = new ComboBox();
        //this.deckList = db.getDecks();
        
        // Main deck selection scene
        GridPane deckSelectionScene = new GridPane();
        deckSelectionScene.setPadding(new Insets(50,10,10,50));
        deckSelectionScene.setMinSize(750,750);
        
        this.deckSelectionBox = new ComboBox();
        this.updateDeckList();
        
        deckSelectionBox.getSelectionModel().selectFirst();
        studyDeckButton = new Button("Study Deck");
        
        
        HBox deckSelectionTopButtonHBox = new HBox();
        VBox deckSelectionVBox = new VBox();
        VBox deckSelectionDeckVBox = new VBox(20);
        
        Button AddDeckButton = new Button("Add Deck");
        AddButton = new Button("Add Card");
        deckSelectionTopButtonHBox.setSpacing(20);
        Label DecksLabel = new Label("Decks");
        DecksLabel.setFont(new Font(30));
        
        deckSelectionVBox.setSpacing(50);
        
        deckSelectionTopButtonHBox.getChildren().addAll(AddButton, AddDeckButton);
        deckSelectionDeckVBox.getChildren().addAll(DecksLabel, deckSelectionBox, studyDeckButton);
        deckSelectionVBox.getChildren().addAll(deckSelectionTopButtonHBox, deckSelectionDeckVBox);
        deckSelectionScene.add(deckSelectionVBox,1,1);
        
        if (this.deckList.isEmpty()){
            this.AddButton.setDisable(true);
            this.studyDeckButton.setDisable(true);
        }
        //this.setSelectionDisabled();
        this.deckSelectionBox.setOnAction((event -> {
        
            this.setSelectionDisabled();
        }));
        
        
        
        // Add Card Scene
        GridPane addCardScene = new GridPane();
        addCardScene.setMinSize(750,750);
        
        HBox addSceneTopHBox = new HBox();
        addSceneTopHBox.setSpacing(20);
        VBox addSceneVBox = new VBox();
        
        Button addSceneReturnToDeckScenebutton = new Button("Decks");
        Label addSceneDeckLabel = new Label("Deck:");
        
        addSceneDeckSelection.getSelectionModel().selectFirst();
        Label addScenereminderLabel = new Label("<--- remember to choose the Deck!");
        addScenereminderLabel.setTextFill(Color.RED);
        addScenereminderLabel.setFont(new Font(20));
        
        addSceneTopHBox.getChildren().addAll(addSceneReturnToDeckScenebutton, addSceneDeckLabel, addSceneDeckSelection, addScenereminderLabel);
        Label addScenefrontLabel = new Label("Front:");
        TextArea addScenefrontText = new TextArea();
        addScenefrontText.setMaxHeight(20);
        addScenefrontText.setFont(new Font(20));
        
        Label addSceneSentenceLabel = new Label("Sentence:");
        TextArea addSceneSentenceText = new TextArea();
        addSceneSentenceText.setFont(new Font(20));
        
        Label addSceneBackLabel = new Label("Back:");
        TextArea addSceneBackText = new TextArea();
        addSceneBackText.setMaxHeight(20);
        addSceneBackText.setFont(new Font(20));
        
        Label addSceneBackSentenceLabel = new Label("Back Sentence:");
        TextArea addSceneBackSentenceText = new TextArea();
        addSceneBackSentenceText.setFont(new Font(20));
        
        Button addSceneAddCardButton = new Button("Add");
        
        addSceneVBox.getChildren().addAll(addSceneTopHBox,addScenefrontLabel,addScenefrontText,addSceneSentenceLabel,addSceneSentenceText,addSceneBackLabel,
                addSceneBackText,addSceneBackSentenceLabel,addSceneBackSentenceText, addSceneAddCardButton);
        addCardScene.add(addSceneVBox, 1, 1);
        
        
        addSceneAddCardButton.setOnAction((event -> {
        
            String front = addScenefrontText.getText();
            String sentence = addSceneSentenceText.getText();
            String back = addSceneBackText.getText();
            String backSentence = addSceneBackSentenceText.getText();
            String deck = (String) addSceneDeckSelection.getValue();
            this.db.addCard(front, sentence, back, backSentence, deck);
            addScenefrontText.clear();
            addSceneSentenceText.clear();
            addSceneBackText.clear();
            addSceneBackSentenceText.clear();
            this.updateDeckList();
        
        }));
        
        // Deck add scene
        GridPane deckAddScene = new GridPane();
        
        Button deckAddReturnToDeckButton = new Button("Decks");
        VBox deckAddVBox = new VBox();
        deckAddVBox.getChildren().add(new Label("Deck name"));
        TextField deckAddNameField = new TextField();
        Button deckAddAddDeckButton = new Button("Add");
        deckAddVBox.getChildren().addAll(deckAddNameField, deckAddAddDeckButton);
        
        deckAddScene.add(deckAddReturnToDeckButton, 0, 0);
        deckAddScene.add(deckAddVBox, 1, 1);
        
        
        
        
        
        // Study card scene
        GridPane CardScene = new GridPane();
        
        Button cardSceneReturnToDeckButton = new Button("Decks");
        Button cardSceneGoToAddButton = new Button("Add Card");
        
        HBox cardSceneTopHBox = new HBox(20);
        cardSceneTopHBox.getChildren().addAll(cardSceneReturnToDeckButton, cardSceneGoToAddButton);
        
        VBox cardSceneVBox = new VBox();
        cardSceneVBox.getChildren().add(cardSceneTopHBox);
        
        Label cardSceneFrontLabel = new Label("Front:");
        cardSceneFrontText = new TextArea();
        cardSceneFrontText.setMaxHeight(20);
        cardSceneFrontText.setFont(new Font(20));
        cardSceneFrontText.setEditable(false);
        Label cardSceneSentenceLabel = new Label("Sentence:");
        cardSceneSentenceText = new TextArea();
        cardSceneSentenceText.setFont(new Font(20));
        cardSceneSentenceText.setEditable(false);
        //sentenceLabelcard.setMaxHeight(40);
        Label cardSceneBackLabel = new Label("Back:");
        cardSceneBackText = new TextArea();
        cardSceneBackText.setMaxHeight(20);
        cardSceneBackText.setFont(new Font(20));
        cardSceneBackText.setEditable(false);
        Label cardSceneBackSentenceLabel = new Label("Back Sentence:");
        cardSceneBackSentenceText = new TextArea();
        cardSceneBackSentenceText.setFont(new Font(20));
        cardSceneBackSentenceText.setEditable(false);
        //backSentenceLabelcard.setMaxHeight(40);
        
        cardSceneVBox.getChildren().addAll(cardSceneFrontLabel, cardSceneFrontText, cardSceneSentenceLabel, cardSceneSentenceText, cardSceneBackLabel, cardSceneBackText,
                cardSceneBackSentenceLabel, cardSceneBackSentenceText);
        HBox cardSceneBottomHBox = new HBox(20);
        
        Button cardSceneShowButton = new Button("Show");
        Button cardSceneNextButton = new Button("Next");
        cardSceneBottomHBox.getChildren().addAll(cardSceneShowButton, cardSceneNextButton);
        cardSceneVBox.getChildren().add(cardSceneBottomHBox);
        
        CardScene.add(cardSceneVBox, 0,0);
        
        
        cardSceneShowButton.setOnAction((event -> {
            
            this.cardSceneBackText.setVisible(true);
            this.cardSceneBackSentenceText.setVisible(true);
        
        }));
        
        cardSceneNextButton.setOnAction((event -> {
            this.place++;
            if (this.place >= this.cardList.size()){
                this.place = 0;
            }
            this.cardSceneBackText.setVisible(false);
            this.cardSceneBackSentenceText.setVisible(false);
            
            Card card = this.cardList.get(place);
            this.cardSceneFrontText.setText(card.getFront());
            this.cardSceneSentenceText.setText(card.getSentence());
            this.cardSceneBackText.setText(card.getBack());
            this.cardSceneBackSentenceText.setText(card.getBackSentence());
            
        
        }));
        
        
        
        
        studyDeckButton.setOnAction((event -> {
            this.place = 0;
            stage.getScene().setRoot(CardScene);
            String deck = (String) deckSelectionBox.getValue();
            this.cardList = this.db.getCards(deck);
            Card card = this.cardList.get(0);
            this.cardSceneFrontText.setText(card.getFront());
            this.cardSceneSentenceText.setText(card.getSentence());
            this.cardSceneBackText.setText(card.getBack());
            this.cardSceneBackSentenceText.setText(card.getBackSentence());
            this.cardSceneBackText.setVisible(false);
            this.cardSceneBackSentenceText.setVisible(false);
        }));
        
        
        
        deckAddAddDeckButton.setOnAction((event -> {
        
            String deckName = deckAddNameField.getText();
            db.addDeck(deckName);
            deckAddNameField.clear();
        
        }));
        
        
        deckAddReturnToDeckButton.setOnAction((event -> {
            this.updateDeckList();
            
            stage.getScene().setRoot(deckSelectionScene);
            if (!this.deckList.isEmpty()){
                this.AddButton.setDisable(false);
                
            }
            
            
            
        }));
        
        
        AddDeckButton.setOnAction((event -> {
            
            stage.getScene().setRoot(deckAddScene);
        
        }));
        
        AddButton.setOnAction((event -> {
            
            stage.getScene().setRoot(addCardScene);
            this.updateDeckList();
            
        
        }));
        
        addSceneReturnToDeckScenebutton.setOnAction((event ->{
            this.updateDeckList();
            stage.getScene().setRoot(deckSelectionScene);
        
        }));
        cardSceneReturnToDeckButton.setOnAction((event -> {
            this.updateDeckList();
            stage.getScene().setRoot(deckSelectionScene);
        
        
        }));
        cardSceneGoToAddButton.setOnAction((event -> {
            this.updateDeckList();
            stage.getScene().setRoot(addCardScene);
        
        }));
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        Scene scene = new Scene(deckSelectionScene);
        stage.setScene(scene);
        stage.setMinHeight(250);
        stage.setMinWidth(250);
        stage.show();
        
    }
    public void runGUI(){
        launch(GUI.class);
    }
    private void updateDeckList(){
        
        deckList = db.getDecks();
        deckSelectionBox.setItems(FXCollections.observableArrayList(deckList));
        addSceneDeckSelection.setItems(FXCollections.observableArrayList(deckList));
    }
    private void setSelectionDisabled(){
        String deck = (String) this.deckSelectionBox.getValue();
        if (deck == null){
            return;
        }
        if (this.db.isDeckEmpty(deck)){
            this.studyDeckButton.setDisable(true);
        }else{
            this.studyDeckButton.setDisable(false);
        }
    }
    
}
