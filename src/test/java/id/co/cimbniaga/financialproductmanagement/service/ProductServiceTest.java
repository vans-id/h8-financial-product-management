package id.co.cimbniaga.financialproductmanagement.service;

import id.co.cimbniaga.financialproductmanagement.dto.ProductRequestDTO;
import id.co.cimbniaga.financialproductmanagement.model.Category;
import id.co.cimbniaga.financialproductmanagement.model.Product;
import id.co.cimbniaga.financialproductmanagement.repository.CategoryRepository;
import id.co.cimbniaga.financialproductmanagement.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    public ProductServiceTest() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    private Product productAddDummyData(int i){
        Product product = new Product();
        product.setId(i);
        product.setName("data" + i);
        product.setDescription("data" + i + " description");
        product.setPrice(10.0 * i);
        product.setStock(1000);
        product.setAvailability(true);
        product.setCategory(categoryAddDummyData(i));

        return product;
    }

    private Category categoryAddDummyData(int i){
        Category category = new Category();
        category.setId(i);
        category.setName("cat" + i);

        return category;
    }

    ///////////////////////////////
    @Test
    void getAllProductsWithData() {
        List<Product> products = new ArrayList<>();
        products.add(productAddDummyData(1));
        products.add(productAddDummyData(2));
        products.add(productAddDummyData(3));

        when(productRepository.findAll()).thenReturn(products);

        //mock list of products
        List<Product> allProducts = productService.getAll();

        assertNotNull(allProducts);
        assertEquals(products.size(), allProducts.size());

        for (int i = 0; i < products.size(); i++) {
            Product expected = products.get(i);
            Product actual = allProducts.get(i);

            assertEquals(expected.getId(), actual.getId());
            assertEquals(expected.getName(), actual.getName());
            assertEquals(expected.getDescription(), actual.getDescription());
            assertEquals(expected.getPrice(), actual.getPrice());
            assertEquals(expected.getStock(), actual.getStock());
            assertEquals(expected.isAvailability(), actual.isAvailability());
            assertEquals(expected.getCategory().getId(), actual.getCategory().getId());
            assertEquals(expected.getCategory().getName(), actual.getCategory().getName());
        }
    }

    @Test
    void getAllProductsWithNoData() {
        when(productRepository.findAll()).thenReturn(new ArrayList<>());
        List<Product> allProducts = productService.getAll();

        assertNotNull(allProducts);

        assertEquals(0, allProducts.toArray().length);
    }

    ///////////////////////////////
    @Test
    void getByIdWithData() {
        List<Product> products = new ArrayList<>();
        products.add(productAddDummyData(1));
        products.add(productAddDummyData(2));
        products.add(productAddDummyData(3));

        for (Product product : products) {
            when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        }

        for (Product expected : products) {
            Product actual = productService.getById(expected.getId());

            assertNotNull(actual);
            assertEquals(expected, actual);
        }
    }

    @Test
    void getByIdWithNoData() {
        List<Product> products = new ArrayList<>();
        when(productRepository.findById(1L)).thenReturn(Optional.of(new Product()));

        Product product = productService.getById(1);

        assertNotNull(products);
        assertNotNull(productService.getById(1));
        assertNull(productService.getById(1).getName());
        assertNull(productService.getById(1).getDescription());
        assertEquals(productService.getById(1).getPrice(), 0.0);
        assertEquals(productService.getById(1).getStock(), 0);
        assertFalse(productService.getById(1).isAvailability());
        assertNull(productService.getById(1).getCategory());

    }

    @Test
    void getByIdInvalidId() {
        List<Product> products = new ArrayList<>();
        products.add(productAddDummyData(1));

        long invalidId = 2L;

        when(productRepository.findById(invalidId)).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            productService.getById(2);
        });

        assertNotNull(thrown);
        assertEquals("Product not found", thrown.getMessage());
    }

    ///////////////////////////////]
    @Test
    void deleteByIdWithData() {
        List<Product> products = new ArrayList<>();
        Product product1 = productAddDummyData(1);
        products.add(product1);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product1));

        productService.deleteById(1);

        verify(productRepository, times(1)).delete(product1);

        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertFalse(productRepository.findById(1L).isPresent());
    }

    @Test
    void deleteByIdWithNoData() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());
        //pake assertThrows supaya pas .deleteByIdnya return Exception, test bakal ttp lanjut
        assertThrows(RuntimeException.class, () -> productService.deleteById(1L));

        verify(productRepository, never()).delete(any(Product.class));
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void deleteByIdInvalidId() {
        List<Product> products = new ArrayList<>();
        products.add(productAddDummyData(1));

        long invalidId = 2L;

        when(productRepository.findById(invalidId)).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            productService.getById(2);
        });

        assertNotNull(thrown);
        assertEquals("Product not found", thrown.getMessage());
    }

    ///////////////////////////////
    @Test
    void editByIdWithData() {
        Product existingProduct = productAddDummyData(1);
        Category existingCategory = categoryAddDummyData(1);

        when(productRepository.findById(1L)).thenReturn(Optional.of(existingProduct));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(existingCategory));

        ProductRequestDTO productRequestDTO = new ProductRequestDTO();
        productRequestDTO.setName("Updated Product");
        productRequestDTO.setDescription("Updated description");
        productRequestDTO.setPrice(25.0);
        productRequestDTO.setStock(1500);
        productRequestDTO.setAvailability(true);
        productRequestDTO.setCategory(existingCategory);

        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Product updatedProduct = productService.editById(1L, productRequestDTO);

        assertNotNull(updatedProduct);
        assertEquals("Updated Product", updatedProduct.getName());
        assertEquals("Updated description", updatedProduct.getDescription());
        assertEquals(25.0, updatedProduct.getPrice());
        assertEquals(1500, updatedProduct.getStock());
        assertTrue(updatedProduct.isAvailability());
        assertEquals(existingCategory, updatedProduct.getCategory());

        verify(productRepository, times(1)).save(updatedProduct);
    }

    @Test
    void editByIdWithInvalidId() {
        long nonExistentProductId = 100L;

        when(productRepository.findById(nonExistentProductId)).thenReturn(Optional.empty());

        ProductRequestDTO productRequestDTO = new ProductRequestDTO();
        productRequestDTO.setName("Updated Product");
        productRequestDTO.setDescription("Updated description");
        productRequestDTO.setPrice(25.0);
        productRequestDTO.setStock(1500);
        productRequestDTO.setAvailability(true);
        productRequestDTO.setCategory(categoryAddDummyData(1));

        //to catch thrown exception
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            productService.editById(nonExistentProductId, productRequestDTO);
        });

        assertEquals("Product not found", thrown.getMessage());
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void editByIdInvalidCategoryId() {
        long nonExistentProductId = 100L;

        ProductRequestDTO productRequestDTO = new ProductRequestDTO();
        productRequestDTO.setName("Updated Product");
        productRequestDTO.setDescription("Updated description");
        productRequestDTO.setPrice(1.0);
        productRequestDTO.setStock(1500);
        productRequestDTO.setAvailability(true);
        productRequestDTO.setCategory(categoryAddDummyData(100));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            productService.editById(nonExistentProductId, productRequestDTO);
        });

        assertEquals("Invalid category id", thrown.getMessage());

        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void editByIdInvalidPrice() {
        long nonExistentProductId = 100L;

        ProductRequestDTO productRequestDTO = new ProductRequestDTO();
        productRequestDTO.setName("Updated Product");
        productRequestDTO.setDescription("Updated description");
        productRequestDTO.setPrice(-1.0);
        productRequestDTO.setStock(1500);
        productRequestDTO.setAvailability(true);
        productRequestDTO.setCategory(categoryAddDummyData(1));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            productService.editById(nonExistentProductId, productRequestDTO);
        });

        assertEquals("Invalid price", thrown.getMessage());

        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void editByIdWithInvalidStock() {
        long nonExistentProductId = 100L;

        ProductRequestDTO productRequestDTO = new ProductRequestDTO();
        productRequestDTO.setName("Updated Product");
        productRequestDTO.setDescription("Updated description");
        productRequestDTO.setPrice(1.0);
        productRequestDTO.setStock(-1);
        productRequestDTO.setAvailability(true);
        productRequestDTO.setCategory(categoryAddDummyData(1));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            productService.editById(nonExistentProductId, productRequestDTO);
        });

        assertEquals("Stock must be greater than zero", thrown.getMessage());

        verify(productRepository, never()).save(any(Product.class));
    }

    ///////////////////////////////
    @Test
    void createWithData() {
        Product product = productAddDummyData(1);
        Category category = categoryAddDummyData(1);

        when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));

        ProductRequestDTO productRequestDTO = new ProductRequestDTO();
        productRequestDTO.setName(product.getName());
        productRequestDTO.setDescription(product.getDescription());
        productRequestDTO.setPrice(product.getPrice());
        productRequestDTO.setStock(product.getStock());
        productRequestDTO.setAvailability(product.isAvailability());
        productRequestDTO.setCategory(product.getCategory());

        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Product updatedProduct = productService.create(productRequestDTO);
        assertNotNull(updatedProduct);
        assertEquals(product.getName(), updatedProduct.getName());
        assertEquals(product.getDescription(), updatedProduct.getDescription());
        assertEquals(product.getPrice(), updatedProduct.getPrice());
        assertEquals(product.getStock(), updatedProduct.getStock());
        assertTrue(updatedProduct.isAvailability());
        assertEquals(product.getCategory(), updatedProduct.getCategory());

        verify(productRepository, times(1)).save(updatedProduct);
    }

    @Test
    void createWithInvalidCategoryId() {
        ProductRequestDTO productRequestDTO = new ProductRequestDTO();
        productRequestDTO.setName("Test Product");
        productRequestDTO.setDescription("Test Description");
        productRequestDTO.setPrice(10.0);
        productRequestDTO.setStock(100);
        productRequestDTO.setAvailability(true);
        productRequestDTO.setCategory(categoryAddDummyData(100));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> productService.create(productRequestDTO));

        assertEquals("Invalid Category", exception.getMessage());

        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void createWithInvalidPrice() {
        ProductRequestDTO productRequestDTO = new ProductRequestDTO();
        productRequestDTO.setName("Test Product");
        productRequestDTO.setDescription("Test Description");
        productRequestDTO.setPrice(-10.0);
        productRequestDTO.setStock(100);
        productRequestDTO.setAvailability(true);
        productRequestDTO.setCategory(categoryAddDummyData(1));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> productService.create(productRequestDTO));

        assertEquals("Invalid price", exception.getMessage());

        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void createWithInvalidStock() {
        ProductRequestDTO productRequestDTO = new ProductRequestDTO();
        productRequestDTO.setName("Test Product");
        productRequestDTO.setDescription("Test Description");
        productRequestDTO.setPrice(10.0);
        productRequestDTO.setStock(-100);
        productRequestDTO.setAvailability(true);
        productRequestDTO.setCategory(categoryAddDummyData(1));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> productService.create(productRequestDTO));

        assertEquals("Stock must be greater than zero", exception.getMessage());

        verify(productRepository, never()).save(any(Product.class));
    }
}