package nl.workingtalent.wtlibrary.controller;

import java.util.ArrayList;
//import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import nl.workingtalent.wtlibrary.dto.FavoriteDto;
import nl.workingtalent.wtlibrary.model.Book;
import nl.workingtalent.wtlibrary.model.Favorite;
import nl.workingtalent.wtlibrary.model.User;
import nl.workingtalent.wtlibrary.service.BookService;
import nl.workingtalent.wtlibrary.service.FavoriteService;
import nl.workingtalent.wtlibrary.service.UserService;


@RestController
@CrossOrigin(maxAge=3600)
public class FavoriteController {

	@Autowired
    private FavoriteService service;
	
	@Autowired
    private BookService bookService;
    
    @Autowired
    private UserService userService;

    @GetMapping("favorite/all/{id}")
    public List<FavoriteDto> findAllFavorites(@PathVariable long id, HttpServletRequest request) {

    	List<Favorite> favorites = service.findAllByUserId(id);
        List<FavoriteDto> dtos = new ArrayList<>();
        
        favorites.forEach(favorite -> {
        	FavoriteDto dto = new FavoriteDto();
        	dto.setId(favorite.getId());
        	dto.setUserId(favorite.getUser().getId());
        	dto.setBookId(favorite.getBook().getId());
        	
        	dtos.add(dto);
        });
        
        return dtos;
    }

    @GetMapping("favorite/{id}")
    public Favorite findById(@PathVariable long id) {
        return service.findById(id);
    }

    @PostMapping("favorite/save")
    public boolean save(@RequestBody FavoriteDto dto, HttpServletRequest request) {
    	User loggedInUser = (User)request.getAttribute("WT_USER");
    	if (loggedInUser == null) {
    		return false;
    	}
    	
    	Optional <User> userOptional = userService.findById(dto.getUserId());
        if (userOptional.isEmpty()) {
        	return false;
        }
        User user = userOptional.get();
        
        Optional<Book> bookOptional = bookService.findById(dto.getBookId());
        if (bookOptional.isEmpty()) {
            return false;
        }
        Book book = bookOptional.get();
        
        if (loggedInUser.getId() == user.getId()) {
        	Favorite favorite = new Favorite();

        	favorite.setUser(user);
        	favorite.setBook(book);
        	
            service.save(favorite);
            return true;
        }
    	return false;
    }

    @DeleteMapping("favorite/delete")
    public void delete(@RequestBody FavoriteDto dto, HttpServletRequest request) {
    	Optional <User> userOptional = userService.findById(dto.getUserId());
    	User user = userOptional.get();
    	
    	Optional<Book> bookOptional = bookService.findById(dto.getBookId());
    	Book book = bookOptional.get();
    	
    	Favorite favorite = service.findByUserIdAndBookId(user.getId(), book.getId());
    	
        service.delete(favorite.getId());
    }
	
}
