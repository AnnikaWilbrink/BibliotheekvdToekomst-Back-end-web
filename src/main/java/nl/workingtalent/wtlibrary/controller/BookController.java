package nl.workingtalent.wtlibrary.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import nl.workingtalent.wtlibrary.dto.BookArchiveDto;
import nl.workingtalent.wtlibrary.dto.BookAvailabilityDto;
import nl.workingtalent.wtlibrary.dto.BookAvgRatingDto;
import nl.workingtalent.wtlibrary.dto.BookDto;
import nl.workingtalent.wtlibrary.dto.BookInfoDto;
import nl.workingtalent.wtlibrary.dto.FilterBookDto;
import nl.workingtalent.wtlibrary.dto.SaveBookDto;
import nl.workingtalent.wtlibrary.dto.UpdateBookDto;
import nl.workingtalent.wtlibrary.model.Book;
import nl.workingtalent.wtlibrary.model.BookCopy;
import nl.workingtalent.wtlibrary.service.BookCopyService;
import nl.workingtalent.wtlibrary.service.BookService;
import nl.workingtalent.wtlibrary.service.BookCopyService;

@RestController
@CrossOrigin(maxAge = 3600)
public class BookController {

	@Autowired
	private BookService service;

	@Autowired
	private BookCopyService bookCopyService;

	@RequestMapping("book/all")
	public List<BookDto> findAllBooks() {
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
			// bookDto.setAvailability(dbBook.getAvailability());
			bookDto.setSubject(dbBook.getSubject());
			bookDto.setCategory(dbBook.getCategory());
			bookDto.setArchived(dbBook.isArchived());
			// bookDto.setReviews(dbBook.getReviews());

			dtos.add(bookDto);
		});

		return dtos;
	}
	
	@RequestMapping("book/allUnarchived")
	public List<BookDto> findAllUnarchivedBooks() {
		List<Book> dbBooks = service.findAllUnarchived();
		List<BookDto> dtos = new ArrayList<>();

		dbBooks.forEach(dbBook -> {
			BookDto bookDto = new BookDto();
			bookDto.setId(dbBook.getId());
			bookDto.setTitle(dbBook.getTitle());
			bookDto.setIsbn(dbBook.getIsbn());
			bookDto.setAuthor(dbBook.getAuthor());
			bookDto.setSummary(dbBook.getSummary());
			bookDto.setCoverUrl(dbBook.getCoverUrl());
			// bookDto.setAvailability(dbBook.getAvailability());
			bookDto.setSubject(dbBook.getSubject());
			bookDto.setCategory(dbBook.getCategory());
			bookDto.setArchived(dbBook.isArchived());
			// bookDto.setReviews(dbBook.getReviews());

			dtos.add(bookDto);
		});

		return dtos;
	}

	@PostMapping("book/save")
	public boolean save(@RequestBody SaveBookDto dto) {
		// Dto -> Model
		Book book = new Book();
		book.setTitle(dto.getTitle());
		book.setIsbn(dto.getIsbn());
		book.setAuthor(dto.getAuthor());
		book.setCategory(dto.getCategory());
		book.setEdition(dto.getEdition());
		book.setCoverUrl(dto.getCoverUrl());
		book.setEdition(dto.getEdition());
		book.setSubject(dto.getSubject());
		book.setSummary(dto.getSummary());
		

		 service.save(book);
		 return true;
		
	}

	@RequestMapping("book/{id}")
	public Optional<Book> findById(@PathVariable long id) {
		return service.findById(id);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "book/{id}")
	public boolean update(@PathVariable long id, @RequestBody UpdateBookDto dto) {
		Optional<Book> optional = service.findById(id);
		if (optional.isEmpty()) {
			return false;
		}
		Book existingBook = optional.get();

		existingBook.setTitle(dto.getTitle());
		existingBook.setIsbn(dto.getIsbn());
		existingBook.setAuthor(dto.getAuthor());
		existingBook.setAvailability(dto.getAvailability());
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
	public Optional<BookInfoDto> findInfoById(@PathVariable long id) {
		// find book by bookCopyId
		Optional<BookCopy> optionalBookCopy = bookCopyService.findById(id);
		if (optionalBookCopy.isEmpty()) {

			return Optional.empty();
		}
		BookCopy bookCopy = optionalBookCopy.get();

		// Directly get the Book from the BookCopy. No need for Optional here.
		Book book = bookCopy.getBook();

		// If the book doesn't exist for some reason, return empty.
		if (book == null) {
			return Optional.empty();
		}

		BookInfoDto dto = new BookInfoDto();

		dto.setId(book.getId());
		dto.setAuthor(book.getAuthor());
		dto.setCategory(book.getCategory());
		dto.setCoverUrl(book.getCoverUrl());
		dto.setIsbn(book.getIsbn());
		dto.setSubject(book.getSubject());
		dto.setSummary(book.getSummary());
		dto.setTitle(book.getTitle());
		dto.setAvailability(book.getAvailability());
		dto.setEdition(book.getEdition());

		return Optional.of(dto);
	}

	@RequestMapping("book/information/{id}")
	public Optional<BookInfoDto> findBookInfoById(@PathVariable long id) {

		Optional<Book> optional = service.findById(id);
		if(optional.isEmpty()) {
			return Optional.empty();
	    }
		Book book = optional.get();
		
		// TODO: get the BookAvailabilityDto based on the id.
		Optional<BookAvailabilityDto> optionalBookAvailabilityDto = Optional.of(findAvailability(id));
		
		
		BookInfoDto dto = new BookInfoDto();

		dto.setId(book.getId());
		dto.setAuthor(book.getAuthor());
		dto.setCategory(book.getCategory());
		dto.setCoverUrl(book.getCoverUrl());
		dto.setIsbn(book.getIsbn());
		dto.setSubject(book.getSubject());
		dto.setSummary(book.getSummary());
		dto.setTitle(book.getTitle());
		dto.setAvailability(book.getAvailability());
		dto.setEdition(book.getEdition());
		// Set numberOfAvailableCopies if BookAvailabilityDto is present
	    optionalBookAvailabilityDto.ifPresent(bookAvailabilityDto -> {
	        dto.setNumberOfAvailableCopies(bookAvailabilityDto.getNumberOfAvailableCopies());
	    });

		return Optional.of(dto);
	}

	@PostMapping("book/filter")
	public List<BookDto> filter(@RequestBody FilterBookDto dto ) {
		List<Book> books = service.filter(dto.getFilterWord(), dto.getCategories(), dto.getSubject(), dto.getMinReviewScore(), dto.getSortField(), dto.getSortOrder());
		
		return books.stream().map(book -> {
			BookDto bookDto = new BookDto();

			bookDto.setAuthor(book.getAuthor());
			// bookDto.setAvailability(book.getAvailability());
			bookDto.setCategory(book.getCategory());
			bookDto.setCoverUrl(book.getCoverUrl());
			bookDto.setId(book.getId());
			bookDto.setIsbn(book.getIsbn());
			bookDto.setSubject(book.getSubject());
			bookDto.setSummary(book.getSummary());
			bookDto.setTitle(book.getTitle());

			return bookDto;
		}).collect(Collectors.toList());
	}

	@PutMapping("book/average/rating/{id}")
	public BookAvgRatingDto calAvgRating(@PathVariable long id){
		Optional<Book> optional = service.findById(id);
		// if(optional.isEmpty()) {
		// 	return Optional.empty();
	    // }
		Book book = optional.get();

		BookAvgRatingDto avgRating = new BookAvgRatingDto();
		Double isAvgRating = service.calculateAverageRating(book);
		avgRating.setAvgRating(isAvgRating);
		return avgRating;
	}

	@GetMapping("book/availability/{id}")
	public BookAvailabilityDto findAvailability(@PathVariable long id) {
		BookAvailabilityDto dto = new BookAvailabilityDto();

		Optional<Book> optional = service.findById(id);
		if (optional.isEmpty()) {
			return dto;
		}
		Book book = optional.get();

		dto.setNumberOfCopies(service.findNrOfCopies(book));
		dto.setNumberOfAvailableCopies(service.findNrOfAvailableCopies(book));
		List<BookCopy> availableCopies = bookCopyService.findAvailableCopies(book);
		ArrayList<Long> availableCopyIds = new ArrayList<>();
		ArrayList<Long> availableCopyNrs = new ArrayList<>();
		for (BookCopy bookCopy : availableCopies) {
			availableCopyNrs.add(bookCopy.getCopyNumber());
			availableCopyIds.add(bookCopy.getId());
		}
		dto.setAvailable(availableCopyNrs);
		dto.setAvailableIds(availableCopyIds);
		// dto.setAvailableCopies(bookCopyService.findAvailableCopies(book));
		return dto;
	}

	@PutMapping("book/archive/{id}")
	public BookArchiveDto archiveBook(@PathVariable long id) {
		BookArchiveDto archived = new BookArchiveDto();
		boolean isArchived = service.archiveBook(id);
		archived.setArchived(isArchived);
		return archived;

	}

	@PutMapping("book/unarchive/{id}")
	public BookArchiveDto unarchiveBook(@PathVariable long id) {
		BookArchiveDto archived = new BookArchiveDto();
		boolean isArchived = service.unarchiveBook(id);
		archived.setArchived(isArchived);
		return archived;

	}
}
