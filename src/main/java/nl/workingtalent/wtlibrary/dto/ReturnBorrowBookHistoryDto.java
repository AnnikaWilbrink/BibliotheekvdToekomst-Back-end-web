package nl.workingtalent.wtlibrary.dto;

import java.time.LocalDate;

// Deze dto is voor wanneer de user het boek inlevert
public class ReturnBorrowBookHistoryDto {

	// TODO: which id is this?
	// moet het niet dit zijn:
	// private long barrowBookHistoryId;
	private long id;
	
	// TODO: deze date kan ook gemaakt worden in java, ipv meegegeven
	private LocalDate returnedDate;
	
	// TODO: getters and setters
}
