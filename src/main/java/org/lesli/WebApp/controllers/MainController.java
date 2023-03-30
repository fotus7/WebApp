package org.lesli.WebApp.controllers;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.lesli.WebApp.dto.SaleDto;
import org.lesli.WebApp.model.Sale;
import org.lesli.WebApp.services.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/sales")
public class MainController {

    @Autowired
    private SaleService saleService;

    @GetMapping
    @CrossOrigin (origins = "*")
    public SaleDto[] test() {
        List<SaleDto> sales = saleService.get();
        SaleDto[] salesArray = new SaleDto[sales.size()];
        sales.toArray(salesArray);
        return salesArray;
    }
}