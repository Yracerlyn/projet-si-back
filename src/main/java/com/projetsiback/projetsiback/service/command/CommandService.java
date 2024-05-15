package com.projetsiback.projetsiback.service.command;

import com.projetsiback.projetsiback.models.Command;
import com.projetsiback.projetsiback.models.Product;
import com.projetsiback.projetsiback.models.Purchase;
import com.projetsiback.projetsiback.models.User;
import com.projetsiback.projetsiback.repository.CommandRepository;
import com.projetsiback.projetsiback.repository.ProductRepository;
import com.projetsiback.projetsiback.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CommandService {

    @Autowired
    private UserService userService;

    @Autowired
    private CommandRepository commandRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<Command> getCommandsByUserId() {
        return commandRepository.findByUserId(userService.getCurrentUser().getId());
    }

    public boolean createCommand(Command command) {
        double total = 0;
        for(Purchase purchase : command.getPurchases()){
            Product product = productRepository.findById(purchase.getProduct().getId());
            if(product == null)
                throw new RuntimeException("Le produit n'existe pas");
            if(product.getStock() < purchase.getQuantite())
                throw new RuntimeException("La quantité commandé excede le stock disponible");
            product.setStock(product.getStock() - purchase.getQuantite());
            purchase.setProduct(product);
            double purchasePrice =  calculatePrice(purchase);
            total += purchasePrice;
            productRepository.save(product);
            purchase.setProduct(product);
            purchase.setPurchasePrice(purchasePrice);
        }
        User user = userService.getCurrentUser();
        command.setTotal(total);
        command.setCommandDateTime(LocalDateTime.now());
        command.setUser(user);
        command.setDeliveryAddress(user.getAddress());
        commandRepository.save(command);
        return true;
    }

    public  List<Purchase>  getPurchaseInfo(Map<String,Integer> basket) {
        List<Purchase> purchases = new ArrayList<>();

        for (String productId : basket.keySet()) {
            Product product = productRepository.findById(Integer.parseInt(productId));
            Purchase purchase = new Purchase(product, basket.get(productId), 0);
            purchase.setPurchasePrice(calculatePrice(purchase));
            purchases.add(purchase);
        }

        return purchases;
    }

    private double calculatePrice(Purchase purchase){
        double productPrice;
        if(purchase.getProduct().getDiscount() == null || purchase.getProduct().getDiscount() == 0){
            productPrice = purchase.getProduct().getPrice();
        }else{
            productPrice =  purchase.getProduct().getPrice() - ( purchase.getProduct().getDiscount() *  purchase.getProduct().getPrice() / 100);
        }
        return purchase.getQuantite() * productPrice;
    }
}