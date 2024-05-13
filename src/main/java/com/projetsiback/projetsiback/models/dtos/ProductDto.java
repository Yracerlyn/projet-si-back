package com.projetsiback.projetsiback.models.dtos;

import com.projetsiback.projetsiback.models.User;

import java.time.LocalDateTime;
import java.util.List;

public record ProductDto(
        int id,
        String name,
        long nbLike,
        List<UserDto>likedBy,
        String category,
        double discount,
        double price,
        String description,
        double note,
        String comment,
        UserDto managedBy,
        LocalDateTime addedDate,
        LocalDateTime modifiedDate
        ) {
}
