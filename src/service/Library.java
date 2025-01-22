package service;

import entity.*;
import java.util.*;

public class Library {
    private List<Book> books;
    private List<Reader> readers;
    private List<Author> authors;
    private Map<Category, List<Book>> categoryBooks;
    private Map<Book, Reader> borrowedBooks;

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public List<Reader> getReaders() {
        return readers;
    }

    public void setReaders(List<Reader> readers) {
        this.readers = readers;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public Map<Category, List<Book>> getCategoryBooks() {
        return categoryBooks;
    }

    public void setCategoryBooks(Map<Category, List<Book>> categoryBooks) {
        this.categoryBooks = categoryBooks;
    }

    public Map<Book, Reader> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(Map<Book, Reader> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    public Library() {
        this.books = new ArrayList<>();
        this.readers = new ArrayList<>();
        this.authors = new ArrayList<>();
        this.categoryBooks = new HashMap<>();
        this.borrowedBooks = new HashMap<>();
    }

    public void addBook(Book book) {
        books.add(book);
        categoryBooks
                .computeIfAbsent(book.getCategory(), c -> new ArrayList<>())
                .add(book);
        book.getAuthor().getBooks().add(book);
    }

    public void removeBook(Long bookId) {
        Book bookToRemove = findById(bookId);
        if (bookToRemove != null) {
            books.remove(bookToRemove);
            categoryBooks.get(bookToRemove.getCategory()).remove(bookToRemove);
            bookToRemove.getAuthor().getBooks().remove(bookToRemove);
            borrowedBooks.remove(bookToRemove);
        }
    }

    public void updateBook(Long bookId, Book updateBook) {
        Book existingBook = findById(bookId);
        if (existingBook != null) {

            categoryBooks.get(existingBook.getCategory()).remove(existingBook);


            int index = books.indexOf(existingBook);
            books.set(index, updateBook);


            categoryBooks
                    .computeIfAbsent(updateBook.getCategory(), c -> new ArrayList<>())
                    .add(updateBook);
        }
    }

    public Book findById(Long bookId) {
        return books.stream()
                .filter(book -> book.getId().equals(bookId))
                .findFirst()
                .orElse(null);
    }

    public Book findBookByName(String name) {
        return books.stream()
                .filter(book -> book.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public List<Book> findBooksByCategory(Category category) {
        return categoryBooks.getOrDefault(category, new ArrayList<>());
    }

    public Set<Book> findBooksByAuthor(Long authorId) {
        Author author = findAuthorById(authorId);
        return author != null ? author.getBooks() : new HashSet<>();
    }

    public List<Book> findBooksByReader(Long readerId) {
        Reader reader = findReaderById(readerId);
        return reader != null ? reader.getBorrowedBooks() : new ArrayList<>();
    }

    public Reader findReaderById(Long readerId) {
        return readers.stream()
                .filter(reader -> reader.getId().equals(readerId))
                .findFirst()
                .orElse(null);
    }

    public void addAuthor(Author author) {
        if (!authors.contains(author)) {
            authors.add(author);
        }
    }

    public Author findAuthorById(Long authorId) {
        return authors.stream()
                .filter(author -> author.getId().equals(authorId))
                .findFirst()
                .orElse(null);
    }

    public Boolean borrowBook(Long bookId, Long readerId) {
        Book book = findById(bookId);
        Reader reader = findReaderById(readerId);

        if (book != null && reader != null &&
                book.getStatus().equals(Status.AVAILABLE) &&
                reader.canBorrowMore()) {

            book.setStatus(Status.BORROWED);
            reader.getBorrowedBooks().add(book);
            borrowedBooks.put(book, reader);
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
        Book book = findById(bookId);
        Reader reader = findReaderById(readerId);

        if (book != null && reader != null &&
                borrowedBooks.get(book) == reader &&
                book.getStatus().equals(Status.BORROWED)) {

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