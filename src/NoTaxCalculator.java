public class NoTaxCalculator implements TaxCalculator{
    @Override
    public double calculateTax(Invoice invoice) {
        return 0.0;
    }

    @Override
    public String getTaxName() {
        return "No Tax";
    }
}
