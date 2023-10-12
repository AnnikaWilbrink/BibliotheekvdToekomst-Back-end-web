package nl.workingtalent.wtlibrary.dto;

public class UserChangePhoneNumberDto {
	
	private String currentPhoneNumber;
	private String newPhoneNumber;
	private String password;
	
	public String getCurrentPhoneNumber() {
		return currentPhoneNumber;
	}
	public void setCurrentPhoneNumber(String currentPhoneNumber) {
		this.currentPhoneNumber = currentPhoneNumber;
	}
	public String getNewPhoneNumber() {
		return newPhoneNumber;
	}
	public void setNewPhoneNumber(String newPhoneNumber) {
		this.newPhoneNumber = newPhoneNumber;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
