package id.co.cimbniaga.financialproductmanagement.controller;

import id.co.cimbniaga.financialproductmanagement.dto.ProductRequestDTO;
import id.co.cimbniaga.financialproductmanagement.model.Product;
import id.co.cimbniaga.financialproductmanagement.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Optional<Product> getById(@PathVariable long id) {
        return productService.getById(id);
    }

    @PutMapping("/{id}")
    public Object edit(@PathVariable long id, @RequestBody ProductRequestDTO productRequestDTO) {
        return productService.editById(id, productRequestDTO);
    }

    @DeleteMapping("/{id}")
    public Object delete(@PathVariable long id) {
        return productService.deleteById(id);
    }

    @PostMapping()
    public Product create(
            @RequestBody ProductRequestDTO productRequestDTO
    ) {
        return productService.create(productRequestDTO);
    }
}
