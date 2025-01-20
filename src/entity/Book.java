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
}
