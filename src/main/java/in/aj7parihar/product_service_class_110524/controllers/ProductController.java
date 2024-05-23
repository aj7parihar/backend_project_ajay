package in.aj7parihar.product_service_class_110524.controllers;

import in.aj7parihar.product_service_class_110524.dtos.ProductRequestDTO;
import in.aj7parihar.product_service_class_110524.dtos.ProductResponseDTO;
import in.aj7parihar.product_service_class_110524.services.ProductService;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {

    // Dependency Injection
    private ProductService productService;

    public ProductController(ProductService productService) {
        // Spring is doing the DI by default once I have made "@" annotation in FakeStoreProductService class
        this.productService = productService;
    }

    @GetMapping("/products/{id}")
    public ProductResponseDTO getProductDetails(@PathVariable ("id") int productId){
        return productService.getSingleProduct(productId);
    }

    @GetMapping("/products")
    public ProductResponseDTO getProductDetails(){
        return productService.getAllProducts();
    }

    @PostMapping("/products")
    public ProductResponseDTO createNewProduct(@RequestBody ProductRequestDTO productRequestDTO){
        return productService.addProduct(
            productRequestDTO.getTitle(),
            productRequestDTO.getPrice(),
            productRequestDTO.getDescription(),
            productRequestDTO.getImageURL(),
            productRequestDTO.getCategory()
        );
    }
}
