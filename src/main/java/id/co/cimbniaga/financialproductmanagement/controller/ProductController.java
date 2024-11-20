package id.co.cimbniaga.financialproductmanagement.controller;

import id.co.cimbniaga.financialproductmanagement.dto.ProductRequestDTO;
import id.co.cimbniaga.financialproductmanagement.model.Product;
import id.co.cimbniaga.financialproductmanagement.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
            return ResponseEntity.ok(product);
        } catch (Exception e) {
            //return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable long id, @RequestBody ProductRequestDTO productRequestDTO) {
        //return productService.editById(id, productRequestDTO);
        try {
            Product product = productService.editById(id, productRequestDTO);
            return ResponseEntity.ok(product);
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        //return productService.deleteById(id);
        try {
            productService.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Insurance policy deleted");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping()
    public ResponseEntity<?> create(
            @RequestBody ProductRequestDTO productRequestDTO
    ) {
        //return productService.create(productRequestDTO);
        try {
            Product product = productService.create(productRequestDTO);
            return ResponseEntity.ok(product);
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
