package nl.workingtalent.wtlibrary.service;

import java.util.List;  
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import nl.workingtalent.wtlibrary.model.BorrowedBook;
import nl.workingtalent.wtlibrary.repository.IBorrowedBookRepository;


@Service
public class BorrowedBookService {
	
	@Autowired
	private  IBorrowedBookRepository repository;

	public List<BorrowedBook> findAll(){
		return repository.findAll();
	}
	
	public void save(BorrowedBook BorrowBookHistory) {
		repository.save(BorrowBookHistory);
	}
	
	public Optional<BorrowedBook> findById(@PathVariable long id){
		return repository.findById(id);
	}
	
	public void deleteById(long id) {
		repository.deleteById(id);
	}
	
	public void update(BorrowedBook BorrowBookHistory) {
		repository.save(BorrowBookHistory);
	}
	
	// TODO: verander deze functie
//	public Optional<BookCopy> availableCopy(Book book) {
//
//		List<BookCopy> bookCopies = book.getBookcopies();
//
//		// Check of dit boek exemplaren heeft
//		if (bookCopies.isEmpty()) {
//			return Optional.empty();
//		}
//		// Zoek een exemplaar dat beschikbaar is
//		for (BookCopy bookCopy : bookCopies) {
//			if (bookCopy.isAvailable()) {
//				return Optional.of(bookCopy);
//			}
//		}
//		return Optional.empty();
//	}

}
