import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Invoice {
    private Customer customer;
    private List<Product> products;
    private LocalDateTime invoiceDate;
    private TaxCalculator taxCalculator;

    public Invoice (Customer customer, TaxCalculator taxCalculator){
        this.customer = customer;
        this.products = new ArrayList<>();
        this.invoiceDate = LocalDateTime.now();
        this.taxCalculator = taxCalculator;
    }

    public void addProduct(Product product){
        products.add(product);
    }
    public Customer getCustomer() {
        return customer;
    }

    public List<Product> getProducts() {
        return products;
    }

    public LocalDateTime getInvoiceDate() {
        return invoiceDate;
    }

    public double getSubtotal(){
        double subTotal = 0;
        for(Product p : products){
            subTotal += p.totalPrice();
        }
        return subTotal;
    }
    public double getDiscount(){
        double subTotal = getSubtotal();
        return (subTotal > 10000) ? subTotal * 0.10 : 0.0;
    }

    public double getTax(){
        return taxCalculator.calculateTax(this);
    }

    public double getTotal(){
        return getSubtotal() - getDiscount() + getTax();
    }

    public void printInvoive() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    System.out.println("========================================");
    System.out.println("              INVOICE");
    System.out.println("========================================");
    System.out.printf("Customer Name : %s\n", customer.getCustomerName());
    System.out.printf("Country       : %s\n", customer.getCountry());
    System.out.printf("Date          : %s\n", invoiceDate.format(formatter));
    System.out.println("----------------------------------------");
    System.out.println(String.format("%-20s %-10s %-10s %-10s", "Product", "Qty", "Unit Price", "Total"));
    System.out.println("----------------------------------------");
    for(Product p : products){
        System.out.println(String.format("%-20s %-10d %-10.2f %-10.2f",
            p.getProductName(), p.getProductQuantity(), p.getProductPrice(), p.totalPrice()));
    }
    System.out.println("----------------------------------------");
    System.out.printf("Subtotal      : %.2f\n", getSubtotal());
    if(getDiscount() > 0) {
        System.out.printf("Discount      : %.2f\n", getDiscount());
    }
    System.out.printf("%-14s: %.2f\n", taxCalculator.getTaxName(), getTax());
    System.out.printf("Total Amount  : %.2f\n", getTotal());
    System.out.println("========================================");
    System.out.println("Thank you for shopping!");
    System.out.println("========================================");
}
}
