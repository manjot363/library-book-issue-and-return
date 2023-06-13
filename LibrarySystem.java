import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Book {
    private int bookId;
    private String title;
    private String author;
    private boolean isAvailable;
    private String issuedTo;
    private int issuedById;

    public Book(int bookId, String title, String author) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.isAvailable = true;
        this.issuedTo = "Not issued";
        this.issuedById = -1;
    }

    public int getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public String getIssuedTo() {
        return issuedTo;
    }

    public void setIssuedTo(String issuedTo) {
        this.issuedTo = issuedTo;
    }

    public int getIssuedById() {
        return issuedById;
    }

    public void setIssuedById(int issuedById) {
        this.issuedById = issuedById;
    }
}

class Library {
    private List<Book> books;
    private Map<Integer, String> issuedBooks;
    private Map<Integer, String> issuedByNames;
    private List<BookRecord> bookRecords;

    public Library() {
        this.books = new ArrayList<>();
        this.issuedBooks = new HashMap<>();
        this.issuedByNames = new HashMap<>();
        this.bookRecords = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void displayBooks() {
        System.out.println("Library Books:");
        for (Book book : books) {
            System.out.println("Book ID: " + book.getBookId());
            System.out.println("Title: " + book.getTitle());
            System.out.println("Author: " + book.getAuthor());
            System.out.println("Availability: " + (book.isAvailable() ? "Available" : "Not Available"));
            System.out.println("Issued To: " + book.getIssuedTo());
            System.out.println();
        }
    }

    public void issueBook(int bookId, int issuedById, String issuedByName) {
        for (Book book : books) {
            if (book.getBookId() == bookId) {
                if (book.isAvailable()) {
                    book.setAvailable(false);
                    book.setIssuedTo(issuedByName);
                    book.setIssuedById(issuedById);
                    issuedBooks.put(bookId, issuedByName);
                    issuedByNames.put(issuedById, issuedByName);
                    BookRecord record = new BookRecord(bookId, book.getTitle(), issuedByName, issuedById, "Issued");
                    bookRecords.add(record);
                    System.out.println("Book with ID " + bookId + " has been issued to " + issuedByName + ".");
                } else {
                    System.out.println("Book with ID " + bookId + " is not available for issuing.");
                }
                return;
            }
        }
        System.out.println("Book with ID " + bookId + " does not exist in the library.");
    }

    public void returnBook(int bookId) {
        for (Book book : books) {
            if (book.getBookId() == bookId) {
                if (!book.isAvailable()) {
                    book.setAvailable(true);
                    String issuedTo = book.getIssuedTo();
                    int issuedById = book.getIssuedById();
                    issuedBooks.remove(bookId);
                    issuedByNames.remove(issuedById);
                    book.setIssuedTo("Not issued");
                    book.setIssuedById(-1);
                    BookRecord record = new BookRecord(bookId, book.getTitle(), issuedTo, issuedById, "Returned");
                    bookRecords.add(record);
                    System.out.println("Book with ID " + bookId + " has been returned by " + issuedTo + ".");
                } else {
                    System.out.println("Book with ID " + bookId + " is already available.");
                }
                return;
            }
        }
        System.out.println("Book with ID " + bookId + " does not exist in the library.");
    }
    
    public void displayIssuedBooks() {
        System.out.println("Issued Books:");
        for (Map.Entry<Integer, String> entry : issuedBooks.entrySet()) {
            int bookId = entry.getKey();
            String issuedTo = entry.getValue();
            int issuedById = issuedByNames.keySet().stream().filter(key -> issuedByNames.get(key).equals(issuedTo)).findFirst().orElse(-1);
            System.out.println("Book ID: " + bookId);
            System.out.println("Issued To: " + issuedTo);
            System.out.println("Issued By ID: " + issuedById);
            System.out.println();
        }
    }

    public void displayBookRecords() {
        System.out.println("Book Records:");
        for (BookRecord record : bookRecords) {
            System.out.println("Book ID: " + record.getBookId());
            System.out.println("Title: " + record.getBookTitle());
            System.out.println("Issued To: " + record.getIssuedTo());
            System.out.println("Issued By ID: " + record.getIssuedById());
            System.out.println("Action: " + record.getAction());
            System.out.println();
        }
    }
}

class BookRecord {
    private int bookId;
    private String bookTitle;
    private String issuedTo;
    private int issuedById;
    private String action;

    public BookRecord(int bookId, String bookTitle, String issuedTo, int issuedById, String action) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.issuedTo = issuedTo;
        this.issuedById = issuedById;
        this.action = action;
    }

    public int getBookId() {
        return bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getIssuedTo() {
        return issuedTo;
    }

    public int getIssuedById() {
        return issuedById;
    }

    public String getAction() {
        return action;
    }
}

public class LibrarySystem {
    public static void main(String[] args) {
        Library library = new Library();

        // Adding some books to the library
        library.addBook(new Book(1, "Java Programming", "John Doe"));
        library.addBook(new Book(2, "Python for Beginners", "Jane Smith"));
        library.addBook(new Book(3, "Data Structures and Algorithms", "Alice Johnson"));

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Display all books");
            System.out.println("2. Issue a book");
            System.out.println("3. Return a book");
            System.out.println("4. Display issued books");
            System.out.println("5. Display book records");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    library.displayBooks();
                    break;
                case 2:
                    System.out.print("Enter the book ID to issue: ");
                    int issueBookId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter the name of the person issuing the book: ");
                    String issuedByName = scanner.nextLine();
                    System.out.print("Enter the ID of the person issuing the book: ");
                    int issuedById = scanner.nextInt();
                    library.issueBook(issueBookId, issuedById, issuedByName);
                    break;
                case 3:
                    System.out.print("Enter the book ID to return: ");
                    int returnBookId = scanner.nextInt();
                    library.returnBook(returnBookId);
                    break;
                case 4:
                    library.displayIssuedBooks();
                    break;
                case 5:
                    library.displayBookRecords();
                    break;
                case 6:
                    System.out.println("Exiting the program...");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }
}
