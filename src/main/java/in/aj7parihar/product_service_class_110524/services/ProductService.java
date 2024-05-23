package in.aj7parihar.product_service_class_110524.services;

import in.aj7parihar.product_service_class_110524.dtos.ProductResponseDTO;

//@Service
public interface ProductService {
    public ProductResponseDTO getSingleProduct(int productId);

    public ProductResponseDTO getAllProducts();

    public ProductResponseDTO addProduct(
            String title,
            Double price,
            String description,
            String imageURL,
            String category
    );
}
