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
    public ResponseEntity<?> getCommandes(@PathVariable int userId) {
        List<Command> commandes = commandService.getCommandsByUserId(userId);
        if (!commandes.isEmpty()) {
            return ResponseEntity.ok().body(commandes);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message("Aucune commande trouvée pour l'utilisateur avec l'ID : " + userId));
        }
    }

    @PostMapping("/commander")
    public ResponseEntity<?> commander(@RequestBody Command command) {
        boolean commandeValide = commandService.createCommand(command);
        if (commandeValide) {
            return ResponseEntity.ok().body(command);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message("La commande n'a pas pu être créée."));
        }
    }
}
