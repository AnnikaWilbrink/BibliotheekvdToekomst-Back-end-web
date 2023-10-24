package nl.workingtalent.wtlibrary.dto;
import java.util.List;
import java.util.ArrayList;

import nl.workingtalent.wtlibrary.model.BookCopy;

public class BookAvailabilityDto {
	
	private int numberOfCopies;
	
	private int numberOfAvailableCopies;

	private List<BookCopy> availableCopies;

	private ArrayList<Long> available;

	private ArrayList<Long> availableIds;

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

    public List<BookCopy> getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(List<BookCopy> availableCopies) {
        this.availableCopies = availableCopies;
    }

	public ArrayList<Long> getAvailable() {
        return available;
    }

    public void setAvailable(ArrayList<Long> available) {
        this.available = available;
    }

    public ArrayList<Long> getAvailableIds() {
        return availableIds;
    }
	
    public void setAvailableIds(ArrayList<Long> availableIds) {
        this.availableIds = availableIds;
    }

}
