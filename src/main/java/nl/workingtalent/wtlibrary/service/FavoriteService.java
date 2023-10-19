package nl.workingtalent.wtlibrary.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import nl.workingtalent.wtlibrary.model.Favorite;
import nl.workingtalent.wtlibrary.repository.IFavoriteRepository;

@Service
public class FavoriteService {

	@Autowired
    private IFavoriteRepository repository;
    
    public List<Favorite> findAll() {
        return repository.findAll();
    }
    
    public Favorite findById(@PathVariable long id) {
        return repository.findById(id).orElse(null);
    }
    
    public List<Favorite> findAllByUserId(long userId) {
    	return repository.findByUserId(userId);
    }
    
    public Favorite findByUserIdAndBookId(long userId, long bookId) {
        return repository.findByUserIdAndBookId(userId, bookId);
    }
    
    public Favorite save(Favorite favorite) {
        return repository.save(favorite);
    }
    
    public void delete(long id) {
    	repository.deleteById(id);
    }
}