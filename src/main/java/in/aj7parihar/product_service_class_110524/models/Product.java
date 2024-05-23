package in.aj7parihar.product_service_class_110524.models;

//import jdk.jfr.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
    private int id;
    private String title;
    private Double price;
    private String description;
    private String imageURL;
    private Category category;
}
