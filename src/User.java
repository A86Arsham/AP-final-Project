
public class User {
	private String username;
	private String password;
	private int highestScore;
	private SoundManager soundsSetting;
	private int lastReachedLevel;

	public User(String username, String password) {
		this.username = username;
		this.password = password;
		this.highestScore = 0;
		this.lastReachedLevel = 0;
		this.soundsSetting = null;
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

	public boolean doesUserExists(String username) {
		if(this.username.equals(username)) {
			return true;
		}
		return false;
	}
}
