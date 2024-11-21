package id.co.cimbniaga.financialproductmanagement.controller;

import id.co.cimbniaga.financialproductmanagement.dto.CategoryResponseDTO;
import id.co.cimbniaga.financialproductmanagement.dto.ProductRequestDTO;
import id.co.cimbniaga.financialproductmanagement.dto.ProductResponseDTO;
import id.co.cimbniaga.financialproductmanagement.model.Product;
import id.co.cimbniaga.financialproductmanagement.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping()
    public List<Product> getAll() {
        return productService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable long id) {
        //return productService.getById(id);
        try {
            Product product = productService.getById(id);
            ProductResponseDTO productResponseDTO = toProductResponseDTO(product);

            return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                    "Product with id-" + id, productResponseDTO
            ));
        } catch (Exception e) {
            //return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item not found!");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable long id, @RequestBody ProductRequestDTO productRequestDTO) {
        //return productService.editById(id, productRequestDTO);
        try {
            Product product = productService.editById(id, productRequestDTO);
            ProductResponseDTO productResponseDTO = toProductResponseDTO(product);

            return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                    "Product with id-" + id + " successfully MODIFIED", productResponseDTO
            ));
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid input!");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        //return productService.deleteById(id);
        try {
            Product product = productService.getById(id);
            ProductResponseDTO productResponseDTO = toProductResponseDTO(product);

            productService.deleteById(id);

            return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                    "Product with id-" + id + " successfully DELETED", productResponseDTO
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item not found!");
        }
    }

    @PostMapping()
    public ResponseEntity<?> create(
            @RequestBody ProductRequestDTO productRequestDTO
    ) {
        //return productService.create(productRequestDTO);
        try {
            Product product = productService.create(productRequestDTO);
            ProductResponseDTO productResponseDTO = toProductResponseDTO(product);

            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "New Product successfully ADDED", productResponseDTO
            ));

        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    public ProductResponseDTO toProductResponseDTO(Product product) {
        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.isAvailability(),
                new CategoryResponseDTO(
                        product.getCategory().getId(),
                        product.getCategory().getName()
                )
        );
    }


}
