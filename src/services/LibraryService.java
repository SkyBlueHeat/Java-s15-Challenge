import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import models.Book;
import models.User;
import models.Invoice;

public class LibraryService {
    private Map<String, Book> books = new HashMap<>();
    private Set<Invoice> invoices = new HashSet<>();

    // Kitap ekleme
    public void addBook(Book book) {
        books.put(book.getId(), book);
        System.out.println("Kitap başarıyla eklendi: " + book);
    }

    // Kitap silme
    public void removeBook(String id) {
        if (books.containsKey(id)) {
            books.remove(id);
            System.out.println("Kitap başarıyla silindi.");
        } else {
            System.out.println("Kitap bulunamadı.");
        }
    }

    // Tüm kitapları listeleme
    public void listBooks() {
        if (books.isEmpty()) {
            System.out.println("Kütüphanede hiç kitap yok.");
        } else {
            for (Book book : books.values()) {
                System.out.println(book);
            }
        }
    }

    // Kitap ID ile seçme
    public Book searchBookById(String bookId) {
        return books.get(bookId);
    }

    // Kitap ismi ile seçme
    public List<Book> searchBooksByTitle(String title) {
        List<Book> result = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.getTitle().contains(title)) {
                result.add(book);
            }
        }
        return result;
    }

    // Kitap yazarı ile seçme
    public List<Book> searchBooksByAuthor(String author) {
        List<Book> result = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.getAuthor().contains(author)) {
                result.add(book);
            }
        }
        return result;
    }

    // Fatura ekleme
    public void addInvoice(Invoice invoice) {
        invoices.add(invoice);
    }

    // Kitap güncelleme
    public void updateBook(String bookId, String newTitle, String newAuthor, String newCategory) {
        Book book = books.get(bookId);
        if (book != null) {
            book.setTitle(newTitle);
            book.setAuthor(newAuthor);
            book.setCategory(newCategory);
            System.out.println("Kitap başarıyla güncellendi: " + book);
        } else {
            System.out.println("Kitap bulunamadı.");
        }
    }

    // Yazara ait kitapları listeleme
    public void listBooksByAuthor(String author) {
        List<Book> booksByAuthor = searchBooksByAuthor(author);
        if (booksByAuthor.isEmpty()) {
            System.out.println("Yazar " + author + " tarafından yazılmış kitap bulunamadı.");
        } else {
            for (Book book : booksByAuthor) {
                System.out.println(book);
            }
        }
    }

    // Kategoriye göre kitapları listeleme
    public void listBooksByCategory(String category) {
        List<Book> booksByCategory = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.getCategory().equalsIgnoreCase(category)) {
                booksByCategory.add(book);
            }
        }
        if (booksByCategory.isEmpty()) {
            System.out.println("Kategori " + category + " altında kitap bulunamadı.");
        } else {
            for (Book book : booksByCategory) {
                System.out.println(book);
            }
        }
    }

    // Kitap ödünç alma
    public void borrowBook(User user, String bookId) {
        if (user.getBorrowedBooks().size() >= User.BORROW_LIMIT) {
            System.out.println("Kitap ödünç alma limitine ulaştınız. Lütfen iade yapın.");
            return;
        }

        Book book = books.get(bookId);
        if (book != null && !book.isBorrowed()) {
            book.borrowBook();
            user.getBorrowedBooks().add(book);
            System.out.println(user.getName() + " kitabı ödünç aldı: " + book);
        } else if (book == null) {
            System.out.println("Kitap bulunamadı.");
        } else {
            System.out.println("Kitap zaten ödünç alınmış.");
        }
    }

    // Kitap iade etme
    public void returnBook(User user, String bookId) {
        Book book = books.get(bookId);
        if (book != null && book.isBorrowed()) {
            book.returnBook();
            user.getBorrowedBooks().remove(book);
            System.out.println(user.getName() + " kitabı geri iade etti: " + book);
        } else if (book == null) {
            System.out.println("Kitap bulunamadı.");
        } else {
            System.out.println("Bu kitap ödünç alınmamış.");
        }
    }

    // Fatura oluşturma
    public void generateInvoice(User user, Book book) {
        String invoiceId = "INV" + System.currentTimeMillis();
        double amount = 10.0;
        Invoice invoice = new Invoice(invoiceId, user.getUserId(), book.getId(), amount);
        invoices.add(invoice);
        System.out.println("Fatura kesildi: " + invoice);
    }
}
