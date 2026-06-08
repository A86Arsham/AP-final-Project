import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class DatabaseManager {
		String usersFileName = "users.csv";
	    String historyFileName = "history.csv";

	public DatabaseManager(){

	    File usersFile = new File(usersFileName);
	    File historyFile = new File(historyFileName);

	    if(!usersFile.exists()) {
	    	try {
	    		usersFile.createNewFile();
	    	}catch(IOException e) {
                e.printStackTrace();
	    	}
	    }

	    if(!historyFile.exists()) {
	    	try {
	    		historyFile.createNewFile();
	    	}catch(IOException e) {
                e.printStackTrace();
	    	}
	    }
	}

	public User registerUser(String username, String password) {

		User newUser = new User(username, password);
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(usersFileName,true))){
			String[] user = {username, password, "0", "0", "true", "true", "true", "true"};
			bw.write(String.join(",", user));
            bw.newLine();

		}catch(IOException e){
			e.printStackTrace();
		}
		return newUser;
	}

	public User loginUser(String username, String password) {
		File usersFile = new File(usersFileName);
		try(Scanner scn = new Scanner(usersFile)){
			while(scn.hasNextLine()) {
				String line = scn.nextLine();
				String[] tokens = line.split(",");
				User newUser = new User(tokens[0],tokens[1]);

				if(username.equals(tokens[0]) && password.equals(tokens[1])) {
					newUser.setHighestScore(Integer.parseInt(tokens[2]));
					newUser.setLastReachedLevel(Integer.parseInt(tokens[3]));
					// user sound prefernces
					//
					//
					//
					//new SoundManager
					//set SoundManager to user
					return newUser;
				}
			}
			return null;

		}
		catch(FileNotFoundException e) {
			return null;
		}
	}
}
