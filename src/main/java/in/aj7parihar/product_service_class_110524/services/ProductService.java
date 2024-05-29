package in.aj7parihar.product_service_class_110524.services;

import in.aj7parihar.product_service_class_110524.exceptions.ProductNotFoundException;
import in.aj7parihar.product_service_class_110524.models.Product;

import java.util.List;

//@Service
public interface ProductService {
    public Product getSingleProduct(int productId) throws ProductNotFoundException;

    public List<Product> getAllProducts();

    public Product addProduct(
            String title,
            Double price,
            String description,
            String imageURL,
            String category
    );
}
