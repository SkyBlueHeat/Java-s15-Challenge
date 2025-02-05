package models;

public class Invoice {
    private String invoiceId;
    private String userId;
    private String bookId;
    private double amount;

    public Invoice(String invoiceId, String userId, String bookId, double amount) {
        this.invoiceId = invoiceId;
        this.userId = userId;
        this.bookId = bookId;
        this.amount = amount;
    }

    public String getInvoiceId() { return invoiceId; }
    public String getUserId() { return userId; }
    public String getBookId() { return bookId; }
    public double getAmount() { return amount; }

    @Override
    public String toString() {
        return "Fatura ID: " + invoiceId + " | Kullanıcı ID: " + userId + " | Kitap ID: " + bookId + " | Ücret: " + amount;
    }
}
