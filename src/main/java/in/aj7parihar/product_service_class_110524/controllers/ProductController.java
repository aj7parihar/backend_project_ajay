package in.aj7parihar.product_service_class_110524.controllers;

import in.aj7parihar.product_service_class_110524.dtos.ProductRequestDTO;
import in.aj7parihar.product_service_class_110524.dtos.ProductResponseDTO;
import in.aj7parihar.product_service_class_110524.exceptions.ProductNotFoundException;
import in.aj7parihar.product_service_class_110524.models.Product;
import in.aj7parihar.product_service_class_110524.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {


    // Dependency Injection
    private ProductService productService;
    private ModelMapper modelMapper;

    public ProductController(@Qualifier("selfProductService") ProductService productService, ModelMapper modelMapper) {
        // Spring is doing the DI by default once I have made "@" annotation in FakeStoreProductService class
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    // Get single Product by ID
    @GetMapping("{id}")
    public ResponseEntity<ProductResponseDTO> getProductDetails(@PathVariable ("id") Long productId) throws ProductNotFoundException {
        Product product = productService.getSingleProduct(productId);
        // Here ProductService is an Interface (or contract), now the reference to the object...
        // "productService" of type ProductService will directly call the methods belonging to different
        // Product Services like FakeStoreProductService, our own Product Service, etc.
        // Hence, FakeStoreProductService or OurOwnProductService will implement this contract/interface (ProductService)
        // and THIS MY DEAR FRIEND IS THE BEAUTY OF INHERITANCE & INTERFACE.

        // Just like Animal class can act as an Interface, and Cats & Dogs class will implement this interface

        // Note - Interface and Abstract class fulfills the same purpose except Interface will not have its own
        // attributes (will only have features i.e. functions/methods),
        // whereas Abstract class will have its own attributes ( ie variables).

        // return convertProductToProductResponseDTO(product);
        ProductResponseDTO productResponseDTO = convertProductToProductResponseDTO(product);
        return new ResponseEntity<>(productResponseDTO, HttpStatus.OK);
    }

    // Get all Products
//    @GetMapping()
//    public ResponseEntity<List<ProductResponseDTO>> getAllProductDetails(){
//        List<Product> product = productService.getAllProducts();
//
//        // We have got the details of all the products, now we will convert Product object to
//        // ProductResponseDTO object
//        List<ProductResponseDTO> productResponseDTOList = new ArrayList<>();
//        for(Product product1 : product){
//            productResponseDTOList.add(convertProductToProductResponseDTO(product1));
//        }
//        return new ResponseEntity<>(productResponseDTOList, HttpStatus.OK);
//
//    }

    // Get all products using pagination
    @GetMapping()
    public ResponseEntity<List<ProductResponseDTO>> getAllProductDetails(
            @RequestParam ("pageNumber") int pageNumber,
            @RequestParam ("pageSize") int pageSize,
            @RequestParam ("sortBy") String sortParam)
    {
        Page<Product> product = productService.getAllProducts(pageNumber, pageSize, sortParam);

        // We have got the details of all the products, now we will convert Product object to
        // ProductResponseDTO object
        List<ProductResponseDTO> productResponseDTOList = new ArrayList<>();

        for(Product product1 : product){
            productResponseDTOList.add(convertProductToProductResponseDTO(product1));
        }
        return new ResponseEntity<>(productResponseDTOList, HttpStatus.OK);

    }

    // Add a new Product
    @PostMapping()
    public ResponseEntity<ProductResponseDTO> createNewProduct(@RequestBody ProductRequestDTO productRequestDTO){
        Product product =  productService.addProduct(
            productRequestDTO.getTitle(),
            productRequestDTO.getPrice(),
            productRequestDTO.getDescription(),
            productRequestDTO.getImageURL(),
            productRequestDTO.getCategory()
        );
        // return convertProductToProductResponseDTO(product);
        ProductResponseDTO productResponseDTO = convertProductToProductResponseDTO(product);
        return new ResponseEntity<>(productResponseDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ProductResponseDTO> deleteAProduct(@PathVariable ("id") Long productId) throws ProductNotFoundException {
        Product product = productService.deleteProduct(productId);
        ProductResponseDTO productResponseDTO = convertProductToProductResponseDTO(product);
        return new ResponseEntity<>(productResponseDTO, HttpStatus.OK);
    }

    @PatchMapping("{id}")
    public ResponseEntity<ProductResponseDTO> updateAProduct(@PathVariable ("id") Long productId,
            @RequestBody ProductRequestDTO productRequestDTO) throws ProductNotFoundException {
        Product product = productService.updateProduct(
                productId,
                productRequestDTO.getTitle(),
                productRequestDTO.getPrice(),
                productRequestDTO.getDescription(),
                productRequestDTO.getImageURL(),
                productRequestDTO.getCategory()
        );
        ProductResponseDTO productResponseDTO = convertProductToProductResponseDTO(product);
        return new ResponseEntity<>(productResponseDTO, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<ProductResponseDTO> replaceAProduct(@PathVariable ("id") Long productId,
                                                             @RequestBody ProductRequestDTO productRequestDTO)
            throws ProductNotFoundException {
        Product product = productService.replaceProduct(
                productId,
                productRequestDTO.getTitle(),
                productRequestDTO.getPrice(),
                productRequestDTO.getDescription(),
                productRequestDTO.getImageURL(),
                productRequestDTO.getCategory()
        );
        ProductResponseDTO productResponseDTO = convertProductToProductResponseDTO(product);
        return new ResponseEntity<>(productResponseDTO, HttpStatus.OK);
    }

    private ProductResponseDTO convertProductToProductResponseDTO(Product product){
        String categoryTitle = product.getCategory().getTitle();

        ProductResponseDTO productResponseDTO = modelMapper.map(product, ProductResponseDTO.class);
        productResponseDTO.setCategory(categoryTitle);
        return productResponseDTO;
    }

    // Add exception Handler
//    @ExceptionHandler(ProductNotFoundException.class)
//    public ResponseEntity<ErrorDTO> handleProductNotFoundException(ProductNotFoundException productNotFoundException){
//        // ResponseEntity is used for HTTP response code, and it is provided by Spring Web
//        ErrorDTO errorDTO = new ErrorDTO();
//        errorDTO.setMessage(productNotFoundException.getMessage());
//        return new ResponseEntity<>(errorDTO, HttpStatus.NOT_FOUND);
//    }
}
