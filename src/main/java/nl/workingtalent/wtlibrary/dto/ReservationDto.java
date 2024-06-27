package nl.workingtalent.wtlibrary.dto;

import java.time.LocalDate;

public class ReservationDto {

    private long id;
    
    private LocalDate reservationDate;

	private boolean approved;
	
	private boolean deleted;

	private boolean borrowed;

    private long userId;

	private String UserFirstName;

	private String UserLastName;

	private String BookTitle;

	private long BookId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public LocalDate getReservationDate() {
		return reservationDate;
	}

	public void setReservationDate(LocalDate reservationDate) {
		this.reservationDate = reservationDate;
	}

    public String getUserFirstName() {
        return UserFirstName;
    }

    public void setUserFirstName(String UserFirstName) {
        this.UserFirstName = UserFirstName;
    }

    public String getUserLastName() {
        return UserLastName;
    }

    public void setUserLastName(String UserLastName) {
        this.UserLastName = UserLastName;
    }

    public String getBookTitle() {
        return BookTitle;
    }

    public void setBookTitle(String BookTitle) {
        this.BookTitle = BookTitle;
    }

    public boolean isBorrowed() {
        return borrowed;
    }

    public void setBorrowed(boolean borrowed) {
        this.borrowed = borrowed;
    }

    public long getBookId() {
        return BookId;
    }

    public void setBookId(long BookId) {
        this.BookId = BookId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

}
