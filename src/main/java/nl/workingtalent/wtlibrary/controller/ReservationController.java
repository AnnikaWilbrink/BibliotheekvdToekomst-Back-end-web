package nl.workingtalent.wtlibrary.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import nl.workingtalent.wtlibrary.dto.ReservationDto;
import nl.workingtalent.wtlibrary.dto.SaveReservationDto;
import nl.workingtalent.wtlibrary.model.Reservation;
import nl.workingtalent.wtlibrary.service.ReservationService;

@RestController
@CrossOrigin(maxAge=3600)
public class ReservationController {
    
    @Autowired
    private ReservationService service;
    
    @RequestMapping("reservation/all")
    public List<ReservationDto> findAllReservations() {
        Iterable<Reservation> reservations = service.findAll();
        List<ReservationDto> dtos = new ArrayList<>();
        
        reservations.forEach(reservation -> {
            ReservationDto dto = new ReservationDto();
            dto.setId(reservation.getId());
            dto.setReservationDate(reservation.getReservationDate());
            
            dtos.add(dto);
        });
        
        return dtos;
    }
    
    @RequestMapping(method = RequestMethod.POST, value="reservation/save")
    public boolean save(@RequestBody SaveReservationDto dto) {
        Reservation reservation = new Reservation();
        reservation.setReservationDate(dto.getReservationDate());
        reservation.setReservationStatus(dto.getReservationStatus());
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
        existingReservation.setReservationDate(dto.getReservationDate());
        
        service.update(existingReservation);
        return true;
    }
}
