package in.aj7parihar.product_service_class_110524;

import in.aj7parihar.product_service_class_110524.models.Category;
import in.aj7parihar.product_service_class_110524.models.Product;
import in.aj7parihar.product_service_class_110524.repositories.CategoryRepository;
import in.aj7parihar.product_service_class_110524.repositories.ProductRepository;
import in.aj7parihar.product_service_class_110524.repositories.projections.ProductProjection;
import in.aj7parihar.product_service_class_110524.repositories.projections.ProductWithIdAndTitle;
import in.aj7parihar.product_service_class_110524.services.CategoryService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class ProductServiceClass110524ApplicationTests {

    // There are three ways of Dependency Injection
    // 1. DI through Constructors --> As we have done in our code
    // 2. DI using setters method, learn this
    // 3. Autowired --> Not recommended --> We do not have to do anything, it uses advanced concepts like
    // reflection, etc. behind the scene.

    @Autowired
    private ProductRepository productRepository;


    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private DataSourceTransactionManagerAutoConfiguration dataSourceTransactionManagerAutoConfiguration;

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

    // In this test we will learn about Fetch Types - Eager and LAZY.
    // We first tried with fetch type as FetchType.EAGER, then it failed because of Transaction
    // then we defined @Transactional and was able to fetch using FetchType.EAGER
    @Test
    @Transactional // Telling Hibernate to keep this as one transaction and do not commit in b/w,
    // until the whole transaction is completed, using this we achieve LAZY loading.
    void testFetchTypes(){
        // Optional keyword - we are using optional keyword because if there doesn't exist a category
        // with given id then it should be optional
        Optional<Category> category = categoryRepository.findById(3L);

        // When we used FetchType.EAGER in Category, this test case failed to fetch all the products by
        // category id because it fetched the cat_id and after that it closed the DB session, and
        // we got error like: org.hibernate.LazyInitializationException: failed to lazily initialize a collection of role: in.aj7parihar.product_service_class_110524.models.Category.products: could not initialize proxy - no Session

        // By using Fetch type explicitly as FetchType.EAGER in Category model this was working.

        // How to overcome the problem of lazy initialization??
        // Since the default fetch type for Category model is Lazy only, so how do we achieve our aim?
        // We read in our DB class that inorder to achieve the results for entire session we need
        // to explicitly tell Hibernate that this is one transaction and that can be done using the
        // annotation "Transactional"

        // So if category exists
        if(category.isPresent()){
            System.out.println(category.get().getTitle());

            // Since we are using Optional keyword hence we are using category.get()
            // Figure out, why when using optional we have to use extra "get()", but not in below test case
            List<Product> products = category.get().getProducts();
            for (Product product : products){
                System.out.println(product.getTitle());
            }
        }
    }

    @Test
    @Transactional // Here also it failed without @Transactional annotation, with same error
    // as the above test case, because hibernate commits the session after first DB call
    // and ends it.
    // Hence, we need to define the @Transactional isolation?? level so that it considers
    // the entire as on DB call.
    // After using @Transactional it worked
    void testFetchMode(){
        // N+1 Problem - Fetching 'N' categories and then fetching "N" products for each category.
        // 1 DB call for fetching N categories
        // N DB calls for fetching N products for each category.
        // In total there will be N+1 DB call, N calls for fetching N products and 1 call for a
        // single category.
        // TC wise it will be O(N^2), but DB calls wise it will be N+1

        // First DB Call - in one DB call it will fetch 'N' categories
        // Do not give "%Electronics" for searching ending with otherwise it will consider
        // % as escaper character
        List<Category> categories = categoryRepository.findByTitleEndingWith("Electronics");

        for (Category category : categories){
            System.out.println(category.getTitle());

            // Second DB Call - for one category it will fetch 'N' products hence 'N' DB call
            // Why not using "category.get().getProducts()" here and using in above test?
            List<Product> products = category.getProducts();
            for (Product product : products){
                System.out.println(product.getTitle());
            }
            System.out.println();
            System.out.println();
        }

        // Solution to N+1 problem -
        // Using subquery, inner query to fetch the related categories and outer query to
        // fetch all the products related to each category


    }
}
