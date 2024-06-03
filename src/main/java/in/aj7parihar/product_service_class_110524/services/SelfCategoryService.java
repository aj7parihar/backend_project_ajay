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
        Category getCategoryById = categoryRepository.findByIdIs(categoryId);
        if (getCategoryById == null) {
            throw new CategoryNotFoundException("Category with an Id #" + categoryId + " is not found.");
        }
        return getCategoryById;
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
