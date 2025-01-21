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

    @Override
    public String whoYouAre() {
        return "Reader " + getName();
    }

    public String canBorrowMore() {
        if (borrowedBooks.size() >= bookBorrowLimit) {
            return "Sorry you cannot borrow anymore books until you return previously borrowed books";
        }
        return "Book successfully borrowed";
    }
}
