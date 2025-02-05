package models;

import java.util.HashSet;
import java.util.Set;

public class User {
    private String userId;
    private String name;
    private String password;
    private Set<Book> borrowedBooks = new HashSet<>();

    // Constructor
    public User(String userId, String name, String password) {
        this.userId = userId;
        this.name = name;
        this.password = password;

    }
    public static final int BORROW_LIMIT = 5;


    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public Set<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }


    public void borrowBook(Book book) {
        borrowedBooks.add(book);
    }

    public void returnBook(Book book) {
        borrowedBooks.remove(book);
    }
}
