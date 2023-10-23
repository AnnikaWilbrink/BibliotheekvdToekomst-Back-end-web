package nl.workingtalent.wtlibrary.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import nl.workingtalent.wtlibrary.dto.BookAvailabilityDto;
import nl.workingtalent.wtlibrary.dto.BookDto;
import nl.workingtalent.wtlibrary.dto.BookInfoDto;
import nl.workingtalent.wtlibrary.dto.SaveBookDto;
import nl.workingtalent.wtlibrary.dto.SearchBookDto;
import nl.workingtalent.wtlibrary.model.Book;
import nl.workingtalent.wtlibrary.service.BookService;

@RestController
@CrossOrigin(maxAge=3600)
public class BookController {
	
	@Autowired
	private BookService service;
	
	@RequestMapping("book/all")
	public List<BookDto> findAllBooks(){
		// list Model -> list Dto
		List<Book> dbBooks = service.findAll();
		List<BookDto> dtos = new ArrayList<>();

		dbBooks.forEach(dbBook -> {
			BookDto bookDto = new BookDto();
			bookDto.setId(dbBook.getId());
			bookDto.setTitle(dbBook.getTitle());
			bookDto.setIsbn(dbBook.getIsbn());
			bookDto.setAuthor(dbBook.getAuthor());
			bookDto.setSummary(dbBook.getSummary());
			bookDto.setCoverUrl(dbBook.getCoverUrl());
//			bookDto.setAvailablity(dbBook.getAvailablity());
			bookDto.setSubject(dbBook.getSubject());
			bookDto.setCategory(dbBook.getCategory());
			//bookDto.setReviews(dbBook.getReviews());

			dtos.add(bookDto);
		});
		
		return dtos;
	}
	
	@RequestMapping(method = RequestMethod.POST, value="book/save")
	public boolean save(@RequestBody SaveBookDto dto) {
		// Dto -> Model
		Book book = new Book();
		book.setTitle(dto.getTitle());
		book.setIsbn(dto.getIsbn());
		book.setAuthor(dto.getAuthor());
//		book.setAvailablity(dto.getAvailablity());
		book.setCategory(dto.getCategory());
		book.setEdition(dto.getEdition());
		book.setCoverUrl(dto.getCoverUrl());
		book.setEdition(dto.getEdition());
		book.setSubject(dto.getSubject());
		book.setSummary(dto.getSummary());
		
//		service.save(book);
//		return true;
//		
		try {
	        // Attempt to save the book
	        service.save(book);
	        return true;
	    } catch (DataIntegrityViolationException e) {
	        return false; 
	    }
	}
	
	@RequestMapping("book/{id}")
	public Optional<Book> findById(@PathVariable long id){
		return service.findById(id);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value="book/{id}")
	public boolean update(@PathVariable long id, @RequestBody SaveBookDto dto) {
	    Optional<Book> optional = service.findById(id);
	    if(optional.isEmpty()) {
	        return false;
	    }
	    Book existingBook = optional.get();

	    existingBook.setTitle(dto.getTitle());
	    existingBook.setIsbn(dto.getIsbn());
	    existingBook.setAuthor(dto.getAuthor());
	    existingBook.setAvailablity(dto.getAvailablity());
	    existingBook.setCategory(dto.getCategory());
		existingBook.setEdition(dto.getEdition());
		existingBook.setCoverUrl(dto.getCoverUrl());
		existingBook.setSubject(dto.getSubject());
		existingBook.setSummary(dto.getSummary());

	    service.update(existingBook);
	    return true;
	}
	
	public boolean delete(@PathVariable long id) {
		service.delete(id);
		return true;
	}
	
	@RequestMapping("book/info/{id}")
	public Optional<BookInfoDto> findInfoById(@PathVariable long id){
		Optional<Book> optional = service.findById(id);
		if(optional.isEmpty()) {
	        return Optional.empty();
	    }
		Book book = optional.get();
		
		BookInfoDto dto = new BookInfoDto();
		
		dto.setId(book.getId());
		dto.setAuthor(book.getAuthor());
		dto.setCategory(book.getCategory());
		dto.setCoverUrl(book.getCoverUrl());
		dto.setIsbn(book.getIsbn());
		dto.setSubject(book.getSubject());
		dto.setSummary(book.getSummary());
		dto.setTitle(book.getTitle());
		dto.setAvailablity(book.getAvailablity());
		dto.setEdition(book.getEdition());
		
		return Optional.of(dto);
	}
	
	@PostMapping("book/search")
	public List<Book> search(@RequestBody SearchBookDto dto ) {
		return service.search(dto.getSearchWord());
	}
	
	@GetMapping("book/availability/{id}")
	public BookAvailabilityDto findAvailability(@PathVariable long id) {
		BookAvailabilityDto dto = new BookAvailabilityDto();
		
		Optional<Book> optional = service.findById(id);
		if(optional.isEmpty()) {
	        return dto;
	    }
		Book book = optional.get();
		
		dto.setNumberOfCopies(service.findNrOfCopies(book));
		dto.setNumberOfAvailableCopies(service.findNrOfAvailableCopies(book));
		
		return dto;
	}
}
