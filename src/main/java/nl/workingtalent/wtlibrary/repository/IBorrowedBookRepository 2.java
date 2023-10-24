package nl.workingtalent.wtlibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import nl.workingtalent.wtlibrary.model.BorrowedBook; 

public interface IBorrowedBookRepository extends JpaRepository<BorrowedBook,Long> {
}
