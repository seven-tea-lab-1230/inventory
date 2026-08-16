package Jar;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StockHistoryController {
    private final StockHistoryService stockHistoryService;
    public StockHistoryController(StockHistoryService stockHistoryService) {
        this.stockHistoryService = stockHistoryService;
    }
    @GetMapping("/product/{productId}/history")
    public List<StockHistory> getHistory(@PathVariable Long productId) {
        return stockHistoryService.getHistory(productId);
    }
}