package com.projetsiback.projetsiback.service.command;

import com.projetsiback.projetsiback.models.Command;
import com.projetsiback.projetsiback.repository.CommandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommandService {
    @Autowired
    private CommandRepository commandRepository;

    public List<Command> getCommandsByUserId(int userId) {
        return commandRepository.findByUserId(userId);
    }

    public boolean createCommand(Command command) {
        commandRepository.save(command);
        return true;
    }
}