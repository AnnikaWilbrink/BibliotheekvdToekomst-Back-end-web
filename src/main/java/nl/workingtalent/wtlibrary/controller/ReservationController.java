package nl.workingtalent.wtlibrary.controller;

import java.util.ArrayList; 
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import nl.workingtalent.wtlibrary.dto.ReservationDto;
import nl.workingtalent.wtlibrary.dto.SaveReservationDto;
import nl.workingtalent.wtlibrary.model.Book;
import nl.workingtalent.wtlibrary.model.Reservation;
import nl.workingtalent.wtlibrary.model.User;
import nl.workingtalent.wtlibrary.service.BookService;
import nl.workingtalent.wtlibrary.service.ReservationService;
import nl.workingtalent.wtlibrary.service.UserService;

@RestController
@CrossOrigin(maxAge=3600)
public class ReservationController {
    
    @Autowired
    private ReservationService service;
    
    @Autowired
    private BookService bookService;
    
    @Autowired
    private UserService userService;
    
    @RequestMapping("reservation/all")
    public List<ReservationDto> findAllReservations() {
        Iterable<Reservation> reservations = service.findAll();
        List<ReservationDto> dtos = new ArrayList<>();
        
        reservations.forEach(reservation -> {
            ReservationDto dto = new ReservationDto();
            dto.setId(reservation.getId());
            dto.setReservationDate(reservation.getReservationDate());
            dto.setApproved(reservation.isApproved());
            dto.setDeleted(reservation.isDeleted());
            dto.setBorrowed(reservation.isBorrowed());
            dto.setUserFirstName(reservation.getUser().getFirstName());
            dto.setUserLastName(reservation.getUser().getLastName());
            dto.setBookTitle(reservation.getBook().getTitle());
            dto.setBookId(reservation.getBook().getId());
            dtos.add(dto);
        });
        
        return dtos;
    }
    
    @PostMapping(value="reservation/save")
    public boolean save(@RequestBody SaveReservationDto dto) {
        // ToDo: authorization maken met httpservlet
    	
        Optional<User> userOptional = userService.findById(dto.getUserId());
        if (userOptional.isEmpty()) {
        	return false;
        }
        User user = userOptional.get();
        
        Optional<Book> bookOptional = bookService.findById(dto.getBookId());
        if (bookOptional.isEmpty()) {
            return false;
        }
        Book book = bookOptional.get();
        
        Reservation reservation = new Reservation();
        reservation.setBook(book);
        reservation.setUser(user);
        reservation.setReservationDate(dto.getReservationDate());
        reservation.setApproved(user.isAdmin());
        reservation.setDeleted(false);
        reservation.setBorrowed(false);
        service.save(reservation);
        return true;
    }
    
    @RequestMapping("reservation/{id}")
    public Optional<ReservationDto> findById(@PathVariable long id) {
        Optional<Reservation> optional = service.findById(id);
        if(optional.isPresent()) {
            Reservation reservation = optional.get();
            ReservationDto dto = new ReservationDto();
            dto.setId(reservation.getId());
            dto.setReservationDate(reservation.getReservationDate());
            dto.setApproved(reservation.isApproved());
            dto.setDeleted(reservation.isDeleted());
            dto.setBorrowed(reservation.isBorrowed());
            dto.setUserFirstName(reservation.getUser().getFirstName());
            dto.setUserLastName(reservation.getUser().getLastName());
            dto.setBookTitle(reservation.getBook().getTitle());
            dto.setBookId(reservation.getBook().getId());
            return Optional.of(dto);
        }
        
        return Optional.empty();
    }
    
    @RequestMapping(method = RequestMethod.PUT, value="reservation/{id}")
    public boolean update(@PathVariable long id, @RequestBody SaveReservationDto dto) {
        Optional<Reservation> optional = service.findById(id);
        if(optional.isEmpty()) {
            return false;
        }
        Reservation existingReservation = optional.get();
        // existingReservation.setReservationDate(dto.getReservationDate());
        existingReservation.setApproved(dto.isApproved());
        existingReservation.setDeleted(dto.isDeleted());
        existingReservation.setBorrowed(dto.isBorrowed());
        service.update(existingReservation);
        return true; 
    }

    @RequestMapping(method = RequestMethod.DELETE, value="reservation/{id}")
    public boolean deleteById(@PathVariable long id) {
    	service.deleteById(id);
    	return true;
    }
}
