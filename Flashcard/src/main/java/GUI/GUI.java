package GUI;

import javafx.application.Application;
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

    @Override
    public void start(Stage stage) throws Exception {
        
        GridPane scene1 = new GridPane();
        scene1.setPadding(new Insets(50,10,10,50));
        scene1.setMinSize(750,750);
        
        
        HBox DecksAddButtonsHBox = new HBox();
        VBox scene1VBox = new VBox();
        VBox DecksVBox = new VBox();
        
        Button AddButton = new Button("Add Card");
        DecksAddButtonsHBox.setSpacing(20);
        Label DecksLabel = new Label("Decks");
        DecksLabel.setFont(new Font(30));
        
        scene1VBox.setSpacing(50);
        
        DecksAddButtonsHBox.getChildren().addAll(AddButton);
        DecksVBox.getChildren().add(DecksLabel);
        scene1VBox.getChildren().addAll(DecksAddButtonsHBox, DecksVBox);
        scene1.add(scene1VBox,1,1);
        
        
        
        
        
        GridPane secondScene = new GridPane();
        secondScene.setMinSize(750,750);
        
        HBox topHBox = new HBox();
        topHBox.setSpacing(20);
        VBox secondSceneVBox = new VBox();
        
        Button DecksButton = new Button("Decks");
        Label topLabel = new Label("Deck:");
        ComboBox topCombo = new ComboBox();
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
        
        
        
        
        
        AddButton.setOnAction((event -> {
        
            stage.getScene().setRoot(secondScene);
        
        }));
        
        DecksButton.setOnAction((event ->{
        
            stage.getScene().setRoot(scene1);
        
        }));
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        FirstScene firstscene = new FirstScene();
        
        Scene scene = new Scene(scene1);
        stage.setScene(scene);
        stage.setMinHeight(250);
        stage.setMinWidth(250);
        stage.show();
        
    }
    public void runGUI(){
        launch(GUI.class);
    }
    
}
