package nl.workingtalent.wtlibrary.dto;

public class BookCopyDto {

    private long id;
    
    private boolean archived;
    
    private boolean available;
    
    private long copyNumber;
    
    private String archivedDescription;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public boolean isArchived() {
		return archived;
	}

	public void setArchived(boolean archived) {
		this.archived = archived;
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
    
    public String getArchivedDescription() {
		return archivedDescription;
	}

	public void setArchivedDescription(String archivedDescription) {
		this.archivedDescription = archivedDescription;
	}

}
