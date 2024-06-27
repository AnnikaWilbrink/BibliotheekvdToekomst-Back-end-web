package nl.workingtalent.wtlibrary.dto;

import java.time.LocalDateTime;

public class BorrowedBookTableDto {

	private long id;
	private String userFirstName;
	private String userLastName;
	private String bookTitle;
	private long bookCopyId;
	private LocalDateTime borrowDate;
	private LocalDateTime returnedDate; // set to null when still in possession
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUserFirstName() {
		return userFirstName;
	}
	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}
	public String getUserLastName() {
		return userLastName;
	}
	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}
	public String getBookTitle() {
		return bookTitle;
	}
	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}
	public long getBookCopyId() {
		return bookCopyId;
	}
	public void setBookCopyId(long bookCopyId) {
		this.bookCopyId = bookCopyId;
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
