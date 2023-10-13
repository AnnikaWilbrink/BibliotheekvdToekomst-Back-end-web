package nl.workingtalent.wtlibrary.dto;

import nl.workingtalent.wtlibrary.model.Book;
import nl.workingtalent.wtlibrary.model.User;

public class FavoriteDto {
	
	private long id;

	private User user;
	
    private Book book;

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

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	

    
}
