package org.howard.edu.lsp.midterm.crccards;

/**
 * Stores a book's identifying information and validates its lending state.
 * @author Nyla Crespo
 */
public class Book {
    private final String isbn;
    private final String title;
    private final String author;
    private boolean available;

    /**
     * Creates an available book.
     * @param isbn the book's ISBN
     * @param title the book's title
     * @param author the book's author
     */
    public Book(String isbn, String title, String author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.available = true;
    }

    /**
     * Gets the ISBN.
     * @return the ISBN
     */
    public String getIsbn() { return isbn; }

    /**
     * Gets the title.
     * @return the title
     */
    public String getTitle() { return title; }

    /**
     * Gets the author.
     * @return the author
     */
    public String getAuthor() { return author; }

    /**
     * Reports whether this book can be borrowed.
     * @return true when available, false when borrowed
     */
    public boolean isAvailable() { return available; }

    /**
     * Marks this book as borrowed.
     * @throws IllegalStateException if the book is already borrowed
     */
    public void borrowBook() {
        if (!available) {
            throw new IllegalStateException("Book is already borrowed.");
        }
        available = false;
    }

    /**
     * Marks this book as available.
     * @throws IllegalStateException if the book is already available
     */
    public void returnBook() {
        if (available) {
            throw new IllegalStateException("Book is already available.");
        }
        available = true;
    }

    /**
     * Describes this book and its availability.
     * @return ISBN, title, author, and bracketed lending state
     */
    @Override
    public String toString() {
        return isbn + " " + title + " by " + author
                + (available ? " [AVAILABLE]" : " [BORROWED]");
    }
}
