package org.lesli.WebApp.services;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.lesli.WebApp.dto.SaleDto;
import org.lesli.WebApp.dto.mappers.SaleMapper;
import org.lesli.WebApp.model.Sale;
import org.lesli.WebApp.parsers.InitialParser;
import org.lesli.WebApp.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SaleService {

    @Autowired
    private InitialParser initialParser;

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private SaleMapper saleMapper;

    public void save() throws IOException, InvalidFormatException {
        saleRepository.saveAll(initialParser.process());
    }
    public List<SaleDto> get() {
        List<Sale> sales = saleRepository.findAll();
        List<SaleDto> salesDto = new ArrayList<>();
        for (Sale s : sales) {
            salesDto.add(saleMapper.toDto(s));
        }
        return salesDto;
    }
}
