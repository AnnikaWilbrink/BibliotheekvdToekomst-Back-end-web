package nl.workingtalent.wtlibrary.dto;

import java.time.LocalDate;

public class ReservationDto {

    private long id;
    
    private LocalDate reservationDate;


	private String reservationStatus;
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getReservationStatus(){
		return reservationStatus;
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

}
