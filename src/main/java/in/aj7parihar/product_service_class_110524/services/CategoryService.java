package in.aj7parihar.product_service_class_110524.services;


import in.aj7parihar.product_service_class_110524.exceptions.CategoryNotFoundException;
import in.aj7parihar.product_service_class_110524.models.Category;

import java.util.ArrayList;

public interface CategoryService {

    public Category getSingleCategory(Long categoryId) throws CategoryNotFoundException;

    public ArrayList<Category> getAllCategories();

    public void addCategory(Category category);

    public void updateCategory(Category category);

    public Category deleteCategory(Long categoryId) throws CategoryNotFoundException;

}
