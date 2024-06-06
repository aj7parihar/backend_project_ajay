package in.aj7parihar.product_service_class_110524.repositories;

import in.aj7parihar.product_service_class_110524.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    // same explanation as mentioned in ProductRepository Interface

    // Through Hibernate(ORM) below methods will execute JPA Declared Queries

    public Category save(Category category);

    public Category findByTitle(String title);

    public Category findByIdIs(Long categoryId);

    public ArrayList<Category> findAll();

    // public Category delete(Long categoryId); --> this gives fatal error on using it
}
