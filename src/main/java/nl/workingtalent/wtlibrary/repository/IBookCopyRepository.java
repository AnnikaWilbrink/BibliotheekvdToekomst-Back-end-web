package nl.workingtalent.wtlibrary.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import nl.workingtalent.wtlibrary.model.Book;
import nl.workingtalent.wtlibrary.model.BookCopy;
import nl.workingtalent.wtlibrary.model.Book;


import java.util.List;

public interface IBookCopyRepository extends JpaRepository<BookCopy, Long> {
	
	List<BookCopy> findByBook(Book book);

    List<BookCopy> findByAvailableAndBook(boolean available, Book book);
    
    @Query("SELECT MAX(b.copyNumber) FROM BookCopy b WHERE b.book = :book")
    Long findMaxCopyNumberByBook(@Param("book") Book book);
}
