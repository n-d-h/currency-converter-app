import java.util.Comparator;
import java.util.List;

class ProductUtils {

    public static void workingWithArray(Product[] arr) {
        // working with array
        System.out.println("\nWorking with array");

        // calculate the total price of all products
        double totalPriceArr = 0;
        for (Product product : arr) {
            totalPriceArr += product.getPrice() * product.getQuantity();
        }
        System.out.printf("1. Total price: %,.2f%n", totalPriceArr);

        // find the most expensive product
        Product mostExpensiveProductArr = arr[0];
        for (Product product : arr) {
            if (product.compareTo(mostExpensiveProductArr) > 0) {
                mostExpensiveProductArr = product;
            }
        }
        System.out.println("2. Most expensive product: " + mostExpensiveProductArr.getName());

        // check if a product "Headphones" is in stock
        boolean isInStockArr = false;
        for (Product product : arr) {
            if (product.getName().equals("Headphones")) {
                isInStockArr = true;
                break;
            }
        }
        System.out.println("3. Headphones are in stock: " + isInStockArr);

        // sort the products by price in descending order
        System.out.println("4. Products sorted by price in descending order:");
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i].compareTo(arr[j]) < 0) {
                    Product temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }
        for (Product product : arr) {
            System.out.println("  - " + product);
        }

        // sort the products by quantity in ascending order
        System.out.println("5. Products sorted by quantity in ascending order:");
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i].getQuantity() > arr[j].getQuantity()) {
                    Product temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }
        for (Product product : arr) {
            System.out.println("  - " + product);
        }
    }

    public static void workingWithList(List<Product> products) {
        // working with list
        System.out.println("Working with list");

        // calculate the total price of all products
        double totalPrice = products.stream()
                .mapToDouble(product -> product.getPrice() * product.getQuantity())
                .sum();
        System.out.printf("1. Total price: %,.2f%n", totalPrice);

        // find the most expensive product
        Product mostExpensiveProduct = products.stream()
                .max(Product::compareTo)
                .get();
        System.out.println("2. Most expensive product: " + mostExpensiveProduct.getName());

        // check if a product "Headphones" is in stock
        boolean isInStock = products.stream()
                .anyMatch(product -> product.getName().equals("Headphones"));
        System.out.println("3. Headphones are in stock: " + isInStock);

        // sort the products by price in descending order
        System.out.println("4. Products sorted by price in descending order:");
        products.stream()
                .sorted(Comparator.comparingDouble(Product::getPrice).reversed())
                .forEach(p -> System.out.println("  - " + p));

        // sort the products by quantity in ascending order
        System.out.println("5. Products sorted by quantity in ascending order:");
        products.stream()
                .sorted(Comparator.comparingInt(Product::getQuantity))
                .forEach(p -> System.out.println("  - " + p));
    }
}