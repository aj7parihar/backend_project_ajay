package in.aj7parihar.product_service_class_110524.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ProductRequestDTO {
    private int id;
    private String title;
    private Double price;
    private String description;
    private String imageURL;
    private String category;
}
