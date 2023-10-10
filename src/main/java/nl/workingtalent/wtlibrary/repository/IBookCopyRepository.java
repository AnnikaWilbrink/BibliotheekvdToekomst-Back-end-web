package nl.workingtalent.wtlibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import nl.workingtalent.wtlibrary.model.BookCopy;

public interface IBookCopyRepository extends JpaRepository<BookCopy, Long> {

}
