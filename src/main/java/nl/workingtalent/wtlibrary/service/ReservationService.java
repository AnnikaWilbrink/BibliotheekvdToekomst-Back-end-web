package nl.workingtalent.wtlibrary.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import nl.workingtalent.wtlibrary.model.Book;
import nl.workingtalent.wtlibrary.model.BookCopy;
import nl.workingtalent.wtlibrary.model.Reservation;
import nl.workingtalent.wtlibrary.repository.IReservationRepository;


@Service
public class ReservationService {

	@Autowired 
	private IReservationRepository repository;
	
	public List<Reservation> findAll(){
		return repository.findAll();
	}
	
	public void save(Reservation reservation) {
		repository.save(reservation);
	}
	
	public Optional<Reservation> findById(@PathVariable long id){
		return repository.findById(id);
	}
	
	public void deleteById(long id) {
		repository.deleteById(id);
	}
	
	public void update(Reservation reservation) {
		repository.save(reservation);
	}
	
	public Optional<BookCopy> availableCopy(Book book) {
		List<BookCopy> bookCopies = book.getBookcopies();
		
		// Check of dit boek exemplaren heeft
		if (bookCopies.isEmpty()) {
			return Optional.empty();
		}
		
		// Zoek een exemplaar dat beschikbaar is
		for (BookCopy bookCopy : bookCopies) {
			if (bookCopy.isAvailable()) {
				return Optional.of(bookCopy);
			}
		}
		
		return Optional.empty();
	}
	
}
