package in.aj7parihar.product_service_class_110524.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;

@Getter
@Setter
@Entity // Using this annotation we will tell the DB to create the table for this model or entity
// and this 'Entity' annotation is from spring-boot-starter-data-JPA

public class Category extends BaseModel {
    private String title;

    // Flyway (schema version management software -- see library in maven)
    // To test Flyway we are restructuring the table and then maintaining its version.
    private String catDescription;

    // So when we represented the cardinalities, what ORM did is that along with
    // tables for two models it also created a third table "category_products"
    // which is always created in case of ManyToMany mapping.
    // hence we will have to explicitly tell ORM that the products here
    // mapped to category keyword in Products table and do not create a new
    // table for this.
    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)  //, fetch = FetchType.LAZY)
    // By default, fetch = FetchType.LAZY, no need to define explicitly
    // fetch = FetchType.EAGER //need to define explicitly
    // cascade = {CascadeType.PERSIST} - we can give this and see in JPA designer

    // Fetch Modes -
    // There will be three types 1.JOIN, 2.SUBSELECT/SUBQUERY, 3.SELECT
    @Fetch(FetchMode.JOIN) // Same as EAGER fetch
    // @Fetch(FetchMode.SUBSELECT) // Only two DB calls, solves N+1 problem
    // @Fetch(FetchMode.SELECT) // Same as Lazy fetch, N+1 problems remain
    List<Product> products;
}
