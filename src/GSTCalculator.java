public class GSTCalculator implements TaxCalculator{

    private static final double GST_RATE = 0.18;
    @Override
    public double calculateTax(Invoice invoice) {
        double subtotal = invoice.getSubtotal();
        return subtotal * GST_RATE;
    }

    @Override
    public String getTaxName() {
        return "GST (18%)";
    }
}
