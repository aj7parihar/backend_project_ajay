package in.aj7parihar.product_service_class_110524.repositories;

import in.aj7parihar.product_service_class_110524.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Long> {
    // Now similarly like ProductService interface we have ProductRepository interface which can be
    // implemented by any product repositories like MySQLProductRepository or PostgresSQLProductRepository, etc.
    // So our ProductRepository interface will implement JpaRepository which will be implemented internally
    // by Hibernate (ORM).
    // Product refers to model in DB and Long is the data type of ID.


    public Product save(Product product);
    // Why "public" modifier is redundant for interface members?
    // Declared Queries --> save() -- POST, PUT, PATCH

    public List<Product> findAll();
    // Declared Query --> findAll() -- GET

    Product findByIdIs(Long id);
    // Declared Query --> findByIdIs() -- GET

    // some methods like "delete()" are there in the repository hence even without defining
    // we can use them in our service class.
    // public Product delete(Long id); --> this gives fatal error on using it
}
