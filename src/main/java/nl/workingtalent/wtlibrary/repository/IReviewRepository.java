package nl.workingtalent.wtlibrary.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import nl.workingtalent.wtlibrary.model.Book;

import nl.workingtalent.wtlibrary.model.Review;

public interface IReviewRepository extends JpaRepository<Review, Long>{
	
	List<Review> findByBook(Book book);

	List<Review> findByUserId(Long userId);

}
