import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        //take customer details
        System.out.println("Enter customer name: ");
        String name = sc.nextLine();

        //take country name
        System.out.println("Enter customer country: ");
        String country = sc.nextLine();

        Customer customer = new Customer(name, country);

        //take product detaild
        List<Product> products = new ArrayList<>();
        System.out.println("How many products? ");

        int noOfProduct = sc.nextInt();
        sc.nextLine();
        for(int i = 1; i <= noOfProduct; i++){
            System.out.println("Enter details of products " + i + ":");
            System.out.println("Name: ");
            String productName = sc.nextLine();

            System.out.println("Price: ");
            double price = sc.nextDouble();

            System.out.println("Quantity: ");
            int quantity = sc.nextInt();
            sc.nextLine();

            products.add(new Product(productName,price,quantity));
        }

        // Customer customer = new Customer("John Doe", "India");
        // Product product = new Product("Laptop", 50000, 1);

        TaxType type = CountryTaxMapper.getTaxTypeForCountry(customer.getCountry());
        TaxCalculator calculator = TaxFactory.getTaxCalculator(type);

        Invoice invoice = new Invoice(customer, calculator);
        for(Product p: products){
            invoice.addProduct(p);
        }
        
        invoice.printInvoive();

    }
}
