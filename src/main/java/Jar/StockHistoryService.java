package Jar;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockHistoryService {
    private final StockHistoryRepository stockHistoryRepository;
    public StockHistoryService(StockHistoryRepository stockHistoryRepository) {
        this.stockHistoryRepository = stockHistoryRepository;
    }

    public List<StockHistory> getHistory(Long productId) {
        return stockHistoryRepository.findByProductIdOrderByCreatedAtDesc(productId);
    }
}
