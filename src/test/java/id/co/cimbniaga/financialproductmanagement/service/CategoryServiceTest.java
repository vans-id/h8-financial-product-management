package id.co.cimbniaga.financialproductmanagement.service;

import id.co.cimbniaga.financialproductmanagement.dto.CategoryRequestDTO;
import id.co.cimbniaga.financialproductmanagement.model.Category;
import id.co.cimbniaga.financialproductmanagement.model.Product;
import id.co.cimbniaga.financialproductmanagement.repository.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CategoryServiceTest {
    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllCategoryTest_Success() {

        List<Category> categories = new ArrayList<>();
        Category category1 = new Category();
        Category category2 = new Category();
        Category category3 = new Category();

        category1.setId(1);
        category1.setName("Reksadana");

        category2.setId(2);
        category2.setName("Reksadana2");

        category3.setId(3);
        category3.setName("Reksadana3");

        when(categoryRepository.findAll()).thenReturn(categories);

        List<Category> categories1 = categoryService.getAllCategory();

        assertNotNull(categories1);
        assertEquals(categories.size(), categories1.size());

        for (int i = 0; i < categories.size(); i++) {
            Category expected = categories.get(i);
            Category actual = categories1.get(i);

            assertEquals(expected.getId(), actual.getId());
            assertEquals(expected.getName(), actual.getName());
        }

        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void getAllCategoryTest_NoData() {
        when(categoryRepository.findAll()).thenReturn(new ArrayList<>());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            categoryService.getAllCategory();
        });

        assertEquals("Category not found", exception.getMessage());
    }

    @Test
    void createCategoryTest() {
        Category category = new Category();
        category.setId(1);
        category.setName("Reksadana");

        CategoryRequestDTO categoryRequestDTO = new CategoryRequestDTO();
        categoryRequestDTO.setName(category.getName());
        //category.setName(categoryRequestDTO.getName());

        //when(categoryRepository.save(category)).thenReturn(category);
        when(categoryRepository.save(any(Category.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Category updatedCategory = categoryService.createCategory(categoryRequestDTO);
        assertNotNull(updatedCategory);
        verify(categoryRepository, times(1)).save(updatedCategory);
    }

    @Test
    void createCategoryTest_NotUnique() {
        Category category = new Category();
        category.setId(1);
        category.setName("Reksadana");

        CategoryRequestDTO categoryRequestDTO = new CategoryRequestDTO();
        categoryRequestDTO.setName(category.getName());

        when(categoryRepository.save(any(Category.class))).thenAnswer(invocation -> invocation.getArgument(0));

        System.out.println(categoryService.createCategory(categoryRequestDTO));
        System.out.println(categoryService.createCategory(categoryRequestDTO));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            categoryService.createCategory(categoryRequestDTO);
        });

        assertEquals("Category already exists", exception.getMessage());
    }
}