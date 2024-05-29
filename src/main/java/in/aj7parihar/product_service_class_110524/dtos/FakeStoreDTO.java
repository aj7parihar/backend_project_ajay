package in.aj7parihar.product_service_class_110524.dtos;

import in.aj7parihar.product_service_class_110524.models.Category;
import in.aj7parihar.product_service_class_110524.models.Product;
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


    public Product toProduct(){
        // converting DTO from Fake Store side to Product object.
        Product product = new Product();
        product.setId(id);
        product.setTitle(title);
        product.setPrice(price);
        product.setDescription(description);
        product.setImageURL(image);

        Category categoryObj = new Category();
        categoryObj.setTitle(category);
        product.setCategory(categoryObj);

        return product;
    }
}
