package nl.workingtalent.wtlibrary.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import nl.workingtalent.wtlibrary.model.Book;
import nl.workingtalent.wtlibrary.model.BookCopy;
import nl.workingtalent.wtlibrary.model.Book;


import java.util.List;

public interface IBookCopyRepository extends JpaRepository<BookCopy, Long> {
	
	List<BookCopy> findByBook(Book book);

    List<BookCopy> findByAvailableAndBook(boolean available, Book book);
    
    Long findTopByOrderByCopyNumberDesc();
}
