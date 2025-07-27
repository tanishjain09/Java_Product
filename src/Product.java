public class Product {
    private String productName;
    private double productPrice;
    private int productQuantity;

    public Product(String productName, double productPrice, int productQuantity) {

        if (productPrice < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        if (productQuantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        this.productName = productName;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
    }

    public String getProductName() {
        return productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public double totalPrice() {
        return this.productQuantity * this.productPrice;
    }

    @Override
    public String toString() {
        return productName + "(Qty: " + productQuantity + ", Unit Price: " + productPrice + ", Total: " + totalPrice()
                + ")";
    }
}
