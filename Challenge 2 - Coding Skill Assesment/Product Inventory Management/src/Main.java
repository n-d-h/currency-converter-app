import java.util.List;

public class Main {
    static List<Product> products = List.of(
            new Product("Laptop", 999.99, 5),
            new Product("Smartphone", 499.99, 10),
            new Product("Tablet", 299.99, 0),
            new Product("SmartWatch", 199.99, 3)
    );

    static Product[] arr = {
            new Product("Laptop", 999.99, 5),
            new Product("Smartphone", 499.99, 10),
            new Product("Tablet", 299.99, 0),
            new Product("SmartWatch", 199.99, 3)
    };

    public static void main(String[] args) {
        ProductUtils.workingWithList(products);
        ProductUtils.workingWithArray(arr);
    }

}