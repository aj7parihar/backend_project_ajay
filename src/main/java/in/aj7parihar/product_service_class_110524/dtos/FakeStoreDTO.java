package in.aj7parihar.product_service_class_110524.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FakeStoreDTO {

    private int id;
    private String title;
    private Double price;
    private String description;
    private String image; // this is going to fake store hence keep this as image but not imageURL
    private String category;


    public ProductResponseDTO toProductResponseDTO(){
        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        productResponseDTO.setId(id);
        productResponseDTO.setTitle(title);
        productResponseDTO.setPrice(price);
        productResponseDTO.setDescription(description);
        productResponseDTO.setImageURL(image);
        productResponseDTO.setCategory(category);

        return productResponseDTO;
    }
}
