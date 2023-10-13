package nl.workingtalent.wtlibrary.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import nl.workingtalent.wtlibrary.dto.FavoriteDto;
import nl.workingtalent.wtlibrary.model.Favorite;
import nl.workingtalent.wtlibrary.service.FavoriteService;


@RestController
@CrossOrigin(maxAge=3600)
public class FavoriteController {

	@Autowired
    private FavoriteService service;

    @GetMapping("favorite/all")
    public List<FavoriteDto> findAllFavorites() {
    	List<Favorite> favorites = service.findAll();
        List<FavoriteDto> dtos = new ArrayList<>();
        
        favorites.forEach(favorite -> {
        	FavoriteDto dto = new FavoriteDto();
        	dto.setId(favorite.getId());
        	dto.setUser(favorite.getUser());
        	dto.setBook(favorite.getBook());
        	
        	dtos.add(dto);
        });
        
        return dtos;
    }

    @GetMapping("favorite/{id}")
    public Favorite findById(@PathVariable long id) {
        return service.findById(id);
    }

    @PostMapping("favorite/save")
    public boolean save(@RequestBody FavoriteDto dto) {
    	Favorite favorite = new Favorite();
    	favorite.setId(dto.getId());
    	favorite.setUser(dto.getUser());
    	favorite.setBook(dto.getBook());
    	
        service.save(favorite);
        return true;
    }

    @DeleteMapping("favorite/{id}")
    public void delete(@PathVariable long id) {
        service.delete(id);
    }
	
}
