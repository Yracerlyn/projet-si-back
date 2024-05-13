package com.projetsiback.projetsiback.controller;

import com.projetsiback.projetsiback.message.Message;
import com.projetsiback.projetsiback.models.CategorieDto;
import com.projetsiback.projetsiback.models.Like;
import com.projetsiback.projetsiback.models.Product;
import com.projetsiback.projetsiback.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produit")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/categories")
    public ResponseEntity<List<CategorieDto>> getAllCategories() {
        List<CategorieDto> categories = productService.getAllCategories();
        return ResponseEntity.ok().body(categories);
    }

    @GetMapping("/get-all-produits/{categorie}")
    public ResponseEntity<List<Product>> getAllProductsByCategory(@PathVariable String categorie) {
        List<Product> produits = productService.getAllProductsByCategory(categorie);
        return ResponseEntity.ok().body(produits);
    }

    @GetMapping("/get-produit/{produitId}")
    public ResponseEntity<Product> getProductById(@PathVariable int produitId) {
        Product produit = productService.getProductById(produitId);
        return ResponseEntity.ok().body(produit);
    }

    @PostMapping("/like")
    public ResponseEntity<Message> likeProduct(@RequestBody Like like) {
        boolean produitLike = productService.likeProduct(like.getProduct().getId(), like.getUser().getId());
        String messageContent = produitLike ? "Product liked successfully" : "Failed to like the product";
        return ResponseEntity.ok().body(new Message(messageContent));
    }

    @PostMapping("/unlike")
    public ResponseEntity<Message> unlikeProduct(@RequestBody Like like) {
        boolean produitLike = productService.unlikeProduct(like.getProduct().getId(), like.getUser().getId());
        String messageContent = produitLike ? "Product unliked successfully" : "Failed to unlike the product";
        return ResponseEntity.ok().body(new Message(messageContent));
    }

    @PostMapping("/ajouter-produit")
    public ResponseEntity<Message> addProduct(@RequestBody Product product) {
        boolean produitAjoute = productService.addProduct(product);
        if (produitAjoute) {
            return ResponseEntity.ok().body(new Message("Product added successfully"));
        } else {
            return ResponseEntity.badRequest().body(new Message("Failed to add product: " + product.getId()));
        }
    }

    @DeleteMapping("/supprimer-produit")
    public ResponseEntity<Message> deleteProduct(@RequestBody int productId) {
        boolean produitSupprime = productService.deleteProduct(productId);
        if (produitSupprime) {
            return ResponseEntity.ok().body(new Message("Product deleted successfully"));
        } else {
            return ResponseEntity.badRequest().body(new Message("Failed to delete product"));
        }
    }

    @PutMapping("/mettre-a-jour")
    public ResponseEntity<Message> updateProduct(@RequestBody Product product) {
        boolean produitMisAJour = productService.updateProduct(product);
        if (produitMisAJour) {
            return ResponseEntity.ok().body(new Message("Product updated successfully"));
        } else {
            return ResponseEntity.badRequest().body(new Message("Failed to update product"));
        }
    }
}
