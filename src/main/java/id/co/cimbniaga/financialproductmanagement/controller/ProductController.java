package id.co.cimbniaga.financialproductmanagement.controller;

import id.co.cimbniaga.financialproductmanagement.dto.CategoryResponseDTO;
import id.co.cimbniaga.financialproductmanagement.dto.ProductRequestDTO;
import id.co.cimbniaga.financialproductmanagement.dto.ProductResponseDTO;
import id.co.cimbniaga.financialproductmanagement.model.Messages;
import id.co.cimbniaga.financialproductmanagement.model.Product;
import id.co.cimbniaga.financialproductmanagement.model.Report;
import id.co.cimbniaga.financialproductmanagement.model.User;
import id.co.cimbniaga.financialproductmanagement.repository.MessageRepository;
import id.co.cimbniaga.financialproductmanagement.repository.ReportRepository;
import id.co.cimbniaga.financialproductmanagement.service.ProductService;
import id.co.cimbniaga.financialproductmanagement.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Product Controller", description = "Products")
public class ProductController {
    @Autowired
    private ProductService productService;


    @Autowired
    private UserService userService;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ReportRepository reportRepository;

    private void addReport(String activity, Product product) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currUser = (User) authentication.getPrincipal();
        User user = userService.validateUser(currUser.getEmail(), currUser.getPassword());

        Report report = new Report();
        report.setUser(user);
        report.setProduct(product);
        report.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
        Messages messages = messageRepository.findByActivityType(activity);
        if (messages == null) {
                messages = new Messages();
                messages.setActivityType(activity);
                messages.setDetail(activity + " PRODUCT");
                messageRepository.save(messages);
        }
        report.setMessages(messages);
        reportRepository.save(report);
    }

    @Operation(summary = "test swagger Product", description = "test swagger Product desc")
    @GetMapping()
    public ResponseEntity<?> getAll() {
        try {
            List<Product> products = productService.getAll();
            addReport("VIEW", products.get(0)); //khusus buat GETALL, Product yg di pass ke addReport() ga dipake
            return ResponseEntity.status(HttpStatus.OK).body(products);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to get Products");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable long id) {
        //return productService.getById(id);
        try {
            Product product = productService.getById(id);
            addReport("VIEW", product);
            return ResponseEntity.ok(product);
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
            addReport("UPDATE", product);

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
            addReport("DELETE", product);

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
            addReport("CREATE", product);

            return ResponseEntity.ok(product);
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
                product.getStock(),
                new CategoryResponseDTO(
                        product.getCategory().getId(),
                        product.getCategory().getName()
                )
        );
    }

}
