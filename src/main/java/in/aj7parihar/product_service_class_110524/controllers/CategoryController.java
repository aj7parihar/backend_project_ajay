package in.aj7parihar.product_service_class_110524.controllers;

import in.aj7parihar.product_service_class_110524.dtos.CategoryResponseDTO;
import in.aj7parihar.product_service_class_110524.exceptions.CategoryNotFoundException;
import in.aj7parihar.product_service_class_110524.models.Category;
import in.aj7parihar.product_service_class_110524.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class CategoryController {

    private CategoryService categoryService;
    private ModelMapper modelMapper;

    public CategoryController(CategoryService categoryService, ModelMapper modelMapper) {
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<CategoryResponseDTO> getCategoryDetails(@PathVariable ("id") Long categoryId)
            throws CategoryNotFoundException {
        Category category = categoryService.getSingleCategory(categoryId);
        CategoryResponseDTO categoryResponseDTO = convertCategoryToCategoryResponseDTO(category);
        return new ResponseEntity<>(categoryResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/category")
    public ResponseEntity<ArrayList<CategoryResponseDTO>> getAllCategoryDetails() {
        ArrayList<Category> categories = categoryService.getAllCategories();
        ArrayList<CategoryResponseDTO> categoryResponseDTOs = new ArrayList<>();
        for (Category category : categories) {
            CategoryResponseDTO categoryResponseDTO = convertCategoryToCategoryResponseDTO(category);
            categoryResponseDTOs.add(categoryResponseDTO);
        }
        return new ResponseEntity<>(categoryResponseDTOs, HttpStatus.OK);
    }

    @DeleteMapping("/category/{id}")
    public ResponseEntity<CategoryResponseDTO> deleteACategory(@PathVariable("id") Long categoryId)
            throws CategoryNotFoundException {
        Category category = categoryService.deleteCategory(categoryId);
        CategoryResponseDTO categoryResponseDTO = convertCategoryToCategoryResponseDTO(category);
        return new ResponseEntity<>(categoryResponseDTO, HttpStatus.OK);
    }

    private CategoryResponseDTO convertCategoryToCategoryResponseDTO(Category category){
        String categoryTitle = category.getTitle();
        CategoryResponseDTO categoryResponseDTO = modelMapper.map(category, CategoryResponseDTO.class);
        categoryResponseDTO.setTitle(categoryTitle);
        return categoryResponseDTO;
    }

}
