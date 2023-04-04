package org.lesli.WebApp.repository;

import org.lesli.WebApp.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByNameIs(String name);
    boolean existsProductByName(String name);
}

