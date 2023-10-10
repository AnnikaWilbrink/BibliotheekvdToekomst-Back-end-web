package nl.workingtalent.wtlibrary.dto;

import java.time.LocalDate;

public class SaveReservationDto {
    
    private LocalDate reservationDate;

	private String reservationStatus;

	public LocalDate getReservationDate() {
		return reservationDate;
	}

	public void setReservationDate(LocalDate reservationDate) {
		this.reservationDate = reservationDate;
	}

    public String getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(String reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

}

