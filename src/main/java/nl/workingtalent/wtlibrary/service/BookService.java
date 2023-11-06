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
	
	public List<Book> findAllUnarchived() {
        List<Book> allBooks = repository.findAll();
        List<Book> nonArchivedBooks = new ArrayList<>();

        for (Book book : allBooks) {
            if (!book.isArchived()) {
                nonArchivedBooks.add(book);
            }
        }

        return nonArchivedBooks;
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
	
	public List<Book> filter(String filterWord, List<String> isCategory, List<String> hasSubject, Integer minReviewScore) {
		return searchRepository.search(filterWord, isCategory, hasSubject, minReviewScore);
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
