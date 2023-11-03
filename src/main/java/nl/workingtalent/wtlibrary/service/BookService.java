package nl.workingtalent.wtlibrary.service;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import nl.workingtalent.wtlibrary.model.Book;
import nl.workingtalent.wtlibrary.repository.BookSearchRepository;
import nl.workingtalent.wtlibrary.model.BookCopy;
import nl.workingtalent.wtlibrary.model.Review;
import nl.workingtalent.wtlibrary.repository.IReviewRepository;
import nl.workingtalent.wtlibrary.repository.IBookRepository;


@Service
public class BookService {

	@Autowired 
	private IBookRepository repository;
	
	@Autowired
	private BookSearchRepository searchRepository;

	@Autowired
	private IReviewRepository reviewRepository;
	
	public List<Book> findAll(){
		return repository.findAll();
	}
	
	public void save(Book book) {
		repository.save(book);
	}
	
	public Optional<Book> findById(@PathVariable long id){
		return repository.findById(id);
	}
	
	public void delete(long id) {
		repository.deleteById(id);
	}
	
	public void update(Book book) {
		repository.save(book);
	}
	
	public List<Book> filter(String filterWord, List<String> isCategory, List<String> hasSubject, Integer minReviewScore, String sortField, String sortOrder) {
		return searchRepository.search(filterWord, isCategory, hasSubject, minReviewScore, sortField, sortOrder);
	}

	public Double calculateAverageRating(Book book){
		List<Review> reviews = reviewRepository.findByBook(book);

		double totalRating = 0.0;
		
		for (Review review : reviews) {
			totalRating += review.getStars(); // Assuming stars is a field or method that returns the star rating value
		}

		if (reviews.isEmpty()) {
			return 0.0; // Return 0 if there are no reviews
		} else {
			return totalRating / reviews.size();
		}
		}

	public int findNrOfCopies(Book book) {
		List<BookCopy> bookCopies = book.getBookcopies();
		
		return bookCopies.size();
	}
	
	public int findNrOfAvailableCopies(Book book) {
		List<BookCopy> bookCopies = book.getBookcopies();
		int i=0;
		for (BookCopy bookCopy : bookCopies) {
			if (bookCopy.isAvailable()) {
				i++;
			}
		}
		
		return i;
	}
	
    public boolean archiveBook(Long bookId) {
        Optional<Book> bookOptional = repository.findById(bookId);
        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            book.setArchived(true);
            repository.save(book);
            return true;
        }
        return false;
    }
    
    public boolean unarchiveBook(Long bookId) {
        Optional<Book> bookOptional = repository.findById(bookId);
        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            book.setArchived(false);
            repository.save(book);
            return true;
        }
        return false;
    }
}
