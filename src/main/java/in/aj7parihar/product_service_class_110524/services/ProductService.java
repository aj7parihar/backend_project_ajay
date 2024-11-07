package in.aj7parihar.product_service_class_110524.services;

import in.aj7parihar.product_service_class_110524.exceptions.ProductNotFoundException;
import in.aj7parihar.product_service_class_110524.models.Product;
import org.springframework.data.domain.Page;

import java.util.List;

// @Service - either use here or directly on the implementation
public interface ProductService {
    // NOTE -
    // 1. Do not implement any methods on its own, methods are implemented by classes that implements it.
    // 2. Do not have any attributes.
    // 3. All methods are implicitly public.
    // 4. We can have Constants that are specific to the class,
    // i.e. constants by default such as Public, Static & Final data-members.
    // 5. Interface cannot have its object created.

    // In the future, if we want to implement our own method that is not there inside interface,
    // then Java 8 and further versions provide a default method which we can override.

    public Product getSingleProduct(Long productId) throws ProductNotFoundException;

    public List<Product> getAllProducts();
    // Why "public" modifier is redundant for interface members? (refer to point 3 in notes)

    // fetching all products using Pagination & Sorting
    Page<Product> getAllProducts(int pageNumber, int pageSize, String sortParam);

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
