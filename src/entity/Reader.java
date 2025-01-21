package entity;

import java.util.ArrayList;
import java.util.List;

public class Reader extends Person{

    private List<Book> borrowedBooks;
    private Integer bookBorrowLimit;
    private Double balance;

    public Reader(Long id, String name, String email) {
        super(id, name, email);
        this.borrowedBooks = new ArrayList<>();
        this.bookBorrowLimit = 5;
        this.balance = 0.0;
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(List<Book> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    public Integer getBookBorrowLimit() {
        return bookBorrowLimit;
    }

    public void setBookBorrowLimit(Integer bookBorrowLimit) {
        this.bookBorrowLimit = bookBorrowLimit;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @Override
    public String whoYouAre() {
        return "Reader " + getName();
    }

    public Boolean canBorrowMore() {
        if (borrowedBooks.size() >= bookBorrowLimit) {
            return false;
        }
        return true;
    }
}
