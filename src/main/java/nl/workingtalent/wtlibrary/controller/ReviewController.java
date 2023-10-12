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
    
    @RequestMapping("review/all")
    public List<ReviewDto> findAll() {
        Iterable<Review> reviews = reviewService.findAll();
        List<ReviewDto> dtos = new ArrayList<>();
        
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
    
    @RequestMapping(method = RequestMethod.POST, value="review/save")
    public boolean save(@RequestBody SaveReviewDto dto) {
        Review review = new Review();
        
        // Populate Book and User with their IDs from the dto by fetching these entities from their services first.
        Optional<Book> bookOptional = bookService.findById(dto.getBookId());
        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            review.setBook(book);
        } else {
            return false;
        }
        
        Optional <User> userOptional = userService.findById(dto.getUserId());
        if (userOptional.isPresent()) {
        	User user = userOptional.get();
        	review.setUser(user);
        } else {
        	return false;
        }
        
        review.setText(dto.getText());
        review.setStars(dto.getStars());
        
        reviewService.save(review);
        return true;
    }
    
    @RequestMapping("review/{id}")
    public Optional<ReviewDto> findById(@PathVariable long id) {
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
        
        return Optional.empty();
    }
    
    @RequestMapping(method = RequestMethod.PUT, value="review/{id}")
    public boolean update(@PathVariable long id, @RequestBody SaveReviewDto dto) {
        Optional<Review> optional = reviewService.findById(id);
        if(optional.isEmpty()) {
            return false;
        }
        Review existingReview = optional.get();
        // You might need to update the Book and User relationships using their IDs from the dto.
        existingReview.setText(dto.getText());
        existingReview.setStars(dto.getStars());
        
        reviewService.update(existingReview);
        return true;
    }
}
