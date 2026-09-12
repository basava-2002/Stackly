
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Book {

    String bookId;
    String title;
    String author;
    boolean available;

    Book(String bookId, String title, String author) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.available = true;
    }

    void displayBook() {
        System.out.println("Book ID   : " + bookId);
        System.out.println("Title     : " + title);
        System.out.println("Author    : " + author);
        System.out.println("Available : " + (available ? "Yes" : "No"));
        System.out.println("-----------------------------");
    }
}

public class LibraryManagementSystem {

    // ArrayList to store all books
    static ArrayList<Book> books = new ArrayList<>();

    // HashMap to store issued books
    // Key = Student ID
    // Value = Book
    static Map<String, Book> issuedBooks = new HashMap<>();

    // Store issue date
    static Map<String, LocalDate> issueDates = new HashMap<>();

    // Store due date
    static Map<String, LocalDate> dueDates = new HashMap<>();

    static Scanner sc = new Scanner(System.in);

    // Fine per day
    static final double FINE_PER_DAY = 5.0;

    // Add Book
    static void addBook() {

    	books.add(new Book("101", "Java Programming", "James Gosling"));
    	books.add(new Book("102", "Python Basics", "Mark Lutz"));
    	books.add(new Book("103", "Data Structures", "Robert Lafore"));
    	books.add(new Book("104", "Database Management", "Raghu Ramakrishnan"));
    	books.add(new Book("105", "Computer Networks", "Andrew Tanenbaum"));

    	System.out.println("Books added successfully.");

    }

    // Search Book
    static void searchBook() {

        System.out.print("Enter book title or ID to search: ");
        String search = sc.nextLine();

        boolean found = false;

        for (Book book : books) {

            if (book.bookId.equalsIgnoreCase(search)
                    || book.title.equalsIgnoreCase(search)) {

                book.displayBook();
                found = true;
            }
        }

        if (!found) {
            System.out.println("Book not found.");
        }
    }

    // Display Available Books
    static void displayAvailableBooks() {

        System.out.println("\n===== AVAILABLE BOOKS =====");

        boolean found = false;

        for (Book book : books) {

            if (book.available) {
                book.displayBook();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No books are currently available.");
        }
    }

    // Librarian Approval
    static boolean librarianApproval() {

        System.out.print("Librarian approval required.");
        System.out.print("\nEnter Yes to approve: ");

        String approval = sc.nextLine();

        return approval.equalsIgnoreCase("Yes");
    }

    // Issue Book
    static void issueBook() {

        System.out.print("Enter Student ID: ");
        String studentId = sc.nextLine();

        System.out.print("Enter Book ID: ");
        String bookId = sc.nextLine();

        Book selectedBook = null;

        for (Book book : books) {

            if (book.bookId.equalsIgnoreCase(bookId)) {
                selectedBook = book;
                break;
            }
        }

        if (selectedBook == null) {
            System.out.println("Book not found.");
            return;
        }

        if (!selectedBook.available) {
            System.out.println("Book is already issued.");
            return;
        }

        if (issuedBooks.containsKey(studentId)) {
            System.out.println("This student already has a book issued.");
            return;
        }

        // Librarian approval
        if (!librarianApproval()) {
            System.out.println("Book issue rejected by librarian.");
            return;
        }

        LocalDate issueDate = LocalDate.now();

        // Book can be kept for 14 days
        LocalDate dueDate = issueDate.plusDays(14);

        selectedBook.available = false;

        //add issue details
        issuedBooks.put(studentId, selectedBook);
        issueDates.put(studentId, issueDate);
        dueDates.put(studentId, dueDate);

        System.out.println("\nBook issued successfully!");
        System.out.println("Issue Date : " + issueDate);
        System.out.println("Due Date   : " + dueDate);
    }

  
    // Fine Calculator
    static double calculateFine(String studentId) {

    	if (!dueDates.containsKey(studentId)) {
    		return 0;
    	}

    	LocalDate dueDate = dueDates.get(studentId);
    	LocalDate today = LocalDate.now();

    	if (today.isAfter(dueDate)) {

    		long lateDays = ChronoUnit.DAYS.between(dueDate, today);

    		double fine = lateDays * FINE_PER_DAY;

    		return fine;
    	}

    	return 0;
    }

 // Fine Collector 
    static void collectFine(String studentId) { 
    	double fine = calculateFine(studentId); 
    	if (fine == 0) { 
    		System.out.println("No fine to collect."); 
    		return; 
    	} 
    	System.out.println("Fine Amount: ₹" + fine); 
    	System.out.print("Enter fine amount paid: ₹"); 
    	double amountPaid = sc.nextDouble(); 
    	sc.nextLine(); 
    	if (amountPaid >= fine) { 
    		double balance = amountPaid - fine; 
    		System.out.println("Fine collected successfully."); 
    		if (balance > 0) { 
    			System.out.println("Return amount: ₹" + balance); 
    		} 
    	} else { 
    		double remaining = fine - amountPaid; 
    		System.out.println("Partial payment received."); 
    		System.out.println("Remaining fine: ₹" + remaining); 
    	} 
    }

    // Return Book
    static void returnBook() {

    	System.out.print("Enter Student ID: ");
    	String studentId = sc.nextLine();
    	
    	if (!issuedBooks.containsKey(studentId)) {
    		System.out.println("No book is issued to this student.");
    		return;
    	}

    	Book book = issuedBooks.get(studentId);

    	System.out.println("\nBook Returned: " + book.title);

    	// Calculate fine
    	double fine = calculateFine(studentId);

    	if (fine > 0) {

    		System.out.println("Fine Amount: ₹" + fine);

    		// Collect fine
    		collectFine(studentId);

    	} else {

    		System.out.println("No fine. Book returned on time.");
    	}

    	// Make book available again
    	book.available = true;

    	// Remove issue details
    	issuedBooks.remove(studentId);
    	issueDates.remove(studentId);
    	dueDates.remove(studentId);

    	System.out.println("Book returned successfully.");
    }


    // Due Date Reminder
    static void dueDateReminder() {

        System.out.print("Enter Student ID: ");
        String studentId = sc.nextLine();

        if (!issuedBooks.containsKey(studentId)) {
            System.out.println("No book is issued to this student.");
            return;
        }

        LocalDate dueDate = dueDates.get(studentId);
        LocalDate today = LocalDate.now();

        long days = ChronoUnit.DAYS.between(today, dueDate);

        System.out.println("Due Date: " + dueDate);

        if (days > 0) {
            System.out.println("Reminder: Book is due in " + days + " days.");
        } else if (days == 0) {
            System.out.println("Reminder: Book is due TODAY!");
        } else {
            System.out.println("Book is overdue by " + Math.abs(days) + " days.");
            System.out.println("Current Fine: ₹" + calculateFine(studentId));
        }
    }

    // Generate Report
    static void generateReport() {

        System.out.println("\n========== LIBRARY REPORT ==========");

        System.out.println("Total Books: " + books.size());

        int available = 0;
        int issued = 0;

        for (Book book : books) {

            if (book.available) {
                available++;
            } else {
                issued++;
            }
        }

        System.out.println("Available Books: " + available);
        System.out.println("Issued Books   : " + issued);

        System.out.println("\n===== ISSUED BOOK DETAILS =====");

        if (issuedBooks.isEmpty()) {
            System.out.println("No books are currently issued.");
        } else {

            for (String studentId : issuedBooks.keySet()) {

                Book book = issuedBooks.get(studentId);

                System.out.println("Student ID : " + studentId);
                System.out.println("Book       : " + book.title);
                System.out.println("Issue Date : " + issueDates.get(studentId));
                System.out.println("Due Date   : " + dueDates.get(studentId));
                System.out.println("Fine       : ₹" + calculateFine(studentId));
                System.out.println("-----------------------------");
            }
        }
    }

    // Main Menu
    public static void main(String[] args) {

    	addBook();
        int choice;

        do {

            System.out.println("\n=================================");
            System.out.println("     LIBRARY MANAGEMENT SYSTEM");
            System.out.println("=================================");

            System.out.println("1. Show Available Book");
            System.out.println("2. Search Books");
            System.out.println("3. Issue Book");
            System.out.println("4. Return Book");
            System.out.println("5. Fine Calculator");
            System.out.println("6. Collect Fine");
            System.out.println("7. Due Date Reminder");
            System.out.println("8. Generate Report");
            System.out.println("9. Exit");

            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                	displayAvailableBooks();
                    break;

                case 2:
                    searchBook();
                    break;

                case 3:
                    issueBook();
                    break;

                case 4:
                    returnBook();
                    break;

                case 5:
                    System.out.print("Enter Student ID: ");
                    String fineStudentId = sc.nextLine();

                    double fine = calculateFine(fineStudentId);

                    if (fine > 0) {
                        System.out.println("Fine Amount: ₹" + fine);
                    } else {
                        System.out.println("No fine for this student.");
                    }
                    break;

                case 6:
                    System.out.print("Enter Student ID: ");
                    String collectStudentId = sc.nextLine();

                    collectFine(collectStudentId);
                    break;

                case 7:
                    dueDateReminder();
                    break;

                case 8:
                    generateReport();
                    break;

                case 9:
                    System.out.println("Thank you for using Library Management System.");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 9);

        sc.close();
    }
}
