package nl.workingtalent.wtlibrary.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import nl.workingtalent.wtlibrary.model.Book;

public interface IBookRepository extends JpaRepository<Book, Long>{

	List<Book> findByTitleContainingOrAuthorContainingOrIsbn(String title, String author, String isbn, Sort sort);

	List<Book> findByCategoryOrAvailabilityOrReviewsOrSubject(String category, String availability, String reviews, String subject, Sort sort);

}
