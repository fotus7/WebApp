package org.lesli.WebApp.repository;

import org.lesli.WebApp.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    Sale findTopByOrderByIdDesc();
}
