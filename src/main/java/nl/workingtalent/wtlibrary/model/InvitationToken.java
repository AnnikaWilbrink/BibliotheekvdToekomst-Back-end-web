package nl.workingtalent.wtlibrary.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;

import java.util.List; 

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "InvitationToken")
public class InvitationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String token;

    @Column(nullable = false)
    private String role;
    
    // when not used, the localdatetime is NULL,
    // when used, the localdatetime is updated to that moment
    // hence, no boolean for used needed
    @Column(nullable = true)
	private LocalDateTime dateUsed;
    
    // the tokens have an expirationDate,
    // when signing up, must be checked if expirationDate has already passed
    @Column(nullable = false)
	private LocalDateTime expirationDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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