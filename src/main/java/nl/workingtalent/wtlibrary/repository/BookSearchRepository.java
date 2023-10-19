package nl.workingtalent.wtlibrary.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import nl.workingtalent.wtlibrary.model.Book;
import nl.workingtalent.wtlibrary.model.Review;

@Repository
public class BookSearchRepository {

	@Autowired
	private EntityManager em;

	public List<Book> search(String filterWord, List<String> categories, Integer minReviewScore) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Book> cq = cb.createQuery(Book.class);

        Root<Book> book = cq.from(Book.class);
        List<Predicate> predicates = new ArrayList<>();

        if (filterWord != null && !filterWord.isBlank()) {
        	Predicate searchAuthorPredicate = cb.like(book.get("author"), "%" + filterWord + "%");
        	Predicate searchTitlePredicate = cb.like(book.get("title"), "%" + filterWord + "%");
        	Predicate searchIsbnPredicate = cb.equal(book.get("isbn"), filterWord);
        	
        	Predicate searchWordPredicate = cb.or(searchAuthorPredicate, searchTitlePredicate, searchIsbnPredicate);
        	predicates.add(searchWordPredicate);
        }

        if (categories != null && !categories.isEmpty()) {
        	In<String> in = cb.in(book.get("category"));
        	categories.forEach(p -> in.value(p));

        	predicates.add(in);
        }

        if (minReviewScore != null) {
        	Join<Book, Review> bookReviewsJoin = book.join("reviews");

        	Predicate reviewsPredicate = cb.greaterThanOrEqualTo(bookReviewsJoin.get("stars"), minReviewScore);
        	predicates.add(reviewsPredicate);
        }

        if (!predicates.isEmpty()) {
	        Predicate finalQuery = cb.and(predicates.toArray(new Predicate[] {}));
	        cq.where(finalQuery);
        }
        cq.orderBy(cb.asc(book.get("title")));

        TypedQuery<Book> query = em.createQuery(cq);

        return query.getResultList();
	}

}
