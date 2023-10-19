package nl.workingtalent.wtlibrary.dto;

import java.time.LocalDateTime;

public class BorrowedBookDto {

    private long id;
    private long userId;
    private long bookCopyId;
    private LocalDateTime borrowDate;
    private LocalDateTime returnedDate;

    // Getters and setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getBookCopyId() {
        return bookCopyId;
    }

    public void setBookCopyId(long bookCopyId) {
        this.bookCopyId = bookCopyId;
    }

    public LocalDateTime getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDateTime borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDateTime getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(LocalDateTime returnedDate) {
        this.returnedDate = returnedDate;
    }
}
