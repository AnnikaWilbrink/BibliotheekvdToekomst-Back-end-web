package nl.workingtalent.wtlibrary.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import nl.workingtalent.wtlibrary.dto.BorrowedBookDto;
import nl.workingtalent.wtlibrary.dto.LendBorrowedBookDto;
import nl.workingtalent.wtlibrary.model.BorrowedBook;
import nl.workingtalent.wtlibrary.service.BorrowedBookService;
import nl.workingtalent.wtlibrary.service.ReservationService;
import nl.workingtalent.wtlibrary.model.Reservation;

@RestController
@CrossOrigin(maxAge = 3600)
public class BorrowedBookController {

    @Autowired
    private BorrowedBookService service;
    
    @Autowired
    private ReservationService reservationService;

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
        borrowedBook.setBorrowDate(dto.getBorrowDate());
        borrowedBook.setReturnedDate(dto.getReturnedDate());
        // TODO: Set user and bookCopy based on dto's userId and bookCopyId
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
    
    
    
    // BOEK UITLENEN
    
    // checkt of er een bookCopy beschikbaar is (numberOfAvailableCopies) anders niet goedkeuren
 
    // kiest een bookCopy uit en zegt dat de admin/frdskmed deze maguitlenen.
    // borrowedbook wordt aangemaakt met borrowDate als huidige datum en tijd
    // 
    
    
    @PostMapping("/borrowedBook/lend")
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
        
        Long bookId = reservation.getBook().getId();
        

        // TODO: Implementeer de logica om het boek uit te lenen gebaseerd op de dto

        boolean success = true; // Dit is een dummy waarde. Je moet dit vervangen door de werkelijke logica om te bepalen of het uitlenen succesvol was.
        response.put("success", success);
        response.put("bookCopyId", dto.getBookCopyId()); // TODO: dit klopt nog niet

        return response;
    }
    
    
   // TODO: verander deze functie
//    @PostMapping(value="reservation/save")
//    public boolean save(@RequestBody SaveReservationDto dto) {
//        // ToDo: authorization maken met httpservlet
//        Optional<User> userOptional = userService.findById(dto.getUserId());
//        
//        if (userOptional.isEmpty()) {
//        	return false;
//        }
//        User user = userOptional.get();
//        Optional<Book> bookOptional = bookService.findById(dto.getBookId());
//        if (bookOptional.isEmpty()) {
//            return false;
//        }
//        Book book = bookOptional.get();
//        // Vind een beschikbaar exemplaar (en check of die wel bestaat)
//        Optional<BookCopy> bookCopyOptional = service.availableCopy(book);
//        if (bookCopyOptional.isEmpty()) {
//        	return false;
//        }
//        BookCopy bookCopy = bookCopyOptional.get();
//
//        Reservation reservation = new Reservation();
//
//        reservation.setBookCopy(bookCopy);
//        reservation.setUser(user);
//        reservation.setReservationDate(dto.getReservationDate());
//        reservation.setReservationStatus(dto.getReservationStatus());
//
//        service.save(reservation);
//
//        return true;
//    }
}
