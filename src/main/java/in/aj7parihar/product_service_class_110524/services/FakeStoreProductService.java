package in.aj7parihar.product_service_class_110524.services;

import in.aj7parihar.product_service_class_110524.dtos.FakeStoreDTO;
import in.aj7parihar.product_service_class_110524.dtos.ProductResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
    public ProductResponseDTO getSingleProduct(int productId) {
        FakeStoreDTO fakeStoreDTO = restTemplate.getForObject("https://fakestoreapi.com/products/"
        + productId, FakeStoreDTO.class);

        return fakeStoreDTO.toProductResponseDTO();
    }

    @Override
    public ProductResponseDTO getAllProducts() {
        FakeStoreDTO fakeStoreDTO = restTemplate.getForObject("https://fakestoreapi.com/products",
                FakeStoreDTO.class);

        return fakeStoreDTO.toProductResponseDTO();
    }

    @Override
    public ProductResponseDTO addProduct(
            String title,
            Double price,
            String description,
            String imageURL,
            String category)
    {
        FakeStoreDTO fakeStoreDTO = new FakeStoreDTO();
        fakeStoreDTO.setTitle(title);
        fakeStoreDTO.setPrice(price);
        fakeStoreDTO.setDescription(description);
        fakeStoreDTO.setImageURL(imageURL);
        fakeStoreDTO.setCategory(category);

        FakeStoreDTO response = restTemplate.postForObject("https://fakestoreapi.com/products",
                fakeStoreDTO, FakeStoreDTO.class);
        // fakeStoreDTO passed in params is request body
        // response from fake store api is sent to FakeStoreDTO.class

        return response.toProductResponseDTO();
    }
}
