package com.projetsiback.projetsiback.service.command;

import com.projetsiback.projetsiback.models.Command;
import com.projetsiback.projetsiback.models.Purchase;
import com.projetsiback.projetsiback.models.dtos.CommandDto;
import com.projetsiback.projetsiback.models.dtos.PurchaseDto;
import com.projetsiback.projetsiback.service.product.ProductDtoMapper;
import com.projetsiback.projetsiback.service.user.UserDtoMapper;

import java.util.function.Function;

public class PurchaseDtoMapper implements Function<Purchase, PurchaseDto> {


    private ProductDtoMapper productDtoMapper;

    @Override
    public PurchaseDto apply(Purchase purchase) {
        return new PurchaseDto(
                purchase.getQuantite(),
                productDtoMapper.apply(purchase.getProduct()),
                purchase.getPurchasePrice());
    }
}