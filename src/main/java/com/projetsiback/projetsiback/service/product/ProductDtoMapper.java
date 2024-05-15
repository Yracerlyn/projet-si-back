package com.projetsiback.projetsiback.service.product;

import com.projetsiback.projetsiback.models.Command;
import com.projetsiback.projetsiback.models.Product;
import com.projetsiback.projetsiback.models.User;
import com.projetsiback.projetsiback.models.dtos.CommandDto;
import com.projetsiback.projetsiback.models.dtos.ProductDto;
import com.projetsiback.projetsiback.models.dtos.UserDto;
import com.projetsiback.projetsiback.service.user.UserDtoMapper;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;
@Service
public class ProductDtoMapper implements Function<Product, ProductDto> {
    UserDtoMapper userDtoMapper;
    @Override
    public ProductDto apply(Product product) {
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getNbLike(),
                product.getLikedBy().stream()
                        .map(userDtoMapper::apply)
                        .collect(Collectors.toList()),
                product.getCategory(),
                product.getDiscount(),
                product.getPrice(),
                product.getDescription(),
                product.getNote(),
                product.getComment(),
                userDtoMapper.apply(product.getManagedBy()),
                product.getAddedDate(),
                product.getModifiedDate());

    }
}
