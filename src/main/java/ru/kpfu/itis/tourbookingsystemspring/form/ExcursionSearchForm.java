package ru.kpfu.itis.tourbookingsystemspring.form;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ExcursionSearchForm {

    private String q;
    private String city;
    private Long categoryId;
    private BigDecimal maxPrice;
    private Integer page = 0;
    private Integer size = 12;
}