package org.lesli.WebApp.services;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.lesli.WebApp.repository.CompanyRepository;
import org.lesli.WebApp.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ExcelParser excelParser;

    @Autowired
    private SaleRepository saleRepository;

    public void a() throws IOException, InvalidFormatException {
        saleRepository.saveAll(excelParser.process());
    }
}
