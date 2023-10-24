package nl.workingtalent.wtlibrary.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import nl.workingtalent.wtlibrary.model.Book;
import nl.workingtalent.wtlibrary.repository.BookSearchRepository;
import nl.workingtalent.wtlibrary.model.BookCopy;
import nl.workingtalent.wtlibrary.repository.IBookRepository;


@Service
public class BookService {

	@Autowired 
	private IBookRepository repository;
	
	@Autowired
	private BookSearchRepository searchRepository;
	
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
	
	public List<Book> filter(String filterWord, List<String> categories, Integer minReviewScore) {
		return searchRepository.search(filterWord, categories, minReviewScore);
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
}
