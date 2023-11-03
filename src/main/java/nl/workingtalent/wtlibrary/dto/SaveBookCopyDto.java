package nl.workingtalent.wtlibrary.dto;

public class SaveBookCopyDto {
    
    private boolean available;
    
    private int copyNumber;
    
    private long book;

	public int getCopyNumber() {
		return copyNumber;
	}

	public void setCopyNumber(int copyNumber) {
		this.copyNumber = copyNumber;
	}

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

	public long getBook() {
		return book;
	}

	public void setBook(long book) {
		this.book = book;
	}
    
    

}
