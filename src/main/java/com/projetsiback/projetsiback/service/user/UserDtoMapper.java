package com.projetsiback.projetsiback.service.user;

import com.projetsiback.projetsiback.models.User;
import com.projetsiback.projetsiback.models.dtos.UserDto;
import org.springframework.stereotype.Service;

import java.util.function.Function;
@Service
public class UserDtoMapper implements Function<User, UserDto> {
    @Override
    public UserDto apply(User user) {
        return new UserDto(
                user.getId(),
                user.getMail(),
                user.getAvatar(),
                user.getLastName(),
                user.getFirstName(),
                user.getAddress(),
                user.getCompanyNote(),
                user.getAccountStatus());
    }
}
