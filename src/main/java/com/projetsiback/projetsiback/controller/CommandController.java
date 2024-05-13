package com.projetsiback.projetsiback.controller;

import com.projetsiback.projetsiback.message.Message;
import com.projetsiback.projetsiback.models.Command;
import com.projetsiback.projetsiback.service.command.CommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/commandes")
public class CommandController {

    @Autowired
    private CommandService commandService;

    @GetMapping("/get-commandes/{userId}")
    public ResponseEntity<List<Command>> getCommandes(@PathVariable int userId) {
        List<Command> commandes = commandService.getCommandsByUserId(userId);
        return ResponseEntity.ok().body(commandes);
    }

    @PostMapping("/commander")
    public ResponseEntity<Message> commander(@RequestBody Command command) {
        boolean commandeValide = commandService.createCommand(command);
        if (commandeValide) {
            Message message = new Message("La commande a été validement créée.");
            return ResponseEntity.ok().body(message);
        } else {
            Message message = new Message("La commande n'a pas pu être créée.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
    }
}
