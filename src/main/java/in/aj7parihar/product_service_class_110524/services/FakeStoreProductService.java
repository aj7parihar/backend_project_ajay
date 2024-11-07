package in.aj7parihar.product_service_class_110524.services;

import in.aj7parihar.product_service_class_110524.dtos.FakeStoreDTO;
import in.aj7parihar.product_service_class_110524.exceptions.ProductNotFoundException;
import in.aj7parihar.product_service_class_110524.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service("fakeStoreProductService")
public class FakeStoreProductService implements ProductService {

    private RestTemplate restTemplate; // RestTemplate is a in-built utility class provided by Spring Web
    // but since it is a class provided by Spring framework itself hence we cannot Annotate this
    // that is why we will create a new configuration and inside that create a class, and annotate as
    // Bean
    public FakeStoreProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Product getSingleProduct(Long productId) throws ProductNotFoundException {
        FakeStoreDTO fakeStoreDTO = restTemplate.getForObject("https://fakestoreapi.com/products/"
        + productId, FakeStoreDTO.class);

        // Exception Handling
        if(fakeStoreDTO == null){
            throw new ProductNotFoundException("Product with id " + productId + " not found, try with an id < 21");
        }

        return fakeStoreDTO.toProduct();
    }

    @Override
    public List<Product> getAllProducts(){
        // List<FakeStoreDTO> fakeStoreDTOList = restTemplate.getForObject("https://fakestoreapi.com/products",
        //         List<FakeStoreDTO>.class);

        /* Here at first List<FakeStoreDTO>.class was throwing error "Cannot access class object of parameterized type"

        About the warning:
        The warning "Cannot access class object of parameterized type" occurs
        because Java’s type erasure removes the generic type information at runtime.

        This means that List<FakeStoreDTO>.class doesn't exist at runtime,
        and hence, you can't directly pass it as a class type in methods like getForObject.

        What is a Parameterized Type?
        A parameterized type refers to a class or interface that is parameterized over types
        (i.e., it has type parameters). In Java, this is commonly seen in generics.
        Generics allow you to create classes, interfaces, and methods that can operate on any specified type
        (e.g., List<String>, Map<Integer, String>).


        "Class Object of Parameterized Type"?
        The term "class object of parameterized type" refers to attempting to obtain a Class object
        for a specific parameterized type (e.g., List<String>.class), which is not allowed in Java.
        This is because Java's generics use type erasure, which means that generic type information is not
        available at runtime. Therefore, you can't directly access a Class object for a
        parameterized type like List<FakeStoreDTO>.

        To resolve this issue, you need to use ParameterizedTypeReference to retain the generic type information.
        Here’s how we can modify your code:
        import org.springframework.core.ParameterizedTypeReference;
        import org.springframework.http.ResponseEntity;
        import org.springframework.web.client.RestTemplate;

        public List<Product> getAllProducts() {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List<FakeStoreDTO>> response = restTemplate.exchange(
            "https://fakestoreapi.com/products",
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<FakeStoreDTO>>() {});

        List<FakeStoreDTO> fakeStoreDTOList = response.getBody();

        return fakeStoreDTOList != null ? fakeStoreDTOList.toProduct() : new ArrayList<>();
        }


         // Approach suggested by Souvik in Class -
         Parent of anything in Java is Object, so if we use List.class (same as List<Object>)
         as workaround in place of List<FakeStoreDTO>.class,
         the Object class will not have any member variables (like title, price, etc)
         Hence jackson will not find any variables to convert and will get a null response.

         */

        // For now we will use FakeStoreDTO[] to receive all the JSON objects from fakestore
        FakeStoreDTO[] fakeStoreDTOS = restTemplate.getForObject("https://fakestoreapi.com/products",
                FakeStoreDTO[].class);

        // Now we will convert all the fakestore objects to Product objects
        List<Product> products = new ArrayList<>();
        for(FakeStoreDTO fakeStoreDTO : fakeStoreDTOS) {
            products.add(fakeStoreDTO.toProduct());
        }
        return products;
    }

    // get all products using pagination
    public Page<Product> getAllProducts(int pageNumber, int pageSize, String sortParam) {
        return null;
    }

    @Override
    public Product addProduct(
            String title,
            Double price,
            String description,
            String imageURL,
            String category)
    {
        FakeStoreDTO requestDTO = new FakeStoreDTO();
        requestDTO.setTitle(title);
        requestDTO.setPrice(price);
        requestDTO.setDescription(description);
        requestDTO.setImage(imageURL);
        requestDTO.setCategory(category);

        FakeStoreDTO response = restTemplate.postForObject("https://fakestoreapi.com/products",
                requestDTO, FakeStoreDTO.class);
        // fakeStoreDTO passed in params is request body
        // response from fake store api is sent to FakeStoreDTO.class

        // and then converted back to Product object

        return response.toProduct();
    }
    public Product deleteProduct(Long productId) throws ProductNotFoundException{
        return null;
    }

    public Product updateProduct(Long productId,
                          String title,
                          Double price,
                          String description,
                          String imageURL,
                          String category) throws ProductNotFoundException{
        return null;
    }

    @Override
    public Product replaceProduct(Long productId, String title, Double price, String description, String imageURL, String category) throws ProductNotFoundException {
        return null;
    }
}
