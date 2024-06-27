package nl.workingtalent.wtlibrary.dto;

public class BookCopyArchiveDto {
	
	private boolean archived;
	
    private boolean available;
    
    private String archivedDescription;

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

	public String getArchivedDescription() {
		return archivedDescription;
	}

	public void setArchivedDescription(String archivedDescription) {
		this.archivedDescription = archivedDescription;
	}

}
