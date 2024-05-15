package com.projetsiback.projetsiback.service.command;

import com.projetsiback.projetsiback.models.Command;
import com.projetsiback.projetsiback.repository.CommandRepository;
import com.projetsiback.projetsiback.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommandService {

    @Autowired
    private UserService userService;

    @Autowired
    private CommandRepository commandRepository;

    public List<Command> getCommandsByUserId() {
        return commandRepository.findByUserId(userService.getCurrentUser().getId());
    }

    public boolean createCommand(Command command) {
        commandRepository.save(command);
        return true;
    }
}