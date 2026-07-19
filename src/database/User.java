/**
 * model for a registered player
 * stores credentials, high scores and level, and audio settings
 */
package database;

import main.*;
import ui.*;
import game.*;
import entities.*;
import database.*;
import audio.*;


public class User {
	private String username;
	private String password;
	private int highestScore;
	private SoundManager soundsSetting;
	private int lastReachedLevel;
	private String type;

	public User(String username, String password) {
		this.username = username;
		this.password = password;
		this.highestScore = 0;
		this.lastReachedLevel = 0;
		this.soundsSetting = null;
		this.type = "Default";
	}

	public void setUsername(String newUsername) {
		this.username = newUsername;
	}
	public String getUsername() {
		return this.username;
	}

	public void setPassword(String newPassword) {
		this.password = newPassword;
	}
	public String getPassword() {
		return this.password;
	}

	public void setHighestScore(int newHighestScore) {
		this.highestScore = newHighestScore;
	}
	public int getHighestScore() {
		return this.highestScore;
	}

	public void setSoundsSetting(SoundManager newSoundsSetting) {
		this.soundsSetting = newSoundsSetting;
	}
	public SoundManager getSoundsSetting() {
		return this.soundsSetting;
	}

	public void setLastReachedLevel(int newLastReachedLevel) {
		this.lastReachedLevel = newLastReachedLevel;
	}
	public int getLastReachedLevel() {
		return this.lastReachedLevel;
	}

	public void setType(String type){
		this.type = type;
	}
	public String getType(){
		return this.type;
	}

}
