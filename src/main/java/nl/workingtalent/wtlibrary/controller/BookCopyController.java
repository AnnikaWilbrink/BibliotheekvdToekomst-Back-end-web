package nl.workingtalent.wtlibrary.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import nl.workingtalent.wtlibrary.dto.BookCopyArchiveDto;
import nl.workingtalent.wtlibrary.dto.BookCopyDto;
import nl.workingtalent.wtlibrary.dto.SaveBookCopyDto;
import nl.workingtalent.wtlibrary.dto.SelectBookCopyDto;
import nl.workingtalent.wtlibrary.model.Book;
import nl.workingtalent.wtlibrary.model.BookCopy;
import nl.workingtalent.wtlibrary.model.Reservation;
import nl.workingtalent.wtlibrary.service.BookCopyService;
import nl.workingtalent.wtlibrary.service.BookService;
import nl.workingtalent.wtlibrary.service.ReservationService;

@RestController
@CrossOrigin(maxAge=3600)
public class BookCopyController {

	@Autowired
	private BookCopyService service;
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private ReservationService reservationService;
	
	
	@RequestMapping("bookcopy/all")
	public List<BookCopyDto> findAllBookCopy() {
	    Iterable<BookCopy> copies = service.findAll();
	    List<BookCopyDto> dtos = new ArrayList<>();
	    
	    copies.forEach(copy -> {
	        BookCopyDto dto = new BookCopyDto();
	        dto.setId(copy.getId());
	        dto.setAvailable(copy.isAvailable());
	        dto.setCopyNumber(copy.getCopyNumber());
	        dto.setArchived(copy.isArchived());
	        dtos.add(dto);
	    });
	    
	    return dtos;
	}
	
	@GetMapping("bookcopy/book/{id}")
	public List<BookCopyDto> findByBook(@PathVariable long id) {
		Optional<Book> bookOptional = bookService.findById(id);
        if (bookOptional.isEmpty()) {
            return Collections.emptyList(); // Return an empty list
        }
        Book book = bookOptional.get();
        
        List<BookCopyDto> dtos = new ArrayList<>();
        
        List<BookCopy> bookCopies = service.findByBook(book);
        
        bookCopies.forEach(bookCopy -> {
        	BookCopyDto dto = new BookCopyDto();
        	dto.setId(bookCopy.getId());
        	dto.setCopyNumber(bookCopy.getCopyNumber());
        	dto.setAvailable(bookCopy.isAvailable());
        	dto.setArchived(bookCopy.isArchived());
        	dto.setArchivedDescription(bookCopy.getArchivedDescription());
        	dtos.add(dto);
        });
        
        return dtos;
	}
	
	@PostMapping("bookcopy/save")
	public boolean save(@RequestBody SaveBookCopyDto dto) {
		Optional<Book> bookOptional = bookService.findById(dto.getBook());
		if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            
		    BookCopy bookCopy = new BookCopy();
		    bookCopy.setAvailable(dto.isAvailable());
		    bookCopy.setBook(book);
	    
		    service.createNewCopy(bookCopy, book);
		}
	    return true;
	}
	
	@RequestMapping("bookcopy/{id}")
	public Optional<BookCopy> findById(@PathVariable long id) {
		return service.findById(id);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value="bookcopy/{id}")
	public boolean update(@PathVariable long id, @RequestBody SaveBookCopyDto dto) {
	    Optional<BookCopy> optional = service.findById(id);
	    if(optional.isEmpty()) {
	        return false;
	    }
	    BookCopy existingBookCopy = optional.get();
	    
	    existingBookCopy.setAvailable(dto.isAvailable());
	    
	    service.update(existingBookCopy);
	    return true;
	}
	
	@PutMapping("bookcopy/{id}/select")
	public boolean select(@PathVariable long id, @RequestBody SelectBookCopyDto dto) {
	    Optional<BookCopy> optional = service.findById(id);
	    if(optional.isEmpty()) {
	        return false;
	    }
	    
	    Optional<Reservation> optionalReservation = reservationService.findById(dto.getReservationId());
	    if(optionalReservation.isEmpty()) {
	        return false;
	    }
	    
	    BookCopy existingBookCopy = optional.get();
	    Reservation existingReservation = optionalReservation.get();
	    
	    existingBookCopy.setAvailable(false);
	    service.update(existingBookCopy);

	    existingReservation.setBorrowed(true);
	    reservationService.update(existingReservation);

	    return true;
	}
	
	@PutMapping("bookcopy/archive/{id}")
	public BookCopyArchiveDto archiveBook(@PathVariable long id, @RequestBody BookCopyArchiveDto dto) {
		BookCopyArchiveDto archived = new BookCopyArchiveDto();
		boolean isArchived = service.archiveBookCopy(id, dto.getArchivedDescription());
		archived.setArchived(isArchived);
		archived.setAvailable(isArchived ? false : true);
	    archived.setArchivedDescription(dto.getArchivedDescription());
		return archived;

	}
	
	@PutMapping("bookcopy/unarchive/{id}")
	public BookCopyArchiveDto unarchiveBook(@PathVariable long id) {
		BookCopyArchiveDto archived = new BookCopyArchiveDto();
		boolean isArchived = service.unarchiveBookCopy(id);
		archived.setArchived(isArchived);
		archived.setAvailable(isArchived ? false : true);
		archived.setArchivedDescription("ha");
		return archived;

	}
	

}
