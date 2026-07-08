/**
 * handles registration, login and user updates
 */
package database;

import main.*;
import ui.*;
import game.*;
import entities.*;
import database.*;
import audio.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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

		newUser.setSoundsSetting(new SoundManager()); 

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

					boolean bg = Boolean.parseBoolean(tokens[4]);
					boolean shot = Boolean.parseBoolean(tokens[5]);
					boolean crash = Boolean.parseBoolean(tokens[6]);
					boolean end = Boolean.parseBoolean(tokens[7]);
					
					SoundManager userSounds = new SoundManager();
					userSounds.bgMusic = bg;
					userSounds.shotSound = shot;
					userSounds.crashSound = crash;
					userSounds.endSound = end;
					newUser.setSoundsSetting(userSounds);

					return newUser;
				}
			}
			return null;

		}
		catch(FileNotFoundException e) {
			return null;
		}
	}
	
	public boolean doesTheUserExist(String username) {
		File usersFile = new File(usersFileName);
		try(Scanner scn = new Scanner(usersFile)){
			while(scn.hasNextLine()) {
				String[] parts = scn.nextLine().split(",");
				if(parts[0].equals(username)) {
					return true;
				}
			}
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}

	public void updateUser(User user){
		File usersFile = new File(usersFileName);
		ArrayList<String> lines = new ArrayList<>();
		boolean userFound = false;
		try(Scanner scn = new Scanner(usersFile)){
			while(scn.hasNextLine()){
				String line = scn.nextLine();
				String[] tokens = line.split(",");

				if(tokens[0].equals(user.getUsername())){
					userFound = true;
					SoundManager sm = user.getSoundsSetting();
					String bg = String.valueOf(sm.bgMusic);
					String shot = String.valueOf(sm.shotSound);
					String crash = String.valueOf(sm.crashSound);
					String end = String.valueOf(sm.endSound);

					String updatedLine = user.getUsername() + "," + user.getPassword() + "," + user.getHighestScore() + "," + user.getLastReachedLevel() + "," + bg + "," + shot + "," + crash + "," + end;
					lines.add(updatedLine);
				}
				else{
					lines.add(line);
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}

		try(BufferedWriter bw = new BufferedWriter(new FileWriter(usersFileName))){
			for(String line : lines){
				bw.write(line);
				bw.newLine();
			}
		}catch(IOException e){
				e.printStackTrace();
		}

	}

	public ArrayList<User> getTopScore(){
		ArrayList<User> users = new ArrayList<>();
		try(Scanner scn = new Scanner(new File(usersFileName))){
			while(scn.hasNextLine()){
				String[] tokens = scn.nextLine().split(",");
				User u = new User(tokens[0], tokens[1]);
				u.setHighestScore(Integer.parseInt(tokens[2]));
				users.add(u);
			}
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}

		for(int i=0; i<users.size() ; i++){
			for(int j = i+1; j< users.size(); j++){
				if(users.get(i).getHighestScore() < users.get(j).getHighestScore()){
					User temp = users.get(i);
					users.set(i, users.get(j));
					users.set(j, temp);
				}
			}
		}

		return users;
	}

	public void saveGameHistory(String username, int score, int level, SoundManager soundSettings){
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(historyFileName, true))){
			String date = java.time.LocalDateTime.now().toString();

			String bg;
			if (soundSettings != null) {
				bg = String.valueOf(soundSettings.bgMusic);
			} else {
				bg = "true";
			}

			String shot;
			if(soundSettings != null){
				shot = String.valueOf(soundSettings.shotSound);
			}else{
				shot = "true";
			}

			String crash;
			if(soundSettings != null){
				crash = String.valueOf(soundSettings.crashSound);
			}else{
				crash = "true";
			}

			String end;
			if(soundSettings != null){
				end = String.valueOf(soundSettings.endSound);
			}else{
				end = "true";
			}

			String line = username + "," + score + "," + level + "," + date + "," + bg + "," + shot + "," + crash + "," + end;

			bw.write(line);
			bw.newLine();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
