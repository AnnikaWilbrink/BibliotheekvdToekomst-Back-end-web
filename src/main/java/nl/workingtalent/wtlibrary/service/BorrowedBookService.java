package nl.workingtalent.wtlibrary.service;

import java.util.List;  
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import nl.workingtalent.wtlibrary.model.BorrowedBook;
import nl.workingtalent.wtlibrary.repository.IBorrowedBookRepository;


@Service
public class BorrowedBookService {
	
	@Autowired
	private  IBorrowedBookRepository repository;

	public List<BorrowedBook> findAll(){
		return repository.findAll();
	}
	
	public void save(BorrowedBook BorrowBookHistory) {
		repository.save(BorrowBookHistory);
	}
	
	public Optional<BorrowedBook> findById(@PathVariable long id){
		return repository.findById(id);
	}
	
	public void deleteById(long id) {
		repository.deleteById(id);
	}
	
	public void update(BorrowedBook BorrowBookHistory) {
		repository.save(BorrowBookHistory);
	}
	
}