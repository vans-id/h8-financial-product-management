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

    public Product getById(long id) {
        //return productRepository.findById(id);
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public void deleteById(long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        productRepository.delete(product);
    }

    public Product editById(long id, ProductRequestDTO productRequestDTO) {
        long category_id = productRequestDTO.getCategory().getId();
        if (category_id < 1 || category_id > 11) {
            throw new RuntimeException("Invalid category id");
        }

        double price = productRequestDTO.getPrice();
        if (price < 0) {
            throw new RuntimeException("Invalid price");
        }

        long stock = productRequestDTO.getStock();
        if (stock < 0) {
            throw new RuntimeException("Stock must be greater than zero");
        }

        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        Category category = categoryRepository.findById(productRequestDTO.getCategory().getId()).orElseThrow(() -> new RuntimeException("Invalid Category"));

        product.setName(productRequestDTO.getName());
        product.setDescription(productRequestDTO.getDescription());
        product.setPrice(productRequestDTO.getPrice());
        product.setAvailability(productRequestDTO.isAvailability()); //buat boolean itu GETnya pake is (isAvailability) dari Lombok
        product.setStock(productRequestDTO.getStock());
        product.setCategory(category);

        return productRepository.save(product);
    }

    public Product create(ProductRequestDTO productRequestDTO) {
        long category_id = productRequestDTO.getCategory().getId();
        if (category_id < 1 || category_id > 11) {
            throw new RuntimeException("Invalid category id");
        }

        double price = productRequestDTO.getPrice();
        if (price < 0) {
            throw new RuntimeException("Invalid price");
        }

        double stock = productRequestDTO.getStock();
        if (stock < 0) {
            throw new RuntimeException("Stock must be greater than zero");
        }

        Category category = categoryRepository.findById(productRequestDTO.getCategory().getId()).orElseThrow(() -> new RuntimeException("Invalid Category"));
        Product product = new Product();

        product.setName(productRequestDTO.getName());
        product.setDescription(productRequestDTO.getDescription());
        product.setPrice(productRequestDTO.getPrice());
        product.setAvailability(productRequestDTO.isAvailability()); //buat boolean itu GETnya pake is (isAvailability) dari Lombok
        product.setStock(productRequestDTO.getStock());
        product.setCategory(category);

        return productRepository.save(product);
    }
}
