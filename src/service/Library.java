package service;

import entity.*;

import java.util.*;

public class Library {
    private Map<Long, Book> books;
    private Map<Long, Reader> readers;
    private Map<Long, Author> authors;
    private Map<Category, List<Book>> categoryBooks;
    private Map<Book, Reader> borrowedBooks;

    public Library() {
        this.books = new HashMap<>();
        this.readers = new HashMap<>();
        this.authors = new HashMap<>();
        this.categoryBooks = new HashMap<>();
        this.borrowedBooks = new HashMap<>();
    }

    public void addBook(Book book) {
        books.put(book.getId(), book);
        categoryBooks.computeIfAbsent(book.getCategory(), c -> new ArrayList<>()).add(book);
    }

    public void removeBook(Long bookId) {
        books.remove(bookId);
    }

    public void updateBook(Long bookId, Book updateBook) {
        books.put(bookId, updateBook);
    }

    public Book findById(Long bookId) {
        return books.get(bookId);
    }

    public Book findBookByName( String name) {
        for (Map.Entry<Long, Book> entry : books.entrySet()) {
            if (entry.getValue().getName().equals(name)) {
                return entry.getValue();
            }
        }
        return null;
    }

    public List<Book> findBooksByCategory(Category category) {
        return categoryBooks.getOrDefault(category, new ArrayList<>());
    }


    public List<Book> findBooksByAuthor(Long authorId) {
        Author author = authors.get(authorId);
        if (author == null) {
            return  new ArrayList<>();
        }
        return new ArrayList<>(author.getBooks());
    }

    public List<Book> findBooksByReader(Long readerId) {
        Reader reader = readers.get(readerId);
        if (reader == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(reader.getBorrowedBooks());
    }

    public Reader findReaderById(Long bookId) {
        return readers.get(bookId);
    }

    public Author findAuthorById(Long authorId) {
        return authors.get(authorId);
    }


    public Boolean borrowBook(Long bookId, Long readerId) {
        Book book = books.get(bookId);
        Reader reader = readers.get(readerId);

        if (book != null && reader != null && book.getStatus().equals(Status.AVAILABLE) && reader.canBorrowMore()) {
            book.setStatus(Status.BORROWED);
            reader.getBorrowedBooks().add(book);
            generateBill(book, reader);
            return true;
        }
        return false;
    }

    private void generateBill(Book book, Reader reader) {
        Double rent = book.getPrice() * 0.1;
        reader.setBalance(reader.getBalance() - rent);
    }

    public boolean returnBook(Long bookId, Long readerId) {
        Book book = books.get(bookId);
        Reader reader = readers.get(readerId);

        if (book != null && reader != null && borrowedBooks.get(book) == reader) {
            book.setStatus(Status.AVAILABLE);
            reader.getBorrowedBooks().remove(book);
            borrowedBooks.remove(book);
            refundBill(book, reader);
            return true;
        }
        return false;
    }

    private void refundBill(Book book, Reader reader) {
        Double rent = book.getPrice() * 0.1;
        reader.setBalance(reader.getBalance() + rent);
    }

}
