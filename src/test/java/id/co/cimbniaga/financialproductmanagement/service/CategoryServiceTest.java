package id.co.cimbniaga.financialproductmanagement.service;

import id.co.cimbniaga.financialproductmanagement.dto.CategoryRequestDTO;
import id.co.cimbniaga.financialproductmanagement.model.Category;
import id.co.cimbniaga.financialproductmanagement.model.Product;
import id.co.cimbniaga.financialproductmanagement.repository.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    ///////////////////////////////////
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

//    @Test
//    void getAllCategoryTest_NoData() {
//        when(categoryRepository.findAll()).thenReturn(new ArrayList<>());
//
//        Exception exception = assertThrows(RuntimeException.class, () -> {
//            categoryService.getAllCategory();
//        });
//
//        assertEquals("Category not found", exception.getMessage());
//    }
    @Test
    void getAllCategoryTest_NoData() {
        when(categoryRepository.findAll()).thenReturn(new ArrayList<>());
        List<Category> allCategory = categoryService.getAllCategory();

        assertNotNull(allCategory);

        assertEquals(0, allCategory.toArray().length);
    }

    ///////////////////////////////////
    @Test
    void getById() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Reksadana");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        Category result = categoryService.getCategoryById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Reksadana", result.getName());
        verify(categoryRepository, times(1)).findById(1L);
    }

    @Test
    void getByIdInvalidId() {
        when(categoryRepository.findById(10L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            categoryService.getCategoryById(10L);
        });

        assertEquals("Category not found", exception.getMessage());
        verify(categoryRepository, times(1)).findById(10L);
    }


    ///////////////////////////////////
    @Test
    void createCategoryTest() {
        Category category = new Category();
        category.setId(1);
        category.setName("Reksadana");

        CategoryRequestDTO categoryRequestDTO = new CategoryRequestDTO();
        categoryRequestDTO.setName(category.getName());

        when(categoryRepository.findByName(category.getName())).thenReturn(Optional.empty());

        when(categoryRepository.save(any(Category.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Category updatedCategory = categoryService.createCategory(categoryRequestDTO);
        assertNotNull(updatedCategory);
        assertEquals(category.getName(), updatedCategory.getName());
        verify(categoryRepository, times(1)).save(updatedCategory);
    }

//    @Test
//    void createCategoryTest_NotUnique() {
//        Category category = new Category();
//        category.setId(1);
//        category.setName("Reksadana");
//
//        CategoryRequestDTO categoryRequestDTO = new CategoryRequestDTO();
//        categoryRequestDTO.setName(category.getName());
//
//        when(categoryRepository.save(any(Category.class))).thenAnswer(invocation -> invocation.getArgument(0));
//
//        System.out.println(categoryService.createCategory(categoryRequestDTO));
//        System.out.println(categoryService.createCategory(categoryRequestDTO));
//
//        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
//            categoryService.createCategory(categoryRequestDTO);
//        });
//
//        assertEquals("Category already exists", exception.getMessage());
//    }
    @Test
    void createCategoryTestNotUnique() {
        Category category = new Category();
        category.setId(1);
        category.setName("Reksadana");

        CategoryRequestDTO categoryRequestDTO = new CategoryRequestDTO();
        categoryRequestDTO.setName(category.getName());

        when(categoryRepository.findByName(category.getName())).thenReturn(Optional.of(category));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            categoryService.createCategory(categoryRequestDTO);
        });

        assertEquals("Duplicate Category", exception.getMessage());
        verify(categoryRepository, never()).save(any(Category.class));
    }

    ///////////////////////////////////
    @Test
    void updateCategoryTest() {
        Category category = new Category();
        category.setId(1);
        category.setName("Reksadana");

        CategoryRequestDTO categoryRequestDTO = new CategoryRequestDTO();
        categoryRequestDTO.setName(category.getName());

        when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));

        when(categoryRepository.save(any(Category.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Category updatedCategory = categoryService.updateCategory(category.getId(), categoryRequestDTO);
        assertNotNull(updatedCategory);
        assertEquals(category.getName(), updatedCategory.getName());
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void updateCategoryTestNotUnique() {
        Category category1 = new Category();
        category1.setId(1);
        category1.setName("Reksadana");

        Category category2 = new Category();
        category2.setId(2);
        category2.setName("Reksadana2");

        CategoryRequestDTO categoryRequestDTO = new CategoryRequestDTO();
        categoryRequestDTO.setName(category1.getName());

        when(categoryRepository.findByName(category1.getName())).thenReturn(Optional.of(category1));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            categoryService.updateCategory(category2.getId(), categoryRequestDTO);
        });

        assertEquals("Duplicate Category", exception.getMessage());
        verify(categoryRepository, never()).save(any(Category.class));
    }

    @Test
    void updateCategoryTestInvalidId() {
        Category category = new Category();
        category.setId(1);
        category.setName("Reksadana");

        CategoryRequestDTO categoryRequestDTO = new CategoryRequestDTO();
        categoryRequestDTO.setName("New Product");

        when(categoryRepository.findById(10L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            categoryService.updateCategory(10L, categoryRequestDTO);
        });

        assertEquals("Category not found", exception.getMessage());
        verify(categoryRepository, never()).save(any(Category.class));
    }

    ///////////////////////////////////
    @Test
    void deleteCategoryTest() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Reksadana");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        doNothing().when(categoryRepository).deleteById(1L);

        categoryService.deleteCategory(1L);

        verify(categoryRepository, times(1)).findById(1L);
        verify(categoryRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteCategoryInvalidId() {
        when(categoryRepository.findById(10L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            categoryService.deleteCategory(10L);
        });

        assertEquals("Category not found", exception.getMessage());
        verify(categoryRepository, times(1)).findById(10L);
        verify(categoryRepository, never()).deleteById(anyLong());
    }

}
