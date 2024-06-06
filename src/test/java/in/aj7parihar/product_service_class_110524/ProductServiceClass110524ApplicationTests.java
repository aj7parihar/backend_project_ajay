package in.aj7parihar.product_service_class_110524;

import in.aj7parihar.product_service_class_110524.models.Product;
import in.aj7parihar.product_service_class_110524.repositories.ProductRepository;
import in.aj7parihar.product_service_class_110524.repositories.projections.ProductProjection;
import in.aj7parihar.product_service_class_110524.repositories.projections.ProductWithIdAndTitle;
import in.aj7parihar.product_service_class_110524.services.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ProductServiceClass110524ApplicationTests {

    // There are three ways of Dependency Injection
    // 1. DI through Constructors --> As we have done in our code
    // 2. DI using setters method, learn this
    // 3. Autowired --> Not recommended --> We do not have to anything, it used advanced concepts like
    // reflection, etc. behind the scene.

    @Autowired
    private ProductRepository productRepository;


    @Autowired
    private CategoryService categoryService;

    @Test
    void contextLoads() {
    }

    @Test
    void testJpaDeclaredJoin(){
        // This is getting all the columns from products for a specific category,
        // do not get confused by what we are printing.
        List<Product> products = productRepository.findAllByCategory_Title("Electronics");
        for (Product product : products) {
            // We are just printing title over here, if we want we can print other columns as well.
            System.out.println(product.getTitle());
        }
    }

    @Test
    void testHQL(){
        // This is also getting all the columns from products for a specific category,
        // do not get confused by what we are printing.
        List<Product> products = productRepository.getProductsWithCategoryName("New Electronics");
        for (Product product : products) {
            // We are just printing title over here, if we want we can print other columns as well.
            System.out.println(product.getTitle());
        }
    }

    @Test
    void testHQLSpecificFields(){
        // This is getting just the titles of all the products for a given category,
        // and not all the columns which will improve performance.
        List<String> list = productRepository.getProductTitles("New Electronics");
        for (String productTitle : list) {
            // We are printing only title over here, and other columns can't be printed as we queried only title.
            System.out.println(productTitle);
        }
    }

    @Test
    void testProjection(){
        List<ProductWithIdAndTitle> productWithIdAndTitles = productRepository.getProductsWithIdAndTitle("New Electronics");
        for (ProductWithIdAndTitle productWithIdAndTitle : productWithIdAndTitles) {
            System.out.print(productWithIdAndTitle.getId() + "  " + productWithIdAndTitle.getTitle());
            System.out.println();
        }
    }

    @Test
    void testProjection2(){
        List<ProductProjection> productProjections = productRepository.getDetails("New Electronics");
        for(ProductProjection productProjection : productProjections){
            System.out.print(productProjection.getId() + "  " + productProjection.getTitle());
            System.out.println();
        }
    }

    @Test
    void testNativeQuery(){
        Product product = productRepository.nativeSQLQuery(5L);
        System.out.println(product.getTitle());
        System.out.println(product.getPrice());

        // This will get the category from product table and corresponding to that
        // category will fetch the title.
        System.out.println(product.getCategory().getTitle());

        // This will fetch the Category object only.
        System.out.println(product.getCategory());
        System.out.println(product.getDescription());
        System.out.println(product.getImageURL());
    }
}
