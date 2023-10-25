package nl.workingtalent.wtlibrary.service;

import java.util.ArrayList;
import java.util.List;  
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import nl.workingtalent.wtlibrary.model.BookCopy;
import nl.workingtalent.wtlibrary.dto.BorrowedBookTableDto;
import nl.workingtalent.wtlibrary.model.Book;
import nl.workingtalent.wtlibrary.model.BorrowedBook;
import nl.workingtalent.wtlibrary.model.User;
import nl.workingtalent.wtlibrary.repository.IBorrowedBookRepository;


@Service
public class BorrowedBookService {
	
	@Autowired
	private  IBorrowedBookRepository repository;
	
	@Autowired
	private UserService userService;

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
	
	
	public Optional<BookCopy> availableCopy(Book book) {
		List<BookCopy> bookCopies = book.getBookcopies();

		// Check of dit boek exemplaren heeft
		if (bookCopies.isEmpty()) {
			return Optional.empty();
		}

		// Zoek een exemplaar dat beschikbaar is
		for (BookCopy bookCopy : bookCopies) {
			if (bookCopy.isAvailable()) {
				return Optional.of(bookCopy);
			}
		}
		return Optional.empty();
	}
	
	public List<BorrowedBookTableDto> findDtosByUser(User user) {
	    if (user == null) {
	        return new ArrayList<>();
	    }
	    List<BorrowedBook> borrowedBooks = repository.findByUser(user);
	    return borrowedBooks.stream().map(this::convertToDto).collect(Collectors.toList());
	}
	
	private BorrowedBookTableDto convertToDto(BorrowedBook entity) {
	    BorrowedBookTableDto dto = new BorrowedBookTableDto();

	    dto.setId(entity.getId());
	    dto.setUserFirstName(entity.getUser().getFirstName()); // Assuming User has a getFirstName method
	    dto.setUserLastName(entity.getUser().getLastName());   // Assuming User has a getLastName method
	    dto.setBookTitle(entity.getBookCopy().getBook().getTitle()); // Assuming BookCopy has a getBook method and Book has a getTitle method
	    dto.setBookCopyId(entity.getBookCopy().getId());
	    dto.setBorrowDate(entity.getBorrowDate());
	    dto.setReturnedDate(entity.getReturnedDate());

	    return dto;
	}
}
