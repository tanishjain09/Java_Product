public class Customer {
    private String customerName;
    private String country;

    public Customer(String name, String country) {
        if(name == null || name.trim().isEmpty()){
            throw new IllegalArgumentException("Customer customerName cannot be empty");
        }
        if(country == null || country.trim().isEmpty()){
            throw new IllegalArgumentException("Country Name cannot be empty");
        }
        this.customerName = name.trim();
        this.country = country.trim();
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public String toString() {
        return "Customer{customerName='" + customerName + "', country='" + country + "'}";
    }
}
