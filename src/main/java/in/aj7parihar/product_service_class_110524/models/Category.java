package in.aj7parihar.product_service_class_110524.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity // Using this annotation we will tell the DB to create the table for this model or entity
// and this 'Entity' annotation is from spring-boot-starter-data-JPA
public class Category extends BaseModel {
    private String title;

    // So when we represented the cardinalities, what ORM did is that along with
    // tables for two models it also created a third table "category_products"
    // which is always created in case of ManyToMany mapping.
    // hence we will have to explicitly tell ORM that the products here
    // mapped to category keyword in Products table and do not create a new
    // table for this.
    @OneToMany (mappedBy = "category")
    List<Product> products;
}
