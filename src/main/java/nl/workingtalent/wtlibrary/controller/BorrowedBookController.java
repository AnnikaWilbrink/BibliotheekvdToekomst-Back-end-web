package nl.workingtalent.wtlibrary.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import nl.workingtalent.wtlibrary.dto.BorrowedBookDto;
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
    private BookCopyService bookCopyService;
    
    @Autowired
    private UserService userService;

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
            dto.setBookTitle(borrowedBook.getBookTitle());
            dto.setBookCopyId(borrowedBook.getBookCopy().getId());
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
            dto.setBookTitle(borrowedBook.getBookTitle());
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
        existingBorrowedBook.setReturnedDate(LocalDateTime.now());
        service.update(existingBorrowedBook);
        return true;
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "borrowed-book/{id}")
    public boolean deleteById(@PathVariable long id) {
        service.deleteById(id);
        return true;
    }
}
