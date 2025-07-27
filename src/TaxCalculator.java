public interface TaxCalculator {
    double calculateTax(Invoice invoice);
    String getTaxName();
}
