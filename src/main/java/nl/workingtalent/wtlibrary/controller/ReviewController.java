package nl.workingtalent.wtlibrary.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import nl.workingtalent.wtlibrary.dto.ReviewDto;
import nl.workingtalent.wtlibrary.dto.SaveReviewDto;
import nl.workingtalent.wtlibrary.model.Book;
import nl.workingtalent.wtlibrary.model.Review;
import nl.workingtalent.wtlibrary.model.User;
import nl.workingtalent.wtlibrary.service.BookService;
import nl.workingtalent.wtlibrary.service.ReviewService;
import nl.workingtalent.wtlibrary.service.UserService;

@RestController
@CrossOrigin(maxAge=3600)
public class ReviewController {
    
    @Autowired
    private ReviewService reviewService;
    
    @Autowired
    private BookService bookService;
    
    @Autowired
    private UserService userService;
    
    @GetMapping("review/all")
    public List<ReviewDto> findAll(HttpServletRequest request) {
        User loggedInUser = (User)request.getAttribute("WT_USER");
    	if (loggedInUser == null) {
    		return Collections.emptyList(); // Return an empty list
    	}
    	
    	List<ReviewDto> dtos = new ArrayList<>();
    	
    	if (loggedInUser.isAdmin()) {
    		Iterable<Review> reviews = reviewService.findAll();
            
            reviews.forEach(review -> {
                ReviewDto dto = new ReviewDto();
                dto.setId(review.getId());
                dto.setBookId(review.getBook().getId());
                dto.setUserId(review.getUser().getId());
                dto.setText(review.getText());
                dto.setStars(review.getStars());
                
                dtos.add(dto);
            });
            
            return dtos;
    	}
    	
    	return Collections.emptyList(); // Return an empty list
    }
    
    @PostMapping(value="review/save")
    public boolean save(@RequestBody SaveReviewDto dto, HttpServletRequest request) {
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
    		Review review = new Review(); 
            
            review.setBook(book);
           	review.setUser(user);
            review.setText(dto.getText());
            review.setStars(dto.getStars());
            
            reviewService.save(review);
            return true;
    	}
    	return false;
    }
    
    @RequestMapping("review/{id}")
    public Optional<ReviewDto> findById(@PathVariable long id, HttpServletRequest request) {
    	User loggedInUser = (User)request.getAttribute("WT_USER");
    	if (loggedInUser == null) {
    		return Optional.empty();
    	}
    	
    	if (loggedInUser.isAdmin()) {
    		Optional<Review> optional = reviewService.findById(id);
            if(optional.isPresent()) {
                Review review = optional.get();
                ReviewDto dto = new ReviewDto();
                dto.setId(review.getId());
                dto.setBookId(review.getBook().getId());
                dto.setUserId(review.getUser().getId());
                dto.setText(review.getText());
                dto.setStars(review.getStars());
                
                return Optional.of(dto);
            }
    	}
        
        return Optional.empty();
    }
    
    @PutMapping(value="review/{id}")
    public boolean update(@PathVariable long id, @RequestBody SaveReviewDto dto, HttpServletRequest request) {
    	User loggedInUser = (User)request.getAttribute("WT_USER");
    	if (loggedInUser == null) {
    		return false;
    	}
    	
        Optional<Review> optional = reviewService.findById(id);
        if(optional.isEmpty()) {
            return false;
        }
        Review existingReview = optional.get();
        
        Optional<Book> bookOptional = bookService.findById(dto.getBookId());
        if (bookOptional.isEmpty()) {
            return false;
        }
        Book book = bookOptional.get();
        
        Optional <User> userOptional = userService.findById(dto.getUserId());
        if (userOptional.isEmpty()) {
        	return false;
        }
        User user = userOptional.get();
        
        if (loggedInUser.isAdmin() || (loggedInUser.getId() == user.getId())) {
        	existingReview.setBook(book);
            existingReview.setUser(user);
            existingReview.setText(dto.getText());
            existingReview.setStars(dto.getStars());
            
            reviewService.update(existingReview);
            return true;
        }
        
        return false;
    }
}
