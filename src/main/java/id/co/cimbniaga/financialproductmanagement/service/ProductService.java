package id.co.cimbniaga.financialproductmanagement.service;


import id.co.cimbniaga.financialproductmanagement.dto.ProductRequestDTO;
import id.co.cimbniaga.financialproductmanagement.model.Category;
import id.co.cimbniaga.financialproductmanagement.model.Product;
import id.co.cimbniaga.financialproductmanagement.repository.CategoryRepository;
import id.co.cimbniaga.financialproductmanagement.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Optional<Product> getById(long id) {
        return productRepository.findById(id);
    }

    public Object deleteById(long id) {
        Optional<Product> prod = getById(id);

        if(prod.isPresent()) {
            productRepository.deleteById(id);

            return "Product deleted";
        } else {
            return "Product not found";
        }
    }

    public Object editById(long id, ProductRequestDTO productRequestDTO) {
        Optional<Product> prod = getById(id);

        if(prod.isPresent()) {
            Category category = categoryRepository.findById(productRequestDTO.getCategory().getId()).orElseThrow(() -> new RuntimeException("Category not found"));
            Product product = prod.get();
            /////////////////////
            product.setName(productRequestDTO.getName());
            product.setDescription(productRequestDTO.getDescription());
            product.setPrice(productRequestDTO.getPrice());
            product.setAvailability(productRequestDTO.isAvailability()); //buat boolean itu GETnya pake is (isAvailability) dari Lombok
            product.setCategory(category);

            productRepository.save(product);

            return "Product updated";
        } else {
            return "Error occured!";
        }
    }

    public Product create(ProductRequestDTO productRequestDTO) {
        Category category = categoryRepository.findById(productRequestDTO.getCategory().getId()).orElseThrow(() -> new RuntimeException("Category not found"));
        Product product = new Product();
        ////////////////////
        product.setName(productRequestDTO.getName());
        product.setDescription(productRequestDTO.getDescription());
        product.setPrice(productRequestDTO.getPrice());
        product.setAvailability(productRequestDTO.isAvailability()); //buat boolean itu GETnya pake is (isAvailability) dari Lombok
        product.setCategory(category);

        return productRepository.save(product);
    }
}
