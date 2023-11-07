package nl.workingtalent.wtlibrary.dto;

public class SaveUserTokenDto {
	
	private String firstName;
    private String lastName;
    private String password;
    private String invitationToken;
    private String email;
    private String phoneNumber;
    
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getInvitationToken() {
		return invitationToken;
	}
	public void setInvitationToken(String invitationToken) {
		this.invitationToken = invitationToken;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
    

}
