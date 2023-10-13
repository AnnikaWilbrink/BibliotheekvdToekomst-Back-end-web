package nl.workingtalent.wtlibrary.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private LocalDate reservationDate;

	@Column
	private String reservationStatus;

	@ManyToOne
	private User user;

	@ManyToOne
	private BookCopy bookCopy;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalDate getReservationDate() {
		return reservationDate;
	}

	public void setReservationDate(LocalDate reservationDate) {
		this.reservationDate = reservationDate;
	}

	public void setReservationStatus(String reservationStatus){
		this.reservationStatus = reservationStatus;
	}

	public String getReservationStatus(){
		return reservationStatus;
	}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BookCopy getBookCopy() {
        return bookCopy;
    }

    public void setBookcopy(BookCopy bookCopy) {
        this.bookCopy = bookCopy;
    }

}
