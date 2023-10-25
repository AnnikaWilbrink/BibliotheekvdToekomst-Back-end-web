package nl.workingtalent.wtlibrary.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import nl.workingtalent.wtlibrary.dto.BookCopyDto;
import nl.workingtalent.wtlibrary.dto.SaveBookCopyDto;
import nl.workingtalent.wtlibrary.model.Book;
import nl.workingtalent.wtlibrary.model.BookCopy;
import nl.workingtalent.wtlibrary.service.BookCopyService;
import nl.workingtalent.wtlibrary.service.BookService;

@RestController
@CrossOrigin(maxAge=3600)
public class BookCopyController {
	@Autowired
	private BookCopyService service;
	
	@Autowired
	private BookService bookService;
	
	@RequestMapping("bookcopy/all")
	public List<BookCopyDto> findAllBookCopy() {
	    Iterable<BookCopy> copies = service.findAll();
	    List<BookCopyDto> dtos = new ArrayList<>();
	    
	    copies.forEach(copy -> {
	        BookCopyDto dto = new BookCopyDto();
	        dto.setId(copy.getId());
	        dto.setAvailable(copy.isAvailable());
	        dto.setCopyNumber(copy.getCopyNumber());
	        
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
        	
        	dtos.add(dto);
        });
        
        return dtos;
	}
	
	@RequestMapping(method = RequestMethod.POST, value="bookcopy/save")
	public boolean save(@RequestBody SaveBookCopyDto dto) {
	    BookCopy bookCopy = new BookCopy();
	    bookCopy.setAvailable(dto.isAvailable());
	    bookCopy.setCopyNumber(dto.getCopyNumber());
	    
	    service.save(bookCopy);
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
	    // existingBookCopy.setCopyNumber(dto.getCopyNumber());
	    
	    service.update(existingBookCopy);
	    return true;
	}

}
