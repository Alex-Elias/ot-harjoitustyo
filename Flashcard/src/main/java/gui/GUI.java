package gui;

import controller.Controller;
import datastructures.Card;
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
    
    Controller controll;
    
    
    
    private TextArea cardSceneFrontText;
    private TextArea cardSceneSentenceText;
    private TextArea cardSceneBackText;
    private TextArea cardSceneBackSentenceText;
    
    private ComboBox deckSelectionBox;
    private ComboBox addSceneDeckSelection;
    
    private Button AddButton;
    private Button studyDeckButton;
    
    private ArrayList<String> deckList;
    
    
    
    private Button cardSceneHardButton;
    private Button cardSceneGoodButton;
    private Button cardSceneEasyButton;
    private Button cardSceneAgainButton;
    
    private Button cardSceneShowButton;
    
    
    

    @Override
    public void start(Stage stage) throws Exception {
        
        this.controll = new Controller();
        this.controll.setUP();
        addSceneDeckSelection = new ComboBox();
        //this.deckList = controll.getDecks();
        
        // Main deck selection scene -----------------------------------------------
        GridPane deckSelectionScene = new GridPane();
        deckSelectionScene.setPadding(new Insets(50,10,10,50));
        deckSelectionScene.setMinSize(750,750);
        
        this.deckSelectionBox = new ComboBox();
        
        
        deckSelectionBox.getSelectionModel().selectFirst();
        studyDeckButton = new Button("Study Deck");
        Button deckDeleteDeckButton = new Button("Delete Deck");
        
        HBox deckSelectionTopButtonHBox = new HBox();
        HBox deckHBox = new HBox(20);
        VBox deckSelectionVBox = new VBox();
        VBox deckSelectionDeckVBox = new VBox(20);
        
        Button AddDeckButton = new Button("Add Deck");
        Button backToUserButton = new Button("Back");
        AddButton = new Button("Add Card");
        deckSelectionTopButtonHBox.setSpacing(20);
        Label DecksLabel = new Label("Decks");
        DecksLabel.setFont(new Font(30));
        
        deckSelectionVBox.setSpacing(50);
        deckHBox.getChildren().addAll(deckSelectionBox, deckDeleteDeckButton);
        
        deckSelectionTopButtonHBox.getChildren().addAll(AddButton, AddDeckButton, backToUserButton);
        deckSelectionDeckVBox.getChildren().addAll(DecksLabel, deckHBox, studyDeckButton);
        deckSelectionVBox.getChildren().addAll(deckSelectionTopButtonHBox, deckSelectionDeckVBox);
        deckSelectionScene.add(deckSelectionVBox,1,1);
        
        
        //this.setSelectionDisabled();
        this.deckSelectionBox.setOnAction((event -> {
        
            this.setSelectionDisabled();
        }));
        deckDeleteDeckButton.setOnAction((event) ->{
        
            String deck = (String) this.deckSelectionBox.getValue();
            this.controll.deleteDeck(deck);
            this.updateDeckList();
            this.setSelectionDisabled();
            
        
        });
        
        // Select User Scene -----------------------------------------------------------------------------------
        
        GridPane userScene = new GridPane();
        userScene.setPadding(new Insets(50,10,10,50));
        userScene.setHgap(10);
        userScene.setVgap(10);
        userScene.setMinSize(750,750);
        VBox userVBox = new VBox();
        
        Button userCreateUserButton = new Button("Create New User");
        Button userSelectUserButton = new Button("Select User");
        Button userDeleteUserButton = new Button("Delete User");
        
        Label userUserLabel = new Label("Users");
        
        ComboBox userComboBox = new ComboBox();
        ArrayList<String> userList = this.controll.getUsers();
        userComboBox.setItems(FXCollections.observableArrayList(userList));
        userComboBox.getSelectionModel().selectFirst();
        userVBox.getChildren().addAll(userCreateUserButton, userUserLabel, userComboBox, userSelectUserButton);
        
        userScene.add(new Label(""), 0, 0);
        userScene.add(userCreateUserButton,2,0);
        userScene.add(userUserLabel,2,2);
        userScene.add(userComboBox, 2,3);
        userScene.add(userDeleteUserButton, 3,3);
        userScene.add(userSelectUserButton, 2, 4);
        
        if (userList.isEmpty()){
            userSelectUserButton.setDisable(true);
        }
        
        userDeleteUserButton.setOnAction((event) -> {
        
            String user = (String) userComboBox.getValue();
            this.controll.deleteUser(user);
            ArrayList<String> adduserList = this.controll.getUsers();
            userComboBox.setItems(FXCollections.observableArrayList(adduserList));
            userComboBox.getSelectionModel().selectFirst();
            if (this.controll.getUsers().isEmpty()) {
                userSelectUserButton.setDisable(true);
            }
            
        
        });
        
        
        
        
        
        
        
        
        
        // Add User Scene ------------------------------------------------------------------------
        
        GridPane addUserScene = new GridPane();
        addUserScene.setPadding(new Insets(50,10,10,50));
        addUserScene.setHgap(10);
        addUserScene.setVgap(10);
        VBox addUserVBox = new VBox(15);
        Label addUserLabel = new Label("User Name");
        TextField addUserTextBox = new TextField();
        Button addUserButton = new Button("Create User");
        Button addUserBackButton = new Button("Back");
        
        addUserVBox.getChildren().addAll(addUserLabel, addUserTextBox, addUserButton);
        addUserScene.add(addUserBackButton, 0, 0);
        addUserScene.add(addUserLabel, 1, 1);
        addUserScene.add(addUserTextBox, 1, 2);
        addUserScene.add(addUserButton, 1, 3);
        
        addUserButton.setOnAction((event -> {
        
            String user = addUserTextBox.getText();
            
            this.controll.addUser(user);
            addUserTextBox.clear();
            
        
        }));
        
        addUserBackButton.setOnAction((event -> {
        
            ArrayList<String> adduserList = this.controll.getUsers();
            userComboBox.setItems(FXCollections.observableArrayList(adduserList));
            userComboBox.getSelectionModel().selectFirst();
            
            if (!adduserList.isEmpty()){
                userSelectUserButton.setDisable(false);
                
            }
            stage.getScene().setRoot(userScene);
        
        }));
        
        
        
        
        // Add Card Scene --------------------------------------------------------------------
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
        addScenefrontText.setWrapText(true);
        
        Label addSceneSentenceLabel = new Label("Sentence:");
        TextArea addSceneSentenceText = new TextArea();
        addSceneSentenceText.setWrapText(true);
        addSceneSentenceText.setFont(new Font(20));
        
        Label addSceneBackLabel = new Label("Back:");
        TextArea addSceneBackText = new TextArea();
        addSceneBackText.setMaxHeight(20);
        addSceneBackText.setWrapText(true);
        addSceneBackText.setFont(new Font(20));
        
        Label addSceneBackSentenceLabel = new Label("Back Sentence:");
        TextArea addSceneBackSentenceText = new TextArea();
        addSceneBackSentenceText.setWrapText(true);
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
            this.controll.setDeck(deck);
            this.controll.addNewCard(front, sentence, back, backSentence);
            addScenefrontText.clear();
            addSceneSentenceText.clear();
            addSceneBackText.clear();
            addSceneBackSentenceText.clear();
            this.updateDeckList();
        
        }));
        
        // Deck add scene -----------------------------------------------------------------------------------
        GridPane deckAddScene = new GridPane();
        
        Button deckAddReturnToDeckButton = new Button("Decks");
        VBox deckAddVBox = new VBox();
        deckAddVBox.getChildren().add(new Label("Deck name"));
        TextField deckAddNameField = new TextField();
        Button deckAddAddDeckButton = new Button("Add");
        deckAddVBox.getChildren().addAll(deckAddNameField, deckAddAddDeckButton);
        
        deckAddScene.add(deckAddReturnToDeckButton, 0, 0);
        deckAddScene.add(deckAddVBox, 1, 1);
        
        
        
        
        
        // Study card scene ----------------------------------------------------------------------------------
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
        cardSceneFrontText.setWrapText(true);
        Label cardSceneSentenceLabel = new Label("Sentence:");
        cardSceneSentenceText = new TextArea();
        cardSceneSentenceText.setFont(new Font(20));
        cardSceneSentenceText.setEditable(false);
        cardSceneSentenceText.setWrapText(true);
        //sentenceLabelcard.setMaxHeight(40);
        Label cardSceneBackLabel = new Label("Back:");
        cardSceneBackText = new TextArea();
        cardSceneBackText.setMaxHeight(20);
        cardSceneBackText.setFont(new Font(20));
        cardSceneBackText.setEditable(false);
        cardSceneBackText.setWrapText(true);
        Label cardSceneBackSentenceLabel = new Label("Back Sentence:");
        cardSceneBackSentenceText = new TextArea();
        cardSceneBackSentenceText.setFont(new Font(20));
        cardSceneBackSentenceText.setEditable(false);
        cardSceneBackSentenceText.setWrapText(true);
        //backSentenceLabelcard.setMaxHeight(40);
        
        cardSceneVBox.getChildren().addAll(cardSceneFrontLabel, cardSceneFrontText, cardSceneSentenceLabel, cardSceneSentenceText, cardSceneBackLabel, cardSceneBackText,
                cardSceneBackSentenceLabel, cardSceneBackSentenceText);
        HBox cardSceneBottomHBox = new HBox(20);
        
        this.cardSceneShowButton = new Button("Show");
        
        
        cardSceneHardButton = new Button();
        cardSceneGoodButton = new Button();
        cardSceneEasyButton = new Button();
        cardSceneAgainButton = new Button();
        
        
        cardSceneGoodButton.setStyle("-fx-text-fill: blue");
        cardSceneEasyButton.setStyle("-fx-text-fill: green");
        cardSceneAgainButton.setStyle("-fx-text-fill: red");
        
        
        this.setButtonsVisible(false);
        
        
        
        cardSceneBottomHBox.getChildren().addAll(cardSceneShowButton,cardSceneAgainButton, cardSceneHardButton, cardSceneGoodButton, cardSceneEasyButton);
        cardSceneVBox.getChildren().add(cardSceneBottomHBox);
        
        CardScene.add(cardSceneVBox, 0,0);
        
        
        cardSceneShowButton.setOnAction((event -> {
            
            this.cardSceneBackText.setVisible(true);
            this.cardSceneBackSentenceText.setVisible(true);
            this.setButtonsVisible(true);
            
        
        }));
        
        
        
        
        
        
        studyDeckButton.setOnAction((event -> {
            
            stage.getScene().setRoot(CardScene);
            this.controll.setDeck((String) deckSelectionBox.getValue());
            this.controll.initSRS();
            this.cardSceneShowButton.setVisible(true);
            this.nextCard();
        }));
        
        cardSceneHardButton.setOnAction((event -> {
        
            
            this.controll.hard();
            this.nextCard();
        
        }));
        
        cardSceneGoodButton.setOnAction((event -> {
        
            
            this.controll.good();
            this.nextCard();
        }));
        
        cardSceneEasyButton.setOnAction((event -> {
        
            this.controll.easy();
            this.nextCard();
        }));
        
        cardSceneAgainButton.setOnAction((event -> {
        
            this.controll.again();
            this.nextCard();
        }));
        
        
        
        deckAddAddDeckButton.setOnAction((event -> {
        
            String deckName = deckAddNameField.getText();
            controll.addDeck(deckName);
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
            
            
            this.controll.close();
            this.controll.setCardNull();
            this.updateDeckList();
            
            stage.getScene().setRoot(deckSelectionScene);
        
        
        }));
        cardSceneGoToAddButton.setOnAction((event -> {
            this.updateDeckList();
            this.controll.close();
            this.controll.setCardNull();
            
            stage.getScene().setRoot(addCardScene);
        
        }));
        
        userCreateUserButton.setOnAction((event -> {
            
            stage.getScene().setRoot(addUserScene);
        
        }));
        
        
        backToUserButton.setOnAction((event -> {
        
        
            stage.getScene().setRoot(userScene);
        
        
        }));
        
        userSelectUserButton.setOnAction((event -> {
            
            String tempUser = (String) userComboBox.getValue();
            
            
            this.controll.setUser(tempUser);
            
            
            
            
            stage.getScene().setRoot(deckSelectionScene);
            this.updateDeckList();
            this.setSelectionDisabled();
        
        
        }));
        
        
        
        Scene scene = new Scene(userScene);
        stage.setScene(scene);
        stage.setMinHeight(250);
        stage.setMinWidth(250);
        stage.show();
        stage.setOnCloseRequest((event ->{
            this.controll.close();
            
            stage.close();
            
        
        }));
        
    }
    
    
    public void runGUI(){
        launch(GUI.class);
    }
    private void updateDeckList(){
        
        deckList = controll.getDecks();
        deckSelectionBox.setItems(FXCollections.observableArrayList(deckList));
        deckSelectionBox.getSelectionModel().selectFirst();
        addSceneDeckSelection.setItems(FXCollections.observableArrayList(deckList));
        addSceneDeckSelection.getSelectionModel().selectFirst();
    }
    private void setSelectionDisabled(){
        String deck = (String) this.deckSelectionBox.getValue();
        if (deck == null){
            this.studyDeckButton.setDisable(true);
            this.AddButton.setDisable(true);
            
            return;
        }
        if (this.controll.isDeckEmpty(deck)){
            this.studyDeckButton.setDisable(true);
            this.AddButton.setDisable(false);
            
        }else{
            this.studyDeckButton.setDisable(false);
            this.AddButton.setDisable(false);
        }
    }
    private void nextCard(){
        Card card = this.controll.nextCard();
        if (card == null) {
            this.cardSceneFrontText.setText("Congratulations!");
            this.cardSceneSentenceText.setText("You finished studing this deck for today");
            
            this.cardSceneBackText.setVisible(false);
            this.cardSceneBackSentenceText.setVisible(false);
            this.setButtonsVisible(false);
            this.cardSceneShowButton.setVisible(false);
            return;
        }
            
            
        if (card.isNew() && card.getPriority() == null){
            cardSceneHardButton.setText("Hard: 5 minutes");
            cardSceneGoodButton.setText("Good: 10 minutes");
            cardSceneEasyButton.setText("Easy: 1 Day");
            cardSceneAgainButton.setText("Again: 1 minute");
            this.controll.setIntervals(5,10,1);
            
        } else if (card.isNew() && card.getPriority() != null){
            int interval = card.getInterval();
            if (interval == 5){
                cardSceneHardButton.setText("Hard: 10 minutes");
                cardSceneGoodButton.setText("Good: 1 Day");
                cardSceneEasyButton.setText("Easy: 1 Day");
                cardSceneAgainButton.setText("Again: 1 minute");
                this.controll.setIntervals(10, 1, 1);
                
            }else if (interval == 10){
                cardSceneHardButton.setText("Hard: 1 Day");
                cardSceneGoodButton.setText("Good: 1 Day");
                cardSceneEasyButton.setText("Easy: 1 Day");
                cardSceneAgainButton.setText("Again: 1 minute");
                this.controll.setIntervals(1, 1, 1);
                
            }else{
                cardSceneHardButton.setText("Hard: 5 minutes");
                cardSceneGoodButton.setText("Good: 10 minutes");
                cardSceneEasyButton.setText("Easy: 1 Day");
                cardSceneAgainButton.setText("Again: 1 minute");
                this.controll.setIntervals(5,10,1);
            }
        }else {
            int interval = card.getInterval();
            StringBuilder sb = new StringBuilder();
            sb.append("Hard: ");
            sb.append(interval *2);
            sb.append(" Days");
            cardSceneHardButton.setText(sb.toString());
            sb = new StringBuilder();
            sb.append("Good: ");
            sb.append(interval * 3);
            sb.append(" Days");
            cardSceneGoodButton.setText(sb.toString());
            sb = new StringBuilder();
            sb.append("Easy: ");
            sb.append(interval * 4);
            sb.append(" Days");
            cardSceneEasyButton.setText(sb.toString());
            cardSceneAgainButton.setText("Again: 10 minute");
            this.controll.setIntervals(interval * 2, interval * 3, interval * 4);
            

        }

        this.cardSceneFrontText.setText(card.getFront());
        this.cardSceneSentenceText.setText(card.getSentence());
        this.cardSceneBackText.setText(card.getBack());
        this.cardSceneBackSentenceText.setText(card.getBackSentence());
        this.cardSceneBackText.setVisible(false);
        this.cardSceneBackSentenceText.setVisible(false);
        this.setButtonsVisible(false);
    }
    public void setButtonsVisible(boolean b) {
        cardSceneHardButton.setVisible(b);
        cardSceneGoodButton.setVisible(b);
        cardSceneEasyButton.setVisible(b);
        cardSceneAgainButton.setVisible(b);
    }
    
}
