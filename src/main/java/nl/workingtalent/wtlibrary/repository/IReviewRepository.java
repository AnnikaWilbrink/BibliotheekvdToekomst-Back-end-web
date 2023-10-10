package nl.workingtalent.wtlibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import nl.workingtalent.wtlibrary.model.Review;

public interface IReviewRepository extends JpaRepository<Review, Long>{

}
