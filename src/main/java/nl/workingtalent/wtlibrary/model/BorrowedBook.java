package nl.workingtalent.wtlibrary.model;
import java.time.LocalDateTime;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class BorrowedBook {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

	@ManyToOne(optional = false)
	private User user;
	
	@ManyToOne(optional = false)
	private BookCopy bookCopy;
	
	@Column(nullable = false)
	private LocalDateTime borrowDate;
	
	// when the book is returned, this is updated,
	// hence, no possession variable is necessary
	@Column(nullable = true)
	private LocalDateTime returnedDate;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public BookCopy getBookCopy() {
		return bookCopy;
	}

	public void setBookCopy(BookCopy bookCopy) {
		this.bookCopy = bookCopy;
	}

	public LocalDateTime getBorrowDate() {
		return borrowDate;
	}

	public void setBorrowDate(LocalDateTime borrowDate) {
		this.borrowDate = borrowDate;
	}

	public LocalDateTime getReturnedDate() {
		return returnedDate;
	}

	public void setReturnedDate(LocalDateTime returnedDate) {
		this.returnedDate = returnedDate;
	}
	
	
}
