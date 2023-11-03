package nl.workingtalent.wtlibrary.dto;

public class BookCopyArchiveDto {
	
	private boolean archived;
	
    private boolean available;

	public boolean isArchived() {
		return archived;
	}

	public void setArchived(boolean archived) {
		this.archived = archived;
	}
	
	public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

}
