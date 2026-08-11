package Jar;
// このファイルがどのフォルダ（パッケージ）にいるかを宣言

import jakarta.persistence.*; // DBと連携するための機能をまとめて読み込む

    @Entity // 「このクラスはDBのテーブルと対応してますよ」とSpringに伝える
    @Table(name = "products") // 対応するテーブル名は「products」
    public class Product {

        @Id // 「このフィールドが主キー（レコードを一意に識別するもの）です」
        @GeneratedValue(strategy = GenerationType.IDENTITY) // idは1,2,3...と自動で連番にする
        private Long id;

        private String name;  // 商品名
        private int stock;    // 在庫数

        // 以下はgetter/setter（フィールドの値を取得・セットするメソッド）
        // Javaではフィールドをprivateにして外からは必ずこれ経由でアクセスする

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public int getStock() { return stock; }
        public void setStock(int stock) { this.stock = stock; }

    }