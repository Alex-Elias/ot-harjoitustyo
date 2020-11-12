package GUI;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 *
 * @author alex
 */
public class FirstScene {
    private GridPane pane;
    
    public FirstScene(){
        this.pane = new GridPane();
        
        pane.setPadding(new Insets(50,10,10,175));
        pane.setMinSize(512, 512);
        
        
        HBox DecksAddButtonsHBox = new HBox();
        VBox scene1VBox = new VBox();
        VBox DecksVBox = new VBox();
        Button DecksButton = new Button("Decks:");
        Button AddButton = new Button("Add");
        DecksAddButtonsHBox.setSpacing(20);
        Label DecksLabel = new Label("Decks");
        DecksLabel.setFont(new Font(30));
        
        scene1VBox.setSpacing(50);
        
        DecksAddButtonsHBox.getChildren().addAll(DecksButton, AddButton);
        DecksVBox.getChildren().add(DecksLabel);
        scene1VBox.getChildren().addAll(DecksAddButtonsHBox, DecksVBox);
        pane.add(scene1VBox,1,1);
    }
    public GridPane getPane(){
        return this.pane;
    }
    
    
    
}
