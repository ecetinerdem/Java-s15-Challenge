import entity.Book;
import service.Library;
import util.InputHandler;

import java.util.Scanner;

public class Main {

    private static Library library = new Library();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            displayMainMenu();
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
                    deleteBook();
                    break;
                case 5:
                    listBooksByCategory();
                    break;
                case 6:
                    listBooksByAuthor();
                    break;
                case 7:
                    borrowBook();
                    break;
                case 8:
                    returnBook();
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
        library.addBook(book);
        return ("Book added to library");
    }


}