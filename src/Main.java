import entity.Author;
import entity.Book;
import entity.Category;
import entity.Status;
import service.Library;
import util.InputHandler;

import java.util.List;
import java.util.Set;

public class Main {
    private static Library library = new Library();

    public static void main(String[] args) {
        while (true) {
            displayMainMenu();
            int choice = InputHandler.getIntInput("Enter your choice: ");

            try {
                switch (choice) {
                    case 1:
                        System.out.println(addBook());
                        break;
                    case 2:
                        System.out.println(searchBook());
                        break;
                    case 3:
                        System.out.println(updateBook());
                        break;
                    case 4:
                        System.out.println(deleteBook());
                        break;
                    case 5:
                        System.out.println(listBooksByCategory());
                        break;
                    case 6:
                        System.out.println(listBooksByAuthor());
                        break;
                    case 7:
                        System.out.println(borrowBook());
                        break;
                    case 8:
                        System.out.println(returnBook());
                        break;
                    case 9:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid choice!");
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }

    private static void displayMainMenu() {
        System.out.println("\n=== Library Management System ===");
        System.out.println("1. Add New Book");
        System.out.println("2. Search Book");
        System.out.println("3. Update Book");
        System.out.println("4. Delete Book");
        System.out.println("5. List Books by Category");
        System.out.println("6. List Books by Author");
        System.out.println("7. Borrow Book");
        System.out.println("8. Return Book");
        System.out.println("9. Exit");
    }

    private static String addBook() {
        System.out.println("\n=== Add New Book ===");
        Long bookId = InputHandler.getLongInput("Enter Book ID: ");

        // Check if book ID already exists
        if (library.findById(bookId) != null) {
            return "Error: Book with this ID already exists";
        }

        String name = InputHandler.getStringInput("Enter Book Name: ");
        if (name.isEmpty()) {
            return "Error: Book name cannot be empty";
        }

        Long authorId = InputHandler.getLongInput("Enter Author ID: ");
        Double price = InputHandler.getDoubleInput("Enter Book Price: ");
        if (price <= 0) {
            return "Error: Price must be greater than 0";
        }

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
        return "Book added successfully: " + book;
    }

    private static String searchBook() {
        System.out.println("\n=== Search Book ===");
        System.out.println("1. Search by ID");
        System.out.println("2. Search by Name");
        int choice = InputHandler.getIntInput("Enter your choice: ");

        switch (choice) {
            case 1:
                Long bookId = InputHandler.getLongInput("Enter Book ID: ");
                Book bookById = library.findById(bookId);
                return bookById != null ? "Found: " + bookById : "Book not found";
            case 2:
                String name = InputHandler.getStringInput("Enter Book Name: ");
                Book bookByName = library.findBookByName(name);
                return bookByName != null ? "Found: " + bookByName : "Book not found";
            default:
                return "Invalid choice";
        }
    }

    private static String updateBook() {
        System.out.println("\n=== Update Book ===");
        Long bookId = InputHandler.getLongInput("Enter Book ID: ");
        Book existingBook = library.findById(bookId);

        if (existingBook == null) {
            return "Book not found";
        }

        String name = InputHandler.getStringInput("Enter new Book Name (press Enter to skip): ");
        if (!name.isEmpty()) {
            existingBook.setName(name);
        }

        String authorIdInput = InputHandler.getStringInput("Enter new Author ID (press Enter to skip): ");
        if (!authorIdInput.isEmpty()) {
            Long authorId = Long.parseLong(authorIdInput);
            Author author = library.findAuthorById(authorId);
            if (author == null) {
                String authorName = InputHandler.getStringInput("Author not found. Enter Author Name: ");
                String authorEmail = InputHandler.getStringInput("Enter Author Email: ");
                author = new Author(authorId, authorName, authorEmail);
                library.addAuthor(author);
            }
            existingBook.setAuthor(author);
        }

        String priceInput = InputHandler.getStringInput("Enter new Book Price (press Enter to skip): ");
        if (!priceInput.isEmpty()) {
            Double price = Double.parseDouble(priceInput);
            if (price > 0) {
                existingBook.setPrice(price);
            }
        }

        Category category = InputHandler.getCategoryInput("Enter new Category: ");
        existingBook.setCategory(category);

        Status status = InputHandler.getStatusInput("Enter new Status: ");
        existingBook.setStatus(status);

        library.updateBook(bookId, existingBook);
        return "Book updated successfully: " + existingBook;
    }

    private static String deleteBook() {
        System.out.println("\n=== Delete Book ===");
        Long bookId = InputHandler.getLongInput("Enter book ID: ");
        Book book = library.findById(bookId);

        if (book == null) {
            return "Book not found";
        }

        if (book.getStatus() == Status.BORROWED) {
            return "Cannot delete borrowed book";
        }

        library.removeBook(bookId);
        return "Book successfully deleted: " + bookId;
    }

    private static String listBooksByCategory() {
        System.out.println("\n=== List Books by Category ===");
        Category category = InputHandler.getCategoryInput("Enter Category: ");
        List<Book> books = library.findBooksByCategory(category);

        if (books.isEmpty()) {
            return "No books found in category: " + category;
        }

        StringBuilder result = new StringBuilder("Books in category " + category + ":\n");
        for (Book book : books) {
            result.append("- ").append(book).append("\n");
        }
        return result.toString();
    }

    private static String listBooksByAuthor() {
        System.out.println("\n=== List Books by Author ===");
        Long authorId = InputHandler.getLongInput("Enter Author ID: ");
        Set<Book> books = library.findBooksByAuthor(authorId);

        if (books == null || books.isEmpty()) {
            return "No books found for author ID: " + authorId;
        }

        StringBuilder result = new StringBuilder("Books by author " + authorId + ":\n");
        for (Book book : books) {
            result.append("- ").append(book).append("\n");
        }
        return result.toString();
    }

    private static String borrowBook() {
        System.out.println("\n=== Borrow Book ===");
        Long bookId = InputHandler.getLongInput("Enter Book ID: ");
        Long readerId = InputHandler.getLongInput("Enter Reader ID: ");

        if (library.borrowBook(bookId, readerId)) {
            return "Book successfully borrowed: " + bookId;
        } else {
            return "Failed to borrow book. Please verify:\n" +
                    "- Book exists and is available\n" +
                    "- Reader exists and can borrow more books\n" +
                    "- Reader has sufficient balance";
        }
    }

    private static String returnBook() {
        System.out.println("\n=== Return Book ===");
        Long bookId = InputHandler.getLongInput("Enter Book ID: ");
        Long readerId = InputHandler.getLongInput("Enter Reader ID: ");

        if (library.returnBook(bookId, readerId)) {
            return "Book successfully returned: " + bookId;
        } else {
            return "Failed to return book. Please verify:\n" +
                    "- Book exists and is borrowed\n" +
                    "- Reader exists and has borrowed this book";
        }
    }
}