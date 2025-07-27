public class VATCalculator implements TaxCalculator{

    private static final double VAT_RATE = 0.12;

    @Override
    public double calculateTax(Invoice invoice) {
        return invoice.getSubtotal() * VAT_RATE;
    }

    @Override
    public String getTaxName() {
        return "VAT (20%)";
    }
}
