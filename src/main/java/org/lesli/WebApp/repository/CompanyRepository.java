package org.lesli.WebApp.repository;

import org.lesli.WebApp.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository <Company, Long> {

    Company findByNameIs(String name);
    boolean existsCompanyByName(String name);
}
