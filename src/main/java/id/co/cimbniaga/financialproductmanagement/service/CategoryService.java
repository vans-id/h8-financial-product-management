package id.co.cimbniaga.financialproductmanagement.service;

import id.co.cimbniaga.financialproductmanagement.dto.CategoryRequestDTO;
import id.co.cimbniaga.financialproductmanagement.model.Category;
import id.co.cimbniaga.financialproductmanagement.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public Category createCategory(CategoryRequestDTO categoryRequestDTO) {
        Optional<Category> category = categoryRepository.findByName(categoryRequestDTO.getName());
        if(category.isPresent()) {
            throw new RuntimeException("Duplicate Category");
        }

        Category cat = new Category();
        cat.setName(categoryRequestDTO.getName());

        return categoryRepository.save(cat);
    }

    public Category updateCategory(Long id, CategoryRequestDTO categoryRequestDTO) {
        Optional<Category> cat = categoryRepository.findByName(categoryRequestDTO.getName());
        if(cat.isPresent()) {
            throw new RuntimeException("Duplicate Category");
        }

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        category.setName(categoryRequestDTO.getName());

        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        categoryRepository.deleteById(id);
    }
}
