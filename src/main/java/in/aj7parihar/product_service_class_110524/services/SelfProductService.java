package in.aj7parihar.product_service_class_110524.services;

import in.aj7parihar.product_service_class_110524.exceptions.ProductNotFoundException;
import in.aj7parihar.product_service_class_110524.models.Category;
import in.aj7parihar.product_service_class_110524.models.Product;
import in.aj7parihar.product_service_class_110524.repositories.CategoryRepository;
import in.aj7parihar.product_service_class_110524.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("selfProductService")
public class SelfProductService implements ProductService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public SelfProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        // This is constructor Dependency Injection
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product getSingleProduct(Long productId) throws ProductNotFoundException {
        Product getProductById = productRepository.findByIdIs(productId);
        if (getProductById == null) {
            throw new ProductNotFoundException("Product with an Id #" + productId + " is not found.");
        }
        return getProductById;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product addProduct(String title,
                              Double price,
                              String description,
                              String imageURL,
                              String category) {
        Product newProduct = new Product();
        newProduct.setTitle(title);
        newProduct.setPrice(price);
        newProduct.setDescription(description);
        newProduct.setImageURL(imageURL);

        // Now we have to check whether the category that is coming from client exists in DB or not
        // if it exists then simply set it else create and set.
        Category categoryFromDb = categoryRepository.findByTitle(category);

        if (categoryFromDb == null) {
            Category newCategory = new Category();
            newCategory.setTitle(category);

            // categoryRepository.save(newCategory);
            // Why to make two DB calls for same thing, since we have already set "cascade = CascadeType.PERSISTS"
            // hence if there is new category coming the Products class will auto. take care of it.


            categoryFromDb = newCategory;
        }

        newProduct.setCategory(categoryFromDb);

        // saving to DB
        Product savedProduct = productRepository.save(newProduct);
        // return the saved product back to controller where it will convert it to ProductResponseDTO and
        // will send back to client
        return savedProduct;
    }

    @Override
    public Product deleteProduct(Long productId) throws ProductNotFoundException{
        // NOTE: This method will delete the entire product
        // whereas isDeleted will update true or false against the record
        Product productInDB = productRepository.findByIdIs(productId);
        if (productInDB == null) {
            throw new ProductNotFoundException("Product with an Id #" + productId + " is not found.");
        }
        // some methods like "delete()" are there in the repository hence even without defining
        // we can use them in our service class
        productRepository.delete(productInDB);
        return productInDB;
    }

    public Product updateProduct(Long productId,
                                 String title,
                                 Double price,
                                 String description,
                                 String imageURL,
                                 String category) throws ProductNotFoundException{
        Product productInDB = productRepository.findByIdIs(productId);
        if (productInDB == null) {
            throw new ProductNotFoundException("Product with an Id #" + productId + " is not found.");
        }


        if(title != null){
            productInDB.setTitle(title);
        }
        if(price != null && price != 0){
            productInDB.setPrice(price);
        }
        if(description != null){
            productInDB.setDescription(description);
        }
        if(imageURL != null){
            productInDB.setImageURL(imageURL);
        }
        if(category != null){
            Category categoryFromDb = categoryRepository.findByTitle(category);
            if (categoryFromDb == null) {
                Category newCategory = new Category();
                newCategory.setTitle(category);
                categoryFromDb = newCategory;
            }
            productInDB.setCategory(categoryFromDb);
        }
        Product updateProduct = productRepository.save(productInDB);
        return updateProduct;
    }

    @Override
    public Product replaceProduct(Long productId,
                                  String title,
                                  Double price,
                                  String description,
                                  String imageURL,
                                  String category)
            throws ProductNotFoundException {
        Product productInDB = productRepository.findByIdIs(productId);
        if (productInDB == null) {
            throw new ProductNotFoundException("Product with an Id #" + productId + " is not found.");
        }

        Category categoryFromDb = categoryRepository.findByTitle(category);
        if (categoryFromDb == null) {
            Category newCategory = new Category();
            newCategory.setTitle(category);
            categoryFromDb = newCategory;
        }

        Product replaceProduct = new Product();
        replaceProduct.setId(productId);
        replaceProduct.setTitle(title);
        replaceProduct.setPrice(price);
        replaceProduct.setDescription(description);
        replaceProduct.setImageURL(imageURL);
        replaceProduct.setCategory(categoryFromDb);

        Product replacedProduct = productRepository.save(replaceProduct);
        return replacedProduct;
    }
}