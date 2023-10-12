package nl.workingtalent.wtlibrary.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
	@Column(length = 100, nullable = false)
    private String firstName;
    
    @Column(length = 100, nullable = false)
    private String lastName;
    
    @Column(length = 100, nullable = false)
    private String password;
    
    @Column(length = 50, nullable = false)
    private String role;
    
    @Column(length = 100, nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String phoneNumber;
    
    @Column()
    private LocalDateTime registrationDate = LocalDateTime.now();
    
    @Column
    private LocalDateTime lastUpdatedDate;
    
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Review> reviews;

	@OneToMany(mappedBy = "user")
	private List<Reservation> reservations;
    
    @Column(length = 100, unique = true)
    private String token;
    
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
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

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}
	
    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }
    
    public void setlastUpdatedDate() {
        this.lastUpdatedDate = LocalDateTime.now();
    }
    
    public LocalDateTime getlastUpdatedDate() {
        return registrationDate;
    }
    
    public String getToken() {
		return token;
	}
    
    public void setToken(String token) {
		this.token = token;
	}
    
    public String getFullName() {
    	return this.firstName + " " + this.lastName;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public LocalDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(LocalDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

}
