package nl.workingtalent.wtlibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import nl.workingtalent.wtlibrary.model.Book;

public interface IBookRepository extends JpaRepository<Book, Long>{
}
