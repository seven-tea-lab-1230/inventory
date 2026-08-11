package Jar;

import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepositoryを継承するだけで検索・保存・削除などが自動で使えるようになる
public interface ProductRepository extends JpaRepository<Product, Long> {
}