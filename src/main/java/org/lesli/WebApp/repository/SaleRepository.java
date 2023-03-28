package org.lesli.WebApp.repository;

import org.lesli.WebApp.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Long> {
}
