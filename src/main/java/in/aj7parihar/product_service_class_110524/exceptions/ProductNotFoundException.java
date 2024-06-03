package in.aj7parihar.product_service_class_110524.exceptions;

public class ProductNotFoundException extends Exception {
    // Exception - error/unwanted thing/unexpected bug
// here our class ProductNotFoundException is extending the abstract class
    // Exception of spring framework

    public ProductNotFoundException(String message) {
        super(message);
    }
}
