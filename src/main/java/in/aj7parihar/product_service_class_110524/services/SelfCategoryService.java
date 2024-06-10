package in.aj7parihar.product_service_class_110524.services;

import in.aj7parihar.product_service_class_110524.exceptions.CategoryNotFoundException;
import in.aj7parihar.product_service_class_110524.models.Category;
import in.aj7parihar.product_service_class_110524.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SelfCategoryService implements CategoryService {

    private final CategoryRepository categoryRepository;

    public SelfCategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category getSingleCategory(Long categoryId) throws CategoryNotFoundException {

        // Fetch Type - Lazy Loading - All the products belonging to a category will be fetched in 2 DB calls
        // 1st DB call - get the category Id.
        Category getCategoryById = categoryRepository.findByIdIs(categoryId);
        if (getCategoryById == null) {
            throw new CategoryNotFoundException("Category with an Id #" + categoryId + " is not found.");
        }

        return getCategoryById;

        // 2nd DB call - category ID from the first DB call is used to fetch all the products in that category,
        // and this is done on demand (which Hibernate provides) i.e. when I need all the products only
        // then I'll fetch them otherwise I'll delay it.
        // return List<Product> p = getCategoryById.getProducts();


        // Fetch Type - Eager Loading - All products belonging to a category in 1 DB call using JOINS
        // Select c.title, p.title
        // from Category c
        // Left Join
        // Product p
        // ON c.id = p.category_id

        // EAGER Loading - Upfront fetch
        // LAZY Loading - Fetch later

        // By default, (until explicitly mentioned) Hibernate does Eager fetch for all the attributes excepts
        // for Collection such lists, as soon as it sees the list it does the Lazy fetch.



    }

    @Override
    public ArrayList<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public void addCategory(Category category) {

    }

    @Override
    public void updateCategory(Category category) {

    }

    @Override
    public Category deleteCategory(Long categoryId) throws CategoryNotFoundException{
        Category categoryInDb = categoryRepository.findByIdIs(categoryId);
        if (categoryInDb == null) {
            throw new CategoryNotFoundException("Category with an Id #" + categoryId + " is not found.");
        }

        categoryRepository.delete(categoryInDb);
        return categoryInDb;
    }
}
