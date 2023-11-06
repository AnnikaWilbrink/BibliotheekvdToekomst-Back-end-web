package nl.workingtalent.wtlibrary.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.workingtalent.wtlibrary.model.Book;
import nl.workingtalent.wtlibrary.model.BookCopy;
import nl.workingtalent.wtlibrary.repository.IBookCopyRepository;

@Service
public class BookCopyService {
	
	@Autowired
	private IBookCopyRepository repository;
	
	public List<BookCopy> findAll(){
		return repository.findAll();
	}
	
	public List<BookCopy> findByBook(Book book) {
		return repository.findByBook(book);
	}
	
	public void createNewCopy(BookCopy bookCopy, Book book) {
		Long maxCopyNumber = repository.findMaxCopyNumberByBook(book);
        bookCopy.setCopyNumber((maxCopyNumber != null) ? maxCopyNumber + 1 : 1);
		repository.save(bookCopy);
	}
	
	public Optional<BookCopy> findById(long id){
		return repository.findById(id);
	}
	
	public void deleteById(long id) {
		repository.deleteById(id);
	}
	
	public void update(BookCopy bookCopy) {
		repository.save(bookCopy);
	}

	public List<BookCopy> findAvailableCopies(Book book){
		return repository.findByAvailableAndBook(true, book);
	}
	
	public boolean archiveBookCopy(Long bookCopyId) {
	    Optional<BookCopy> bookCopyOptional = repository.findById(bookCopyId);
	    if (bookCopyOptional.isPresent()) {
	        BookCopy bookCopy = bookCopyOptional.get();
	        bookCopy.setArchived(true);
	        bookCopy.setAvailable(false);
	        repository.save(bookCopy);
	        return true;
	    }
	    return false;
	}
    
	public boolean unarchiveBookCopy(Long bookCopyId) {
	    Optional<BookCopy> bookCopyOptional = repository.findById(bookCopyId);
	    if (bookCopyOptional.isPresent()) {
	        BookCopy bookCopy = bookCopyOptional.get();
	        bookCopy.setArchived(false);
	        bookCopy.setAvailable(true);
	        repository.save(bookCopy);
	        return true;
	    }
	    return false;
	}
	
	
	
	
	
	
}
