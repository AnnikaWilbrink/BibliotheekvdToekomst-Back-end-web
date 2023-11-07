package nl.workingtalent.wtlibrary.dto;

import java.time.LocalDateTime;

public class InvitationTokenDto {

    private String token;

    private String role;
    
    // when not used, the localdatetime is NULL,
    // when used, the localdatetime is updated to that moment
    // hence, no boolean for used needed
	private LocalDateTime dateUsed;
    
    // the tokens have an expirationDate,
    // when signing up, must be checked if expirationDate has already passed
	private LocalDateTime expirationDate;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public LocalDateTime getDateUsed() {
		return dateUsed;
	}

	public void setDateUsed(LocalDateTime dateUsed) {
		this.dateUsed = dateUsed;
	}

	public LocalDateTime getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(LocalDateTime expirationDate) {
		this.expirationDate = expirationDate;
	}
	
}
