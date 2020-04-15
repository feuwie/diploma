package com.simakov.diploma.Repository;

import java.util.List;

import com.simakov.diploma.Model.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByParentId(int parent);

    List<Product> findByProductTitleLike(String title);

    Product findByProductArticle(int article);

    Product findByProductId(int productId);
}
