package models;

public class Book {
    private String id;
    private String title;
    private String author;
    private String category;
    private boolean isBorrowed;

    public Book(String id, String title, String author, String category) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;

    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getCategory() { return category; }

    // Kitabın ödünç alınıp alınmadığını kontrol et
    public boolean isBorrowed() {
        return isBorrowed;
    }

    // Kitap ödünç alındığında çağrılacak metot
    public void borrowBook() {
        isBorrowed = true;
    }

    // Kitap iade edildiğinde çağrılacak metot
    public void returnBook() {
        isBorrowed = false;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "[" + id + "] " + title + " by " + author + " (" + category + ")";
    }
}
