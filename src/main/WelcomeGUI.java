package main;

import java.net.MalformedURLException;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import main.Controller;
import main.GameGUI;
//The welcome window for the game
public class WelcomeGUI {
	/*Fields*/
	private Controller controller;
	private Stage stage;
	private Label nameLabel, welcomeLabel;
	private TextField nameText;
	private Button enterButton;
	private BorderPane mainPane;
	
	public WelcomeGUI(Stage stage) {		
		//gets the parent stage
		this.stage = stage;
		
	}
	
	public void setWelcomeScene() {
		
		//Top for main pane
		HBox topPane = new HBox();
		welcomeLabel = new Label("Welcome to Hang Man");
		welcomeLabel.setFont(Font.font(30));
		topPane.getChildren().add(welcomeLabel);
		topPane.setAlignment(Pos.CENTER);
		
		//Center for main pane
		HBox centerPane = new HBox(20);
		nameLabel = new Label("Player name: ");
		nameText = new TextField();
		centerPane.getChildren().addAll(nameLabel, nameText);
		centerPane.setAlignment(Pos.CENTER);
		
		//Bottom for main pane
		HBox botPane = new HBox();
		enterButton = new Button("Start!");
		enterButton.setDefaultButton(true);
		enterButton.setStyle("-fx-background-color: gray");
		botPane.getChildren().addAll(enterButton);
		botPane.setAlignment(Pos.CENTER);
		
		enterButton.setOnAction(e-> {
			try {
				controller = new Controller(nameText.getText());
			} 
			catch (MalformedURLException e1) {
				e1.printStackTrace();
			}			
			userPromptScene();
		});
		
		//main pane setup
		mainPane = new BorderPane();
		mainPane.setTop(topPane);
		mainPane.setCenter(centerPane);
		mainPane.setBottom(botPane);
		
		for(Node child : mainPane.getChildren()) {
			mainPane.setMargin(child, new Insets(10,10,10,10));
		}
		
		Scene scene = new Scene(mainPane, 500, 250);
		stage.setScene(scene);
	}
	
	//the method returns the scene for user input for game words
	public void userPromptScene() {
		
		Label promptLabel = new Label("Question words: ");
		Label hintLabel = new Label("Hint: ");
		
		TextField promptText = new TextField();
		promptText.setPromptText("No whitespace please!");
		
		TextField hintText = new TextField();
		
		HBox top = new HBox(10);
		top.getChildren().addAll(promptLabel, promptText);
		
		HBox bottom = new HBox(10);
		bottom.getChildren().addAll(hintLabel, hintText);
		
		Button enterButton = new Button("Enter");
		enterButton.setDefaultButton(true);
		enterButton.setStyle("-fx-background-color: green");
		enterButton.setOnAction(e-> {
			GameGUI gameGUI = new GameGUI(promptText.getText().toUpperCase(),
					hintText.getText().toUpperCase(), stage, controller);
			gameGUI.setGameScene();
		});
		
		GridPane main = new GridPane();
		main.add(promptLabel, 0, 0);
		main.add(promptText, 1, 0);
		main.add(hintLabel, 0, 1);
		main.add(hintText, 1, 1);
		main.add(enterButton, 2, 1);
		
		for(Node child : main.getChildren()) {
			main.setMargin(child, new Insets(10, 10, 10, 10));
		}
		
		stage.setScene(new Scene(main, 450, 150));
	}
}
