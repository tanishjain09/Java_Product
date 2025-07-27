import java.util.HashMap;
import java.util.Map;

public class CountryTaxMapper {
    private static final Map<String, TaxType> countryTaxMap = new HashMap<>();

    static{
        countryTaxMap.put("India",TaxType.GST);
        countryTaxMap.put("Hong Kong", TaxType.NONE);
        countryTaxMap.put("UK", TaxType.VAT);
        countryTaxMap.put("UAE",TaxType.NONE);
        countryTaxMap.put("Qatar",TaxType.NONE);
    }

    public static TaxType getTaxTypeForCountry(String country){
        return countryTaxMap.getOrDefault(country, TaxType.NONE);
    }
}
