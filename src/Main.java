

import models.Book;
import models.User;
import models.Invoice;
import services.UserService;
import services.AuthService;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LibraryService library = new LibraryService();
        AuthService authService = new AuthService();
        User loggedInUser = null;

        // Login/Register
        while (loggedInUser == null) {
            System.out.println("\n=== GİRİŞ EKRANI ===");
            System.out.println("1 - Giriş Yap");
            System.out.println("2 - Kayıt Ol");
            System.out.println("3 - Çıkış");
            System.out.print("Seçiminizi yapın: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("E-posta: ");
                    String email = scanner.nextLine();
                    System.out.print("Şifre: ");
                    String password = scanner.nextLine();
                    loggedInUser = authService.login(email, password);
                    if (loggedInUser == null) {
                        System.out.println("Geçersiz giriş bilgileri. Tekrar deneyin.");
                    }
                    break;
                case 2:
                    System.out.print("Adınız: ");
                    String name = scanner.nextLine();
                    System.out.print("E-posta: ");
                    String newEmail = scanner.nextLine();
                    System.out.print("Şifre: ");
                    String newPassword = scanner.nextLine();
                    authService.register(name, newEmail, newPassword);
                    System.out.println("Kayıt başarılı! Giriş yapabilirsiniz.");
                    break;
                case 3:
                    System.out.println("Çıkış yapılıyor...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Geçersiz seçim. Tekrar deneyin.");
            }
        }

        // Ana menü
        while (true) {
            System.out.println("\n=== KÜTÜPHANE SİSTEMİ ===");
            System.out.println("1 - Kitap Ekle");
            System.out.println("2 - Kitapları Listele");
            System.out.println("3 - Kitap Sil");
            System.out.println("4 - Kitapları ID, İsim veya Yazar ile Seç");
            System.out.println("5 - Kitap Bilgilerini Güncelle");
            System.out.println("6 - Yazara Ait Kitapları Listele");
            System.out.println("7 - Kategorideki Kitapları Listele");
            System.out.println("8 - Kitap Ödünç Al");
            System.out.println("9 - Kitap Geri İade Et");
            System.out.println("10 - Fatura Kes");
            System.out.println("11 - Kitap Ödünç Limiti Kontrolü");
            System.out.println("12 - Çıkış Yap");
            System.out.print("Seçiminizi yapın: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Kitap ID: ");
                    String bookId = scanner.nextLine();
                    System.out.print("Kitap Adı: ");
                    String title = scanner.nextLine();
                    System.out.print("Yazar: ");
                    String author = scanner.nextLine();
                    System.out.print("Kategori: ");
                    String category = scanner.nextLine();
                    library.addBook(new Book(bookId, title, author, category));
                    System.out.println("Kitap başarıyla eklendi.");
                    break;

                case 2:
                    library.listBooks();
                    break;

                case 3:
                    System.out.print("Silinecek Kitap ID: ");
                    String removeId = scanner.nextLine();
                    library.removeBook(removeId);
                    System.out.println("Kitap başarıyla silindi.");
                    break;

                case 4:
                    System.out.println("Kitapları ID, isim veya yazar ile arayın.");
                    System.out.print("Arama Türü (ID/İsim/Yazar): ");
                    String searchType = scanner.nextLine();
                    System.out.print("Arama Değeri: ");
                    String searchValue = scanner.nextLine();
                    if ("ID".equalsIgnoreCase(searchType)) {
                        Book book = library.searchBookById(searchValue);
                        System.out.println(book != null ? book : "Kitap bulunamadı.");
                    } else if ("İsim".equalsIgnoreCase(searchType)) {
                        library.searchBooksByTitle(searchValue).forEach(System.out::println);
                    } else if ("Yazar".equalsIgnoreCase(searchType)) {
                        library.searchBooksByAuthor(searchValue).forEach(System.out::println);
                    } else {
                        System.out.println("Geçersiz arama türü.");
                    }
                    break;

                case 5:
                    System.out.print("Güncellemek istediğiniz Kitap ID: ");
                    String updateId = scanner.nextLine();
                    System.out.print("Yeni Kitap Adı: ");
                    String newTitle = scanner.nextLine();
                    System.out.print("Yeni Yazar: ");
                    String newAuthor = scanner.nextLine();
                    System.out.print("Yeni Kategori: ");
                    String newCategory = scanner.nextLine();
                    library.updateBook(updateId, newTitle, newAuthor, newCategory);
                    break;

                case 6:
                    System.out.print("Yazar adı girin: ");
                    String authorName = scanner.nextLine();
                    library.listBooksByAuthor(authorName);
                    break;

                case 7:
                    System.out.print("Kategori girin: ");
                    String categoryName = scanner.nextLine();
                    library.listBooksByCategory(categoryName);
                    break;

                case 8:
                    if (loggedInUser.getBorrowedBooks().size() < User.BORROW_LIMIT) {
                        System.out.print("Ödünç alınacak Kitap ID: ");
                        String borrowId = scanner.nextLine();
                        Book borrowBook = library.searchBookById(borrowId);
                        if (borrowBook != null && !borrowBook.isBorrowed()) {
                            borrowBook.borrowBook();
                            loggedInUser.borrowBook(borrowBook);
                            System.out.println("Kitap başarıyla ödünç alındı.");
                        } else {
                            System.out.println("Bu kitap zaten ödünç alınmış veya geçerli değil.");
                        }
                    } else {
                        System.out.println("Kitap ödünç alma limitinize ulaştınız.");
                    }
                    break;

                case 9:
                    System.out.print("İade edilecek Kitap ID: ");
                    String returnId = scanner.nextLine();
                    Book returnBook = library.searchBookById(returnId);
                    if (returnBook != null && returnBook.isBorrowed()) {
                        returnBook.returnBook();
                        loggedInUser.returnBook(returnBook);
                        System.out.println("Kitap başarıyla iade edildi.");
                    } else {
                        System.out.println("Bu kitap ödünç alınmamış veya geçerli değil.");
                    }
                    break;

                case 10:
                    System.out.print("Fatura için Kitap ID: ");
                    String invoiceBookId = scanner.nextLine();
                    System.out.print("Fatura için Ücret: ");
                    double amount = scanner.nextDouble();
                    String invoiceId = "FATURA-" + System.currentTimeMillis();
                    Invoice invoice = new Invoice(invoiceId, loggedInUser.getUserId(), invoiceBookId, amount);
                    library.addInvoice(invoice);
                    System.out.println("Fatura başarıyla oluşturuldu: " + invoice);
                    break;

                case 11:
                    System.out.println("Şu an " + loggedInUser.getBorrowedBooks().size() + " kitap ödünç alındı.");
                    break;

                case 12:
                    System.out.println("Çıkış yapılıyor...");
                    loggedInUser = null;
                    break;

                default:
                    System.out.println("Geçersiz seçim. Tekrar deneyin.");
            }
        }
    }
}
