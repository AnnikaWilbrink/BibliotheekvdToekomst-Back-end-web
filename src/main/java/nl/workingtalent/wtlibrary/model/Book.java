package nl.workingtalent.wtlibrary.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(length = 100, nullable = false)
    private String title;
    
    @Column(length = 100, nullable = false)
    private String author;
    
    @Column(nullable = true, length = 50)
    private String isbn;
    
    @Column(length = 50, nullable = false)
    private String subject;
    
    @Column(length = 50, nullable = false)
    private String category;
    
    @Column(length = 300, nullable = true)
    private String summary;
    
    @Column(length = 50, nullable = false)
    private String edition;
    
    @Column(length = 300, nullable = false)
    private String coverUrl;
    
//    @Column(length = 50, nullable = false)
//    private String availablity;
    
    @OneToMany(mappedBy = "book")
	private List<Reservation> reservations;
    
    @OneToMany(orphanRemoval = true, mappedBy = "book")
    private List<Review> reviews;

	@OneToMany(mappedBy = "book")
	private List<BookCopy> bookcopies;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getEdition() {
		return edition;
	}

	public void setEdition(String edition) {
		this.edition = edition;
	}

	public String getCoverUrl() {
		return coverUrl;
	}

	public void setCoverUrl(String coverUrl) {
		this.coverUrl = coverUrl;
	}

//	public String getAvailablity() {
//		return availablity;
//	}
//
//	public void setAvailablity(String availablity) {
//		this.availablity = availablity;
//	}
	
	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}
	
	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

    public List<BookCopy> getBookcopies() {
        return bookcopies;
    }

    public void setBookcopies(List<BookCopy> bookcopies) {
        this.bookcopies = bookcopies;
    }

}
