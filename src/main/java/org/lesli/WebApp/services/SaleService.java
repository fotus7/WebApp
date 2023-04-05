package org.lesli.WebApp.services;

import jakarta.transaction.Transactional;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.lesli.WebApp.dto.SaleDto;
import org.lesli.WebApp.dto.mappers.SaleMapper;
import org.lesli.WebApp.model.Sale;
import org.lesli.WebApp.parsers.ExcelParser;
import org.lesli.WebApp.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class SaleService {

    @Autowired
    private ExcelParser excelParser;

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private SaleMapper saleMapper;

    public void saveOld() throws IOException, InvalidFormatException {
        saleRepository.saveAll(excelParser.getInitialData());
    }

    @Transactional
    private void save(Set<Sale> sales) {
        saleRepository.saveAll(sales);

    }

    private SaleDto[] convert (Set<Sale> sales) {
        List<SaleDto> salesDto = new ArrayList<>();
        for (Sale s : sales.stream().toList()) {
            salesDto.add(saleMapper.toDto(s));
        }
        SaleDto[] salesArray = new SaleDto[sales.size()];
        salesDto.toArray(salesArray);
        return salesArray;
    }

    public SaleDto[] update() throws IOException, InvalidFormatException {
        Set<Sale> sales = excelParser.updateData();
        save(sales);
        return convert(sales);
    }

    public SaleDto[] get() throws IOException, InvalidFormatException {
        Set<Sale> sales = excelParser.getInitialData();
        save(sales);
        return convert(sales);
    }
}
