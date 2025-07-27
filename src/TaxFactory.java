public class TaxFactory {
    public static TaxCalculator getTaxCalculator(TaxType type){
        switch (type){
            case GST: return new GSTCalculator();
            case VAT: return new VATCalculator();
            case NONE: return new NoTaxCalculator();
            default: throw new IllegalArgumentException("Invalid Tax Type");
        }
    }
}
