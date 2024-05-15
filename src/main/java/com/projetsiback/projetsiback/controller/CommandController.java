package com.projetsiback.projetsiback.controller;

import com.projetsiback.projetsiback.message.Message;
import com.projetsiback.projetsiback.models.Command;
import com.projetsiback.projetsiback.service.command.CommandService;
import com.projetsiback.projetsiback.service.SequenceGeneratorService;
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

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @GetMapping("/get-commandes/")
    public ResponseEntity<?> getCommandes() {
        List<Command> commandes = commandService.getCommandsByUserId();
        return ResponseEntity.ok().body(commandes);
    }

    @PostMapping("/commander")
    public ResponseEntity<?> commander(@RequestBody Command command) {
        int commandeId = sequenceGeneratorService.generateSequence("command_sequence");
        command.setId(commandeId);

        boolean commandeValide = commandService.createCommand(command);
        if (commandeValide) {
            return ResponseEntity.ok().body(command);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message("La commande n'a pas pu être créée."));
        }
    }
}
