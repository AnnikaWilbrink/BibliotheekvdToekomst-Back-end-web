package nl.workingtalent.wtlibrary.dto;

public class BookCopyDto {

    private long id;
    
    private boolean available;
    
    private long copyNumber;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCopyNumber() {
		return copyNumber;
	}

	public void setCopyNumber(long copyNumber) {
		this.copyNumber = copyNumber;
	}

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

}
