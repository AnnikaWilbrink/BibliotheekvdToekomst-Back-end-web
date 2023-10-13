package nl.workingtalent.wtlibrary.dto;

public class UserChangeEmailDto {

	//private String token;
	private String password;
	private String currentEmail;
	private String newEmail;
	
//	public String getToken() {
//		return token;
//	}
//	public void setToken(String token) {
//		this.token = token;
//	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCurrentEmail() {
		return currentEmail;
	}
	public void setCurrentEmail(String currentEmail) {
		this.currentEmail = currentEmail;
	}
	public String getNewEmail() {
		return newEmail;
	}
	public void setNewEmail(String newEmail) {
		this.newEmail = newEmail;
	}
	
}
