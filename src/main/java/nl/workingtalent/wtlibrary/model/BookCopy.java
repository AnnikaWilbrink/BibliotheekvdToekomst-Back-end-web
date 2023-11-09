package nl.workingtalent.wtlibrary.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class BookCopy {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column
    private boolean available;
    
    @Column
    private long copyNumber;
    
    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean archived;
    
    @Column(length = 100, nullable = false)
    private String archivedDescription = "";

	@ManyToOne
	private Book book;

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

    public boolean isArchived() {
		return archived;
	}

	public void setArchived(boolean archived) {
		this.archived = archived;
	}

	public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
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
