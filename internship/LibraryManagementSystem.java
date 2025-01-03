import java.io.*;
import java.util.*;

class Book implements Serializable {
    private int id;
    private String title;
    private String author;
    private boolean isBorrowed;

    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isBorrowed = false;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public void borrowBook() {
        isBorrowed = true;
    }

    public void returnBook() {
        isBorrowed = false;
    }

    @Override
    public String toString() {
        return "Book ID: " + id + ", Title: " + title + ", Author: " + author + ", Borrowed: " + isBorrowed;
    }
}

class Library {
    private List<Book> books = new ArrayList<>();
    private final String filePath = "library_data.ser";

    public Library() {
        loadBooksFromFile();
    }

    public void addBook(Book book) {
        books.add(book);
        saveBooksToFile();
    }

    public void removeBook(int id) {
        books.removeIf(book -> book.getId() == id);
        saveBooksToFile();
    }

    public void listBooks() {
        if (books.isEmpty()) {
            System.out.println("No books available.");
            return;
        }
        books.forEach(System.out::println);
    }

    public void borrowBook(int id) {
        for (Book book : books) {
            if (book.getId() == id && !book.isBorrowed()) {
                book.borrowBook();
                System.out.println("You borrowed: " + book.getTitle());
                saveBooksToFile();
                return;
            }
        }
        System.out.println("Book not available or already borrowed.");
    }

    public void returnBook(int id) {
        for (Book book : books) {
            if (book.getId() == id && book.isBorrowed()) {
                book.returnBook();
                System.out.println("You returned: " + book.getTitle());
                saveBooksToFile();
                return;
            }
        }
        System.out.println("Invalid return request.");
    }

    private void saveBooksToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(books);
        } catch (IOException e) {
            System.out.println("Error saving books: " + e.getMessage());
        }
    }

    private void loadBooksFromFile() {
        File file = new File(filePath);
        if (!file.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            books = (List<Book>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading books: " + e.getMessage());
        }
    }
}

public class LibraryManagementSystem {
    public static void main(String[] args) {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nLibrary Management System");
            System.out.println("1. Add Book");
            System.out.println("2. Remove Book");
            System.out.println("3. List Books");
            System.out.println("4. Borrow Book");
            System.out.println("5. Return Book");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter Book ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter Book Title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter Book Author: ");
                    String author = scanner.nextLine();
                    library.addBook(new Book(id, title, author));
                    System.out.println("Book added successfully!");
                }
                case 2 -> {
                    System.out.print("Enter Book ID to remove: ");
                    int id = scanner.nextInt();
                    library.removeBook(id);
                    System.out.println("Book removed successfully!");
                }
                case 3 -> library.listBooks();
                case 4 -> {
                    System.out.print("Enter Book ID to borrow: ");
                    int id = scanner.nextInt();
                    library.borrowBook(id);
                }
                case 5 -> {
                    System.out.print("Enter Book ID to return: ");
                    int id = scanner.nextInt();
                    library.returnBook(id);
                }
                case 6 -> {
                    System.out.println("Exiting the system...");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
