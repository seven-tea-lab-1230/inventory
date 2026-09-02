package Jar;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private StockHistoryRepository stockHistoryRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void addStock_shouldIncreaseStock() {
        Product product = new Product();
        product.setStock(10);

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));
        when(productRepository.save(product))
                .thenReturn(product);

        Product result = productService.addStock(1L, 5);

        assertEquals(15, result.getStock());

    }
    @Test
    void removeStock_shouldThrowWhenStockIsInsufficient() {
        Product product = new Product();
        product.setStock(10);

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        assertThrows(
                ResponseStatusException.class,
                ()-> productService.removeStock(1L,15)
        );
    }

    @Test
    void addStock_shouldThrowWhenQuantityIsZero() {
        Product product = new Product();
        product.setStock(10);

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        assertThrows(
                ResponseStatusException.class,
                ()-> productService.addStock(1L,0)
        );
    }

    @Test
    void removeStock_shouldThrowWhenQuantityIsZero() {
        Product product = new Product();
        product.setStock(10);

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        assertThrows(
                ResponseStatusException.class,
                ()-> productService.removeStock(1L,0)
        );
    }

    @Test
    void getProduct_shouldThrowWhenProductNotFound() {
        when(productRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResponseStatusException.class,
                () -> productService.getProduct(999L)
        );
    }

}