package com.projetsiback.projetsiback.models.dtos;

public record UserDto(
         int id,
         String mail,
         String avatar,
         String lastName,
         String firstName,
         String address,
         int companyNote,
         boolean accountValidated
) {

}
