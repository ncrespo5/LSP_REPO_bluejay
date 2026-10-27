package org.howard.edu.lsp.midterm.crccards;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Maintains unique books and coordinates lending with a three-book limit.
 * Books belonging to this library are borrowed and returned through it.
 * @author Nyla Crespo
 */
public class Library {
    private static final int BORROWING_LIMIT = 3;
    private final Map<String, Book> books = new HashMap<>();

    /** Creates an empty library. */
    public Library() { }

    /**
     * Adds a book without replacing an existing ISBN.
     * @param book the book to add
     * @throws IllegalArgumentException if the book is null, its ISBN already
     *     exists, or adding a borrowed book would exceed the lending limit
     */
    public void addBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book must not be null.");
        }
        if (books.containsKey(book.getIsbn())) {
            throw new IllegalArgumentException("Duplicate ISBN.");
        }
        if (!book.isAvailable() && getBorrowedBookCount() >= BORROWING_LIMIT) {
            throw new IllegalArgumentException("Adding book would exceed borrowing limit.");
        }
        books.put(book.getIsbn(), book);
    }

    /**
     * Finds a book by its ISBN.
     * @param isbn the ISBN to find
     * @return the stored Book instance, or null if absent
     */
    public Book findBook(String isbn) { return books.get(isbn); }

    /**
     * Locates and borrows a book after checking the library limit.
     * @param isbn the ISBN to borrow
     * @throws IllegalArgumentException if the ISBN is absent
     * @throws IllegalStateException if already borrowed or three books are borrowed
     */
    public void borrowBook(String isbn) {
        Book book = requireBook(isbn);
        if (getBorrowedBookCount() >= BORROWING_LIMIT) {
            throw new IllegalStateException("Three books are already borrowed.");
        }
        book.borrowBook();
    }

    /**
     * Locates a book and delegates its return to that book.
     * @param isbn the ISBN to return
     * @throws IllegalArgumentException if the ISBN is absent
     * @throws IllegalStateException if the book is already available
     */
    public void returnBook(String isbn) { requireBook(isbn).returnBook(); }

    /**
     * Retrieves the available books.
     * @return a new list of available books, empty if none are available
     */
    public List<Book> getAvailableBooks() {
        List<Book> result = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.isAvailable()) { result.add(book); }
        }
        return result;
    }

    /**
     * Retrieves books with exactly the given author, including borrowed books.
     * @param author the case-sensitive author name
     * @return a new list of matching books, empty if none match
     */
    public List<Book> getBooksByAuthor(String author) {
        List<Book> result = new ArrayList<>();
        for (Book book : books.values()) {
            if (Objects.equals(author, book.getAuthor())) { result.add(book); }
        }
        return result;
    }

    /**
     * Retrieves all books in ascending lexicographical ISBN order.
     * @return a new sorted list, empty if the library is empty
     */
    public List<Book> getBooksSortedByIsbn() {
        List<Book> result = new ArrayList<>(books.values());
        result.sort(Comparator.comparing(Book::getIsbn));
        return result;
    }

    /**
     * Counts the books currently borrowed from this library.
     * @return the current number of borrowed books
     */
    public int getBorrowedBookCount() {
        int count = 0;
        for (Book book : books.values()) {
            if (!book.isAvailable()) { count++; }
        }
        return count;
    }

    private Book requireBook(String isbn) {
        Book book = findBook(isbn);
        if (book == null) {
            throw new IllegalArgumentException("ISBN is not in the library.");
        }
        return book;
    }
}
