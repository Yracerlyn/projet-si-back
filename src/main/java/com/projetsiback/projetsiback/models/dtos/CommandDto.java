package com.projetsiback.projetsiback.models.dtos;

import com.projetsiback.projetsiback.models.Product;
import com.projetsiback.projetsiback.models.User;

import java.time.LocalDateTime;

public record CommandDto(
        int id,
        UserDto userDto,
        ProductDto productDto,
        LocalDateTime commandDateTime
) {

}