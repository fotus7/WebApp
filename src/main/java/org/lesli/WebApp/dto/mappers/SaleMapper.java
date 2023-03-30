package org.lesli.WebApp.dto.mappers;

import org.lesli.WebApp.dto.SaleDto;
import org.lesli.WebApp.model.Sale;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class SaleMapper {

    public SaleDto toDto (Sale sale) {
        Date date = sale.getDate();
        String company = sale.getCompany().getName();
        String product = sale.getProduct().getName();
        double amount = sale.getAmount();
        return new SaleDto(date, company, product, amount);
    }
}
