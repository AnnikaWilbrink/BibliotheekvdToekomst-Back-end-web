package nl.workingtalent.wtlibrary.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import nl.workingtalent.wtlibrary.model.Book;
import nl.workingtalent.wtlibrary.dto.BorrowedBookDto;

import nl.workingtalent.wtlibrary.dto.BorrowedBookTableDto;
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

import nl.workingtalent.wtlibrary.dto.CreateBorrowedBookDto;
import nl.workingtalent.wtlibrary.model.BookCopy;
import nl.workingtalent.wtlibrary.model.BorrowedBook;
import nl.workingtalent.wtlibrary.model.Reservation;
import nl.workingtalent.wtlibrary.model.User;
import nl.workingtalent.wtlibrary.service.BorrowedBookService;
import nl.workingtalent.wtlibrary.service.BookCopyService;
import nl.workingtalent.wtlibrary.service.ReservationService;
import nl.workingtalent.wtlibrary.service.UserService;

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
            dto.setUserFirstName(borrowedBook.getUser().getFirstName());
            dto.setUserLastName(borrowedBook.getUser().getLastName());
            dto.setUserEmail(borrowedBook.getUser().getEmail());
            dto.setBookTitle(borrowedBook.getBookTitle());
//            dto.setBookCopyId(borrowedBook.getBookCopy().getId());
            BookCopy bookCopy = borrowedBook.getBookCopy();
            dto.setBookCopyId(bookCopy.getId());
            dto.setCopyNumber(bookCopy.getCopyNumber());
            dto.setBorrowDate(borrowedBook.getBorrowDate());
            dto.setReturnedDate(borrowedBook.getReturnedDate());
            dtos.add(dto);
        });

        return dtos;
    }

    @RequestMapping(method = RequestMethod.POST, value = "borrowed-book/save")
    public boolean save(@RequestBody CreateBorrowedBookDto dto) {
        Optional<User> userOptional = userService.findById(dto.getUserId());
        if (userOptional.isEmpty()) {
            return false;
        }
        User user = userOptional.get();
        Optional<BookCopy> bookCopyOptional = bookCopyService.findById(dto.getBookCopyId());
        if (bookCopyOptional.isEmpty()) {
            return false;
        }
        BookCopy bookCopy = bookCopyOptional.get();

        BorrowedBook borrowedBook = new BorrowedBook();

        borrowedBook.setId(dto.getId());
        borrowedBook.setBorrowDate(LocalDateTime.now());
        borrowedBook.setReturnedDate(null);
        borrowedBook.setUser(user);
        borrowedBook.setBookTitle(dto.getBookTitle());
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
            dto.setUserEmail(borrowedBook.getUser().getEmail());
            dto.setBookTitle(borrowedBook.getBookTitle());
            BookCopy bookCopy = borrowedBook.getBookCopy();
            if (bookCopy != null) {
                dto.setBookCopyId(bookCopy.getId());
                dto.setCopyNumber(bookCopy.getCopyNumber());
            }
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
        // Don't change this method's body or you'll break the return functionality :(
        BorrowedBook existingBorrowedBook = optional.get();
        existingBorrowedBook.setReturnedDate(LocalDateTime.now());
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

    @GetMapping("/user/borrowed-books-list")
    public List<BorrowedBookTableDto> getUserBorrowedBooks(HttpServletRequest request) {
        User user = (User) request.getAttribute("WT_USER");

        if (user == null) {
            throw new RuntimeException("User not found.");
        }

        List<BorrowedBookTableDto> borrowedBookDTOs = service.findDtosByUser(user);

        return borrowedBookDTOs;
    }

}
