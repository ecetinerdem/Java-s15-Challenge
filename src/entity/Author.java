package entity;

import java.util.HashSet;
import java.util.Set;

public class Author extends Person{

    private Set<Book> books;

    public Author (Long id, String name, String email) {
        super(id, name, email);
        this.books = new HashSet<>();

    }


    @Override
    public String whoYouAre() {
        return "Author " + getName();
    }

    public String addBook (Book book) {
        if (books.contains(book)){
            return "Book already exist";
        }
        books.add(book);
        return "Book added successfully";
    }
}
