package nl.workingtalent.wtlibrary.model;

import java.util.List;

import org.hibernate.annotations.ColumnDefault;

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
    
    @Column(length = 1000, nullable = true)
    private String summary;
    
    @Column(length = 50, nullable = false)
    private String edition;
    
    @Column(length = 300, nullable = false)
    private String coverUrl;
    
    @Column(nullable = false, columnDefinition = "varchar(50) default 'available'")
    private String availability = "available";

//	@Column(nullable = true)
//	private Double avgRating;
    
    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean archived;
    
    @OneToMany(mappedBy = "book")
	private List<Reservation> reservations;
    
    @OneToMany(mappedBy = "book")
    private List<Favorite> favorite;
    
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

	public String getAvailability() {
		return availability;
	}

	public void setAvailability(String availability) {
		this.availability = availability;
	}

//	public Double getAvgRating() {
//		return avgRating;
//	}
//
//	public void setAvgRating(Double avgRating) {
//		this.avgRating = avgRating;
//	}

	public boolean isArchived() {
		return archived;
	}

	public void setArchived(boolean archived) {
		this.archived = archived;
	}

	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}

	public List<Favorite> getFavorite() {
		return favorite;
	}

	public void setFavorite(List<Favorite> favorite) {
		this.favorite = favorite;
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
