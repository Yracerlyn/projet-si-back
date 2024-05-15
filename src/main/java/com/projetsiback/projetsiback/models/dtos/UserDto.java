package com.projetsiback.projetsiback.models.dtos;

import com.projetsiback.projetsiback.models.AccountStatus;

public record UserDto(
         int id,
         String mail,
         String avatar,
         String lastName,
         String firstName,
         String address,
         int companyNote,
         AccountStatus accountStatus
) {

}
