import entity.Author;
import entity.Book;
import entity.Category;
import entity.Status;
import service.Library;
import util.InputHandler;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static Library library = new Library();


    public static void main(String[] args) {
        while (true) {
            //displayMainMenu();
            int choice = InputHandler.getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    searchBook();
                    break;
                case 3:
                    updateBook();
                    break;
                case 4:
                    //deleteBook();
                    break;
                case 5:
                    //listBooksByCategory();
                    break;
                case 6:
                    //listBooksByAuthor();
                    break;
                case 7:
                    //borrowBook();
                    break;
                case 8:
                    //returnBook();
                    break;
                case 9:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }


    private static String addBook() {

        System.out.println("\n=== Add New Book ===");
        Long bookId = InputHandler.getLongInput("Enter Book ID: ");
        String name = InputHandler.getStringInput("Enter Book Name: ");
        Long authorId = InputHandler.getLongInput("Enter Author ID: ");
        Double price = InputHandler.getDoubleInput("Enter Book Price: ");
        Category category = InputHandler.getCategoryInput("Enter Category: ");
        Status status = InputHandler.getStatusInput("Enter Status: ");

        Author author = library.findAuthorById(authorId);
        if (author == null) {
            String authorName = InputHandler.getStringInput("Author not found. Enter Author Name: ");
            String authorEmail = InputHandler.getStringInput("Enter Author Email: ");
            author = new Author(authorId, authorName, authorEmail);
            library.addAuthor(author);
        }
        Book book = new Book(bookId, name, author, price, category, status);
        library.addBook(book);
        return ("Book added to library");
    }

    private static String searchBook() {
        System.out.println("\n=== Search Book ===");
        System.out.println("1. Search by ID");
        System.out.println("2. Search by Name");
        int choice = InputHandler.getIntInput("Enter your choice: ");

        switch (choice) {
            case 1:
                Long bookId = InputHandler.getLongInput("Enter Book ID: ");
                Book bookByID = library.findById(bookId);
                if (bookByID != null) {
                    return bookByID.toString();
                } else {
                    return "Book does not exist";
                }
            case 2:
                String name = InputHandler.getStringInput("Enter Book Name: ");
                Book bookByName = library.findBookByName(name);
                if (bookByName != null) {
                    return bookByName.toString();
                } else {
                    return "Book does not exist";
                }
        }
        return ("Book does not exist");
    }

    private static String updateBook() {
        System.out.println("\n=== Update Book ===");
        Long bookId = InputHandler.getLongInput("Enter Book ID: ");
        if (!library.getBooks().containsKey(bookId)) {
            return "Book does not exist";
        }

        // Retrieve the existing book
        Book existingBook = library.getBooks().get(bookId);

        // Get new values
        String name = InputHandler.getStringInput("Enter Book Name (leave blank to keep current): ");
        if (!name.isEmpty()) {
            existingBook.setName(name);
        }

        Long authorId = InputHandler.getLongInput("Enter Author ID: ");
        Author author = library.findAuthorById(authorId);
        if (author == null) {
            String authorName = InputHandler.getStringInput("Author not found. Enter Author Name: ");
            String authorEmail = InputHandler.getStringInput("Enter Author Email: ");
            author = new Author(authorId, authorName, authorEmail);
            library.addAuthor(author);
        }
        existingBook.setAuthor(author);

        Double price = InputHandler.getDoubleInput("Enter Book Price: ");
        existingBook.setPrice(price);

        Category category = InputHandler.getCategoryInput("Enter Category: ");
        existingBook.setCategory(category);

        Status status = InputHandler.getStatusInput("Enter Status: ");
        existingBook.setStatus(status);

        return "Book updated in library";
    }

}