package nl.workingtalent.wtlibrary.dto;

public class BookAvailabilityDto {
	
	private int numberOfCopies;
	
	private int numberOfAvailableCopies;

	public int getNumberOfCopies() {
		return numberOfCopies;
	}

	public void setNumberOfCopies(int numberOfCopies) {
		this.numberOfCopies = numberOfCopies;
	}

	public int getNumberOfAvailableCopies() {
		return numberOfAvailableCopies;
	}

	public void setNumberOfAvailableCopies(int numberOfAvailableCopies) {
		this.numberOfAvailableCopies = numberOfAvailableCopies;
	}
	
}
