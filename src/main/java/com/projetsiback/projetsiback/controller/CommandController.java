package com.projetsiback.projetsiback.controller;

import com.projetsiback.projetsiback.message.Message;
import com.projetsiback.projetsiback.models.Command;
import com.projetsiback.projetsiback.models.Product;
import com.projetsiback.projetsiback.models.Purchase;
import com.projetsiback.projetsiback.repository.ProductRepository;
import com.projetsiback.projetsiback.service.command.CommandService;
import com.projetsiback.projetsiback.service.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/commandes")
@CrossOrigin("http://localhost:3000")
public class CommandController {

    @Autowired
    private CommandService commandService;

    @Autowired
    private ProductRepository productRepository;

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

    @PostMapping("/basket-info")
    public ResponseEntity<?> getPurchaseInfo(@RequestBody Map<String,Integer> basket) {
        List<Purchase> purchases = commandService.getPurchaseInfo(basket);
        if (!purchases.isEmpty()) {
            return ResponseEntity.ok().body(purchases);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message("La commande n'a pas pu être créée."));
        }
    }
}
