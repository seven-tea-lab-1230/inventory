package Jar;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final StockHistoryRepository stockHistoryRepository;

    public ProductService(ProductRepository productRepository
            ,StockHistoryRepository stockHistoryRepository) {
        this.productRepository = productRepository;
        this.stockHistoryRepository = stockHistoryRepository;
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product getProduct(Long id) {
        return productRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "商品が見つかりません"
                )
        );
    }

    public Product updateProduct(Long id,Product updatedProduct){
        Product product = productRepository.findById(id).orElseThrow();
        product.setName(updatedProduct.getName());
        product.setStock(updatedProduct.getStock());
        return  productRepository.save(product);
    }

    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }

    @Transactional
    public Product removeStock(Long id,int quantity){
        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "商品が見つかりません"
                        )
                );

        if (quantity <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "出庫数は1以上にしてください"
            );
        }

        if (product.getStock() < quantity) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "在庫が足りません"
            );
        }

        int newStock = product.getStock() - quantity;
        product.setStock(newStock);

        StockHistory history = new StockHistory();
        history.setProductId(id);
        history.setType("OUT");
        history.setQuantity(quantity);
        history.setCreatedAt(LocalDateTime.now());

        stockHistoryRepository.save(history);

        return productRepository.save(product);
    }

    @Transactional
    public Product addStock(Long id,int quantity){
        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "商品が見つかりません"
                        )
                );

        if (quantity <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "入庫数は1以上にしてください"
            );
        }

        int newStock = product.getStock() + quantity;
        product.setStock(newStock);

        StockHistory history = new StockHistory();
        history.setProductId(id);
        history.setType("IN");
        history.setQuantity(quantity);
        history.setCreatedAt(LocalDateTime.now());

        stockHistoryRepository.save(history);

        return productRepository.save(product);
    }
}
