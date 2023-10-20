package nl.workingtalent.wtlibrary.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import nl.workingtalent.wtlibrary.model.Book;
import nl.workingtalent.wtlibrary.dto.BorrowedBookDto;
import nl.workingtalent.wtlibrary.dto.LendBorrowedBookDto;
import nl.workingtalent.wtlibrary.model.BookCopy;
import nl.workingtalent.wtlibrary.model.BorrowedBook;
import nl.workingtalent.wtlibrary.service.BookCopyService;
import nl.workingtalent.wtlibrary.service.BookService;
import nl.workingtalent.wtlibrary.service.BorrowedBookService;
import nl.workingtalent.wtlibrary.service.ReservationService;
import nl.workingtalent.wtlibrary.service.UserService;
import nl.workingtalent.wtlibrary.model.Reservation;
import nl.workingtalent.wtlibrary.model.User;

@RestController
@CrossOrigin(maxAge = 3600)
public class BorrowedBookController {

    @Autowired
    private BorrowedBookService service;
    
    @Autowired
    private ReservationService reservationService;
    
    @Autowired
    private BookService bookService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private BookCopyService bookCopyService;

    @RequestMapping("borrowed-book/all")
    public List<BorrowedBookDto> findAllBorrowedBooks() {
        Iterable<BorrowedBook> borrowedBooks = service.findAll();
        List<BorrowedBookDto> dtos = new ArrayList<>();

        borrowedBooks.forEach(borrowedBook -> {
            BorrowedBookDto dto = new BorrowedBookDto();
            dto.setId(borrowedBook.getId());
            dto.setUserId(borrowedBook.getUser().getId());
            dto.setBookCopyId(borrowedBook.getBookCopy().getId());
            dto.setBorrowDate(borrowedBook.getBorrowDate());
            dto.setReturnedDate(borrowedBook.getReturnedDate());
            dtos.add(dto);
        });

        return dtos;
    }

    @RequestMapping(method = RequestMethod.POST, value = "borrowed-book/save")
    public boolean save(@RequestBody BorrowedBookDto dto) {
        BorrowedBook borrowedBook = new BorrowedBook();
        
        // find the User by the id in the dto
        Optional <User> userOptional = userService.findById(dto.getUserId());
        if (userOptional.isEmpty()) {
        	return false;
        }
        User user = userOptional.get();
        
        // find the BookCopy by the id in the dto
        Optional <BookCopy> bookCopyOptional = bookCopyService.findById(dto.getBookCopyId());
        if (bookCopyOptional.isEmpty()) {
        	return false;
        }
        BookCopy bookCopy = bookCopyOptional.get();
        
        borrowedBook.setBorrowDate(dto.getBorrowDate());
        borrowedBook.setReturnedDate(dto.getReturnedDate());
        borrowedBook.setUser(user);
        borrowedBook.setBookCopy(bookCopy);
        
        service.save(borrowedBook);
        return true;
    }

    @RequestMapping("borrowed-book/{id}")
    public Optional<BorrowedBookDto> findById(@PathVariable long id) {
        Optional<BorrowedBook> optional = service.findById(id);
        if (optional.isPresent()) {
            BorrowedBook borrowedBook = optional.get();
            BorrowedBookDto dto = new BorrowedBookDto();
            dto.setId(borrowedBook.getId());
            dto.setUserId(borrowedBook.getUser().getId());
            dto.setBookCopyId(borrowedBook.getBookCopy().getId());
            dto.setBorrowDate(borrowedBook.getBorrowDate());
            dto.setReturnedDate(borrowedBook.getReturnedDate());
            return Optional.of(dto);
        }

        return Optional.empty();
    }

    @RequestMapping(method = RequestMethod.PUT, value = "borrowed-book/{id}")
    public boolean update(@PathVariable long id, @RequestBody BorrowedBookDto dto) {
        Optional<BorrowedBook> optional = service.findById(id);
        if (optional.isEmpty()) {
            return false;
        }
        BorrowedBook existingBorrowedBook = optional.get();
        existingBorrowedBook.setBorrowDate(dto.getBorrowDate());
        existingBorrowedBook.setReturnedDate(dto.getReturnedDate());
        // TODO: Set user and bookCopy based on dto's userId and bookCopyId
        service.update(existingBorrowedBook);
        return true;
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "borrowed-book/{id}")
    public boolean deleteById(@PathVariable long id) {
        service.deleteById(id);
        return true;
    }
    
    
    @PostMapping("borrowed-book/save/{id}")
    public @ResponseBody Map<String, Object> lendBook(@RequestBody LendBorrowedBookDto dto) {
        Map<String, Object> response = new HashMap<>();
        
        Long reservationId = dto.getReservationId();
        
        Optional<Reservation> optionalReservation = reservationService.findById(reservationId);
        if (optionalReservation.isEmpty()) {
            // Handle the case where the reservation is not found
            response.put("success", false);
            response.put("message", "Reservation not found");
            return response;
        }
        Reservation reservation = optionalReservation.get();
        
        long bookId = reservation.getBook().getId();
        
        // vind Book aan de hand van bookId
        Optional<Book> optionalBook = bookService.findById(bookId);
        if (optionalBook.isEmpty()) {
            // Handle the case where the book is not found
            response.put("success", false);
            response.put("message", "Book not found");
            return response;
        }
        Book book = optionalBook.get();
        
        // Finding an available BookCopy for the given Book
        Optional<BookCopy> optionalBookCopy = service.availableCopy(book);
        if (optionalBookCopy.isEmpty()) {
            response.put("success", false);
            response.put("message", "No available copy for the book");
            return response;
        }
        BookCopy bookCopy = optionalBookCopy.get();
        
        
        // find User aan de hand van userid in reservation
        Long userId = reservation.getUser().getId();
        
        Optional<User> optionalUser = userService.findById(userId);
        if (optionalUser.isEmpty()) {
        	// Handle the case where the user is not found
        	response.put("success", false);
        	response.put("message", "User not found");
        	return response;
        }
        User user = optionalUser.get();
        
        // Creating a new BorrowedBook record
        BorrowedBook borrowedBook = new BorrowedBook();
        borrowedBook.setBorrowDate(java.time.LocalDateTime.now()); // Setting the current date-time as borrow date
        borrowedBook.setBookCopy(bookCopy);
        borrowedBook.setUser(user);
        service.save(borrowedBook);
        
        
        response.put("success", true);
        response.put("bookCopyId", bookCopy.getId());

        return response;
    }
}
