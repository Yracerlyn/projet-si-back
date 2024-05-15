package com.projetsiback.projetsiback.service.command;

import com.projetsiback.projetsiback.models.Command;
import com.projetsiback.projetsiback.models.User;
import com.projetsiback.projetsiback.models.dtos.CommandDto;
import com.projetsiback.projetsiback.models.dtos.UserDto;
import com.projetsiback.projetsiback.service.product.ProductDtoMapper;
import com.projetsiback.projetsiback.service.user.UserDtoMapper;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class CommandDtoMapper implements Function<Command, CommandDto> {

    private UserDtoMapper userDtoMapper;
    private ProductDtoMapper productDtoMapper;

    private PurchaseDtoMapper purchaseDtoMapper;

    @Override
    public CommandDto apply(Command command) {
        return new CommandDto(
                command.getId(),
                userDtoMapper.apply(command.getUser()),
                command.getPurchases().stream().map(purchaseDtoMapper).toList(),
                command.getCommandDateTime(),
                command.getTotal(),
                command.getDeliveryAddress());
    }
}
