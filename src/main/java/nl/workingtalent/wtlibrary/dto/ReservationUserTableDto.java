package nl.workingtalent.wtlibrary.dto;

import java.time.LocalDate;

public class ReservationUserTableDto {

	private String bookTitle;
    private LocalDate reservationDate;
    private boolean approved;
    
    
	public String getBookTitle() {
		return bookTitle;
	}
	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}
	public LocalDate getReservationDate() {
		return reservationDate;
	}
	public void setReservationDate(LocalDate reservationDate) {
		this.reservationDate = reservationDate;
	}
	public boolean isApproved() {
		return approved;
	}
	public void setApproved(boolean approved) {
		this.approved = approved;
	}
}
