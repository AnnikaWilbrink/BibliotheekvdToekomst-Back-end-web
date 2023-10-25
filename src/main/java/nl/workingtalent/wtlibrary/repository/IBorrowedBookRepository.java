package nl.workingtalent.wtlibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository; 

import java.util.List; 

import nl.workingtalent.wtlibrary.model.BorrowedBook; 
import nl.workingtalent.wtlibrary.model.User;

public interface IBorrowedBookRepository extends JpaRepository<BorrowedBook,Long> {
	List<BorrowedBook> findByUser(User user);
}
