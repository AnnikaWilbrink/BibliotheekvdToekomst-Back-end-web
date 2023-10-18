package nl.workingtalent.wtlibrary.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

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
	
}
