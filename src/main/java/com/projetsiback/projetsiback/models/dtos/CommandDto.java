package com.projetsiback.projetsiback.models.dtos;

import com.projetsiback.projetsiback.models.Product;
import com.projetsiback.projetsiback.models.Purchase;
import com.projetsiback.projetsiback.models.User;

import java.time.LocalDateTime;
import java.util.List;

public record CommandDto(
        int id,
        UserDto user,
        List<PurchaseDto> purchases,
        LocalDateTime orderDateTime,
        Double total,
        String deliveryAddress
) {

}