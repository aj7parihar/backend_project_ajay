package in.aj7parihar.product_service_class_110524.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Product extends BaseModel{
    //private int id;
    //private long id; (changed from int to long, now removing from here as we have base model)
    private String title;
    private Double price;
    private String description;
    private String imageURL;

    // category id here is foreign key hence we will mention the mapping here
    // and since now two tables are connected we are using cascade so that changes
    // are reflected accordingly.
    @ManyToOne(cascade = {CascadeType.PERSIST} )
    private Category category;
}
