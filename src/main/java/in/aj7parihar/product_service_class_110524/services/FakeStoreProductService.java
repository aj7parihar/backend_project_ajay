package in.aj7parihar.product_service_class_110524.services;

import in.aj7parihar.product_service_class_110524.exceptions.ProductNotFoundException;
import in.aj7parihar.product_service_class_110524.dtos.FakeStoreDTO;
import in.aj7parihar.product_service_class_110524.models.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class FakeStoreProductService implements ProductService {

    private RestTemplate restTemplate; // RestTemplate is a class provided by Spring Web
    // but since RestTemplate is a class provided by Spring framework itself hence we cannot Annotate this
    // that is why we will create a new configuration and inside that create this file, and annotate as
    // Bean
    public FakeStoreProductService(RestTemplate restTemplate) {

        this.restTemplate = restTemplate;
    }

    @Override
    public Product getSingleProduct(int productId) throws ProductNotFoundException {
        FakeStoreDTO fakeStoreDTO = restTemplate.getForObject("https://fakestoreapi.com/products/"
        + productId, FakeStoreDTO.class);

        if(fakeStoreDTO == null){
            throw new ProductNotFoundException("Product with id " + productId + " not found, try with an id < 21");
        }

        return fakeStoreDTO.toProduct();
    }

    @Override
    public List<Product> getAllProducts(){
        FakeStoreDTO[] fakeStoreDTOS = restTemplate.getForObject("https://fakestoreapi.com/products",
                FakeStoreDTO[].class);

        // Here at first List<FakeStoreDTO>.class was throwing error "Cannot access class object of parameterized type"
        // From here will learn about the concept of Generics in java: list, maps etc. all are generics
        // Why generics - because it takes any type of object and create a list, such as list<Integer>,
        // list<Character>, etc.
        // Hence in-order to store list of FakeStoreDTO object we will introduce the concept of typeEraser,
        // so that java will be able to create a list of any object type but for now we will work with array.

        // Parent of anything in Java is object, so if we use List.class (same as List<Object>)
        // as workaround in place of List<FakeStoreDTO>.class,
        // the Object class will not have any member variables (like title, price, etc)
        // Hence jackson will not find any variables to convert and will get a null response.

        // For now we will use FakeStoreDTO[] to receive all the JSON objects from fakestore

        // Now we will convert all the fakestore objects to Product objects
        List<Product> products = new ArrayList<>();
        for(FakeStoreDTO fakeStoreDTO : fakeStoreDTOS) {
            products.add(fakeStoreDTO.toProduct());
        }
        return products;
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

        return response.toProduct();
    }
}
