package main;

import java.util.ArrayList;
import java.util.TreeMap;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import main.Controller;

//The class represents the GUI for playing game
public class GameGUI {
	private	Font font = Font.font("Century Gothic", 20);
	private Stage stage;
	private Label gameTitle;
	private ArrayList<Label> hiddenWords;
	private String gameWord, hint, playerName;
	private Scene scene;
	private Button[] alphabetButtons;
	private int count = 10;
	private TreeMap<String, Long> players;
	private Label countLabel;
	private Controller control;

	public GameGUI(String gameWord, String hint, Stage stage, Controller control) {
		this.stage = stage;
		this.countLabel = new Label();
		this.gameWord = gameWord;
		this.hiddenWords = new ArrayList<Label>();
		this.hint = hint;
		this.scene = null;
		this.control = control;
		this.playerName = control.getPlayerName();
		this.players = control.getPlayersData();
	}


	//Guess Words 
	public void guess(String alphabet) {
		boolean guessRight = false;

		ArrayList<Label> removeLabels = new ArrayList<Label>();
		for(Label word : hiddenWords) {
			if(word.getText().equals(alphabet)) {
				word.setStyle("-fx-background-color: white");
				removeLabels.add(word);
				guessRight = true;
			}
		}

		//update words list
		for(Label removeL : removeLabels) {
			hiddenWords.remove(removeL);
		}

		//right answer label to show in the end
		Label rightAns = new Label("Correct Answer: " + gameWord);
		rightAns.setFont(font);

		//winning state
		if(guessRight && hiddenWords.size() == 0) {
			VBox winDisplay = new VBox(20);

			Label winL = new Label("Winner, " + playerName + "!");
			winL.setFont(font);

			//update the score
			long score = players.get(playerName) + 1;
			players.put(playerName, score);

			Label scoreL = new Label(playerName + "'s score: " + players.get(playerName));
			scoreL.setFont(font);

			//saves the player data
			control.writePlayersData();

			winDisplay.getChildren().addAll(winL, scoreL, rightAns);
			for(Node child : winDisplay.getChildren()) {
				winDisplay.setMargin(child, new Insets(10, 10, 10, 10));
			}
			scene = new Scene(winDisplay, 500, 250);
			stage.setScene(scene);
		}
		else if(!guessRight) {
			count--;
		}

		//update the trial count label
		countLabel.setText( "Trials Left: \n      " + count + "");

		//Lost state
		if(count == 0) {
			VBox lostDisplay = new VBox(20);

			Label lostL = new Label("Sorry, " + playerName + ", ;(");
			lostL.setFont(font);

			Label scoreL = new Label(playerName + "'s score: " + players.get(playerName));
			scoreL.setFont(font);

			//saves the player data
			control.writePlayersData();

			lostDisplay.getChildren().addAll(lostL, scoreL, rightAns);
			for(Node child : lostDisplay.getChildren()) {
				lostDisplay.setMargin(child, new Insets(10, 10, 10, 10));
			}
			scene = new Scene(lostDisplay, 500, 250);
			stage.setScene(scene);
		}
	}

	//Prepares the buttons and guess words
	public void prepareContents() {

		//Prepare buttons
		char[] alphabets = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

		alphabetButtons = new Button[26];

		for(int i = 0; i < alphabetButtons.length; i++) {
			alphabetButtons[i] = new Button(alphabets[i] + "");
			int num = i;
			alphabetButtons[i].setOnAction(e-> {
				alphabetButtons[num].setDisable(true);
				guess(alphabets[num] + "");
			});
		}

		//prepare guess words
		char[] gameWords = gameWord.toCharArray(); 
		for(int i = 0; i < gameWords.length; i++) {
			Label word = new Label(gameWords[i] + "");
			word.setPrefWidth(20);
			word.setStyle("-fx-background-color: black");
			hiddenWords.add(word);
		}
	}
	//Main Game Scene
	public void setGameScene() {	
		//main scene
		VBox gameMain = new VBox(10);
		scene = new Scene(gameMain, 500, 650);

		//title
		HBox title = new HBox();
		gameTitle = new Label("Hang Man!");
		gameTitle.setFont(Font.font("Century Gothic", 40));
		title.getChildren().add(gameTitle);
		title.setAlignment(Pos.CENTER);

		//prepare contents for main scene
		prepareContents();

		//Player info panel
		HBox playerinfo = new HBox(200);
		Label playerNameL = new Label(playerName);
		playerNameL.setFont(font);
		Label playerScoreL = new Label(players.get(playerName) + "");
		playerScoreL.setFont(font);
		playerinfo.getChildren().addAll(playerNameL, playerScoreL);
		playerinfo.setAlignment(Pos.CENTER);

		//Create the alphabet pad for guess
		TilePane alphaPad = new TilePane();
		alphaPad.getChildren().addAll(alphabetButtons);
		for(Node child : alphaPad.getChildren()) {
			alphaPad.setMargin(child, new Insets(10, 10, 10, 10));
		}

		//Hint panel
		HBox hintPanel = new HBox();
		Label hintL = new Label("Hint: " + hint);
		hintL.setFont(font);
		hintPanel.setAlignment(Pos.CENTER);
		hintPanel.getChildren().add(hintL);

		//Create the guess panel
		HBox guessPanel = new HBox(20);
		guessPanel.getChildren().addAll(hiddenWords);
		guessPanel.setAlignment(Pos.CENTER);

		//Describe trials left
		countLabel = new Label("Trials Left: \n      " + count + ""); 
		countLabel.setFont(Font.font(35));
		countLabel.setStyle("-fx-text-fill: red");
		HBox trialsPanel = new HBox();
		trialsPanel.getChildren().add(countLabel);
		trialsPanel.setAlignment(Pos.CENTER);


		gameMain.getChildren().addAll(title, playerinfo, alphaPad, hintPanel, guessPanel, trialsPanel);
		for(Node child : gameMain.getChildren()) {
			gameMain.setMargin(child, new Insets(10, 10, 10, 10));
		}

		stage.setScene(scene);
		
		//get key press from the scene for alphabets rather than clicking buttons one at a time
		scene.setOnKeyPressed(e1-> {
			String key = e1.getCode().getName();
			for(Button button : alphabetButtons) {
				if(button.getText().equals(key) && !button.isDisable()) {
					button.setDisable(true);
					guess(button.getText());
				}
			}
		});
	}
	/**
	 * @param gameWord the gameWord to set
	 */
	public void setGameWord(String gameWord) {
		this.gameWord = gameWord;
	}
	/**
	 * @param hint the hint to set
	 */
	public void setHint(String hint) {
		this.hint = hint;
	}
}
