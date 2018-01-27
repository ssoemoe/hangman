package main;

import javafx.application.Application;
import javafx.stage.Stage;
import main.WelcomeGUI;
//Main class for program
public class Main extends Application {
	private WelcomeGUI welcome;
	public static void main(String[] args) {
		launch(args);
	}

	//the method starts the app
	@Override
	public void start(Stage primaryStage) throws Exception {
		welcome = new WelcomeGUI(primaryStage);
		welcome.setWelcomeScene();
		primaryStage.setTitle("Hang Man");
		primaryStage.show();
	}
}
