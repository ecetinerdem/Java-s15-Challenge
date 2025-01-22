package entity;

import java.time.LocalDate;

public class Book {
    private Long id;
    private String name;
    private Author author;
    private Double price;
    private Status status;
    private LocalDate purchaseDate;
    private LocalDate returnDate;
    private Category category;

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", author=" + author +
                ", category=" + category +
                ", status=" + status +
                ", price=" + price +
                ", id=" + id +
                '}';
    }

    public Book(Long id, String name, Author author, Double price, Category category, Status status) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.price = price;
        this.category = category;
        this.status = status;
    }

    public Book(Long id, String name, Author author, Double price, Status status, LocalDate purchaseDate, LocalDate returnDate, Category category) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.price = price;
        this.status = status;
        this.purchaseDate = purchaseDate;
        this.returnDate = returnDate;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
