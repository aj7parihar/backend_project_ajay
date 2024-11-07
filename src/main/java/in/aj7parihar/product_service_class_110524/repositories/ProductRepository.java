package in.aj7parihar.product_service_class_110524.repositories;

import in.aj7parihar.product_service_class_110524.models.Product;
import in.aj7parihar.product_service_class_110524.repositories.projections.ProductProjection;
import in.aj7parihar.product_service_class_110524.repositories.projections.ProductWithIdAndTitle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // Now similarly like ProductService interface we have ProductRepository interface which can be
    // implemented by any product repositories like MySQLProductRepository or PostgresSQLProductRepository, etc.
    // So our ProductRepository interface will implement JpaRepository which will be implemented internally
    // by Hibernate (ORM).
    // Product refers to model(table) in DB and Long is the data type of ID.


    public Product save(Product product);
    // Through Hibernate(ORM) above method will execute JPA Declared Queries
    // Declared Query --> save() -- POST, PUT, PATCH


    public List<Product> findAll();
    // Through Hibernate(ORM) above method will execute JPA Declared Queries
    // Declared Query --> findAll() -- GET


    // find All() using pagination
    Page<Product> findAll(Pageable pageable);



    Product findByIdIs(Long id);
    // Through Hibernate(ORM) above method will execute JPA Declared Queries
    // Declared Query --> findByIdIs() -- GET


    // some methods like "delete()" are there in the repository hence even without defining
    // we can use them in our service class.
    // public Product delete(Long id); --> this gives fatal error on using it


    // JOINS using declared queries
    // w/o JOINS - 1st DB call to get the Category object from Category table using title
    // 2nd DB call to get all the products belonging to that Category.
    // We can reduce the DB call by joining Category & Product table on category_id from both the tables.
    // Raw SQL query:
    // Select *
    // from Product p
    // Left JOIN
    // Category c
    // Where p.cat_id = c.cat_id AND
    // c.title = categoryTitle;
    public List<Product> findAllByCategory_Title(String categoryTitle);


    // HQL Queries - writing custom queries
    // List of all the products with a given category using HQL
    @Query("select p from Product p where p.category.title = :categoryName")
    public List<Product> getProductsWithCategoryName(String categoryName);

    // If we want only one field but not all the fields.
    // List of all Product Titles that belong to a given category
    @Query("select p.title as title from Product p where p.category.title = :categoryName")
    public List<String> getProductTitles(String categoryName);


    // There is also another way of getting only specific fields from the product table by using PROJECTION
    // It is the utility provided by Hibernate.

    // Let's say for above query we also want list of ids and tomorrow list of titles, is it feasible
    // to keep on creating list of compound objects?? --- BIG NO
    // Hence for solving this problem Spring gives me Projection (created as Interface inside repositories)

    @Query("select p.id as id, p.title as title from Product p where p.category.title = :categoryName")
    public List<ProductWithIdAndTitle> getProductsWithIdAndTitle(String categoryName);

    // Now let's say we want to have price and category also in our query result,
    // are we going to create another Projection? NO, right.
    // Hence instead of creating a separate Projection for each field we will create a Projection for all
    // the fields in a model.

    @Query("select p.id as id, p.title as title from Product p where p.category.title = :categoryName")
    public List<ProductProjection> getDetails(String categoryName);


    // Native SQL Query
    @Query(value = "select * from Product p where p.id = :productId", nativeQuery = true)
    public Product nativeSQLQuery(Long productId);

}
