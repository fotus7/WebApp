package org.lesli.WebApp.controllers;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.lesli.WebApp.dto.SaleDto;
import org.lesli.WebApp.services.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/update")
public class UpdateController {

    @Autowired
    SaleService saleService;

    @PostMapping
    @CrossOrigin(origins = "*")
    public SaleDto[] updateSales() throws IOException, InvalidFormatException {
        return saleService.updateData();
    }
}
