package id.co.cimbniaga.financialproductmanagement.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import id.co.cimbniaga.financialproductmanagement.constants.Variables;
import id.co.cimbniaga.financialproductmanagement.dto.ProductRequestDTO;
import id.co.cimbniaga.financialproductmanagement.model.Category;
import id.co.cimbniaga.financialproductmanagement.model.Product;
import id.co.cimbniaga.financialproductmanagement.repository.CategoryRepository;
import id.co.cimbniaga.financialproductmanagement.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public ProductService(ObjectMapper objectMapper, ProductRepository productRepository, CategoryRepository categoryRepository, RedisTemplate<String, Object> redisTemplate) {
        this.objectMapper = objectMapper;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.redisTemplate = redisTemplate;
    }



    public List<Product> getAll() {
        String jsonString = (String) redisTemplate.opsForValue().get(Variables.REDIS_PRODUCTS_KEY);

        System.out.println("jsons string");
        System.out.println(jsonString);
        List<Product> products;

        if (jsonString != null) {
            try {
                products = objectMapper.readValue(jsonString, new TypeReference<>() {
                });
                System.out.println("goes cache...");
                return products;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        products = productRepository.findAll();
        try {
            String jsonArrString = objectMapper.writeValueAsString(products);
            redisTemplate.opsForValue().set(Variables.REDIS_PRODUCTS_KEY, jsonArrString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        System.out.println("goes db...");

        return products;
    }

    public Product getById(long id) {
        String jsonString = (String) redisTemplate.opsForValue().get(Variables.REDIS_PRODUCTS_KEY);
        List<Product> products;

        if (jsonString != null) {
            try {
                products = objectMapper.readValue(jsonString, new TypeReference<>() {
                });
                for (Product product : products) {
                    if (product.getId() == id) return product;
                }

                throw new RuntimeException("Product Not Found");
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product Not Found"));
    }

    public void deleteById(long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        productRepository.delete(product);

//        redisTemplate.getConnectionFactory().getConnection().serverCommands().flushAll();
        redisTemplate.delete(Variables.REDIS_PRODUCTS_KEY);
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

        productRepository.save(product);
        redisTemplate.delete(Variables.REDIS_PRODUCTS_KEY);

        return product;
    }

    public Product create(ProductRequestDTO productRequestDTO) {
        long category_id = productRequestDTO.getCategory().getId();
        if (category_id < 1 || category_id > 11) {
            throw new RuntimeException("Invalid Category");
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

        productRepository.save(product);
        redisTemplate.delete(Variables.REDIS_PRODUCTS_KEY);

        return product;
    }
}
