package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.TreeMap;

//the class works as data manager between GUI and Data file
public class Controller {
	private TreeMap<String, Long> players;
	private String playerName;
	private URL url;

	public Controller(String playerName) throws MalformedURLException {
		this.url = this.getClass().getResource("C:\\Users\\shane\\Documents\\Git\\HangMan\\PlayersData.txt");
		this.playerName = playerName;
		inputPlayersData();
		
		if(!players.containsKey(playerName))
			players.put(playerName, 0L);
	}
	
	//returns a current player
	public String getPlayerName() {
		return playerName;
	}

	//returns player's score
	public long getplayerScore() {
		return players.get(getPlayerName());
	}

	//the method returns the player database
	public TreeMap<String, Long> getPlayersData() {
		return this.players;
	}
	

	@SuppressWarnings("unchecked")
	//the method reads the players data from the file
	public void inputPlayersData() {
		File file = new File("C:\\Users\\shane\\Documents\\Git\\HangMan\\PlayersData.txt");

		try {
			FileInputStream fileInput = new FileInputStream(file);

			//To check if there is no EOFException
			if(fileInput.read() != -1) {
				ObjectInputStream objInput = new ObjectInputStream(new FileInputStream(file));
				players = (TreeMap<String, Long>) objInput.readObject();
				fileInput.close();
				objInput.close();
				return;
			}

			players = new TreeMap<String, Long>();

		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	//the method outputs the players data by serialization
	public void writePlayersData() {
		File file = new File("C:\\Users\\shane\\Documents\\Git\\HangMan\\PlayersData.txt");

		try {
			FileOutputStream outputFile = new FileOutputStream(file);
			ObjectOutputStream objOut = new ObjectOutputStream(outputFile);

			objOut.writeObject(players);
			objOut.flush();

			objOut.close();
			outputFile.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
