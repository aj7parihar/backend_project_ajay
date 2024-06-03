package in.aj7parihar.product_service_class_110524.services;

import in.aj7parihar.product_service_class_110524.exceptions.ProductNotFoundException;
import in.aj7parihar.product_service_class_110524.models.Product;

import java.util.List;

//@Service
public interface ProductService {
    public Product getSingleProduct(Long productId) throws ProductNotFoundException;

    public List<Product> getAllProducts();
    // Why "public" modifier is redundant for interface members?

    public Product addProduct(
            String title,
            Double price,
            String description,
            String imageURL,
            String category
    );

    public Product deleteProduct(Long productId) throws ProductNotFoundException;

    public Product updateProduct(Long productId,
                          String title,
                          Double price,
                          String description,
                          String imageURL,
                          String category) throws ProductNotFoundException;

    public Product replaceProduct(Long productId,
                                  String title,
                                  Double price,
                                  String description,
                                  String imageURL,
                                  String category) throws ProductNotFoundException;
}
