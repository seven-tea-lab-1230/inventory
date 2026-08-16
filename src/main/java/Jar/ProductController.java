package Jar;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public List<Product> getProducts() {
        return productService.getProducts();
    }

    @PostMapping("/products")
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }
    @GetMapping("/products/{id}")
    public Product getProduct(@PathVariable Long id) {
        return productService.getProduct(id);
    }
    @PutMapping("/products/{id}")
    public Product updateProduct(
            @PathVariable Long id,
            @RequestBody Product updatedProduct) {
        return productService.updateProduct(id,updatedProduct);
    }
    @DeleteMapping("/products/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
    @PutMapping("/products/{id}/stock/remove")
    public Product removeStock(
            @PathVariable Long id,
            @RequestParam int quantity) {
        return productService.removeStock(id,quantity);
    }

    @PutMapping("/products/{id}/stock/add")
    public Product addStock(
            @PathVariable Long id,
            @RequestParam int quantity) {
        return productService.addStock(id,quantity);
    }
}