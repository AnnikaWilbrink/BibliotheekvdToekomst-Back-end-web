package nl.workingtalent.wtlibrary.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import nl.workingtalent.wtlibrary.dto.BorrowedBookDto;
import nl.workingtalent.wtlibrary.model.BorrowedBook;
import nl.workingtalent.wtlibrary.service.BorrowedBookService;

@RestController
@CrossOrigin(maxAge = 3600)
public class BorrowedBookController {

    @Autowired
    private BorrowedBookService service;

    @RequestMapping("borrowed-book/all")
    public List<BorrowedBookDto> findAllBorrowedBooks() {
        Iterable<BorrowedBook> borrowedBooks = service.findAll();
        List<BorrowedBookDto> dtos = new ArrayList<>();

        borrowedBooks.forEach(borrowedBook -> {
            BorrowedBookDto dto = new BorrowedBookDto();
            dto.setId(borrowedBook.getId());
            dto.setUserId(borrowedBook.getUser().getId());
            dto.setBookCopyId(borrowedBook.getBookCopy().getId());
            dto.setBorrowDate(borrowedBook.getBorrowDate());
            dto.setReturnedDate(borrowedBook.getReturnedDate());
            dtos.add(dto);
        });

        return dtos;
    }

    @RequestMapping(method = RequestMethod.POST, value = "borrowed-book/save")
    public boolean save(@RequestBody BorrowedBookDto dto) {
        BorrowedBook borrowedBook = new BorrowedBook();
        borrowedBook.setBorrowDate(dto.getBorrowDate());
        borrowedBook.setReturnedDate(dto.getReturnedDate());
        // TODO: Set user and bookCopy based on dto's userId and bookCopyId
        service.save(borrowedBook);
        return true;
    }

    @RequestMapping("borrowed-book/{id}")
    public Optional<BorrowedBookDto> findById(@PathVariable long id) {
        Optional<BorrowedBook> optional = service.findById(id);
        if (optional.isPresent()) {
            BorrowedBook borrowedBook = optional.get();
            BorrowedBookDto dto = new BorrowedBookDto();
            dto.setId(borrowedBook.getId());
            dto.setUserId(borrowedBook.getUser().getId());
            dto.setBookCopyId(borrowedBook.getBookCopy().getId());
            dto.setBorrowDate(borrowedBook.getBorrowDate());
            dto.setReturnedDate(borrowedBook.getReturnedDate());
            return Optional.of(dto);
        }

        return Optional.empty();
    }

    @RequestMapping(method = RequestMethod.PUT, value = "borrowed-book/{id}")
    public boolean update(@PathVariable long id, @RequestBody BorrowedBookDto dto) {
        Optional<BorrowedBook> optional = service.findById(id);
        if (optional.isEmpty()) {
            return false;
        }
        BorrowedBook existingBorrowedBook = optional.get();
        existingBorrowedBook.setBorrowDate(dto.getBorrowDate());
        existingBorrowedBook.setReturnedDate(dto.getReturnedDate());
        // TODO: Set user and bookCopy based on dto's userId and bookCopyId
        service.update(existingBorrowedBook);
        return true;
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "borrowed-book/{id}")
    public boolean deleteById(@PathVariable long id) {
        service.deleteById(id);
        return true;
    }
}
