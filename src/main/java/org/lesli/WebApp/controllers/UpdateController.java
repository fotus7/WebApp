package org.lesli.WebApp.controllers;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.lesli.WebApp.dto.SaleDto;
import org.lesli.WebApp.services.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/update")
public class UpdateController {

    @Autowired
    SaleService saleService;

    @PostMapping
    @CrossOrigin("*")
    public SaleDto[] update() throws IOException, InvalidFormatException {
        List<SaleDto> sales = saleService.update();
        SaleDto[] salesArray = new SaleDto[sales.size()];
        sales.toArray(salesArray);
        System.out.println(Arrays.toString(salesArray));
        return salesArray;
    }
}
