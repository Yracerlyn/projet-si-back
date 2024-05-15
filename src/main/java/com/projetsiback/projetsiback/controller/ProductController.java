package com.projetsiback.projetsiback.controller;

import com.projetsiback.projetsiback.message.Message;
import com.projetsiback.projetsiback.models.CategorieDto;
import com.projetsiback.projetsiback.models.Like;
import com.projetsiback.projetsiback.models.Product;
import com.projetsiback.projetsiback.models.dtos.ProductDto;
import com.projetsiback.projetsiback.service.product.ProductDtoMapper;
import com.projetsiback.projetsiback.service.product.ProductService;
import com.projetsiback.projetsiback.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/produit")
@CrossOrigin("http://localhost:3000")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/categories")
    public ResponseEntity<List<CategorieDto>> getAllCategories() {
        List<CategorieDto> categories = productService.getAllCategories();
        return ResponseEntity.ok().body(categories);
    }

    @GetMapping("/get/all/{categorie}")
    public ResponseEntity<List<Product>> getAllProductsByCategory(@PathVariable String categorie) {
        List<Product> produits = productService.getAllProductsByCategory(categorie);
        return ResponseEntity.ok().body(produits);
    }

    @GetMapping("/get/{produitId}")
    public ResponseEntity<Product> getProductById(@PathVariable int produitId) {
        Product produit = productService.getProductById(produitId);
        return ResponseEntity.ok().body(produit);
    }

    @PostMapping("/like/{produitId}")
    public ResponseEntity<?> likeProduct(@PathVariable String produitId) {
        boolean produitLike = productService.likeProduct(Integer.parseInt(produitId));
        if (produitLike) {
            return ResponseEntity.ok().body(true);
        } else {
            return ResponseEntity.internalServerError().body(new Message("Failed to like the product"));
        }
    }

    @PostMapping("/unlike/{produitId}")
    public ResponseEntity<?> unlikeProduct(@PathVariable int produitId) {
        boolean produitLike = productService.unlikeProduct(produitId);
        if (produitLike) {
            return ResponseEntity.ok().body(true);
        } else {
            return ResponseEntity.internalServerError().body(new Message("Failed to unlike the product"));
        }
    }

    @PostMapping("/ajouter-produit")
    public ResponseEntity<?> addProduct(@RequestBody Product product) {
        boolean addedProduct = productService.addProduct(product);
        if (addedProduct) {
            return ResponseEntity.ok().body(true);
        } else {
            return ResponseEntity.internalServerError().body(new Message("Échec de l'ajout du produit"));
        }
    }
    @DeleteMapping("/supprimer-produit/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable int productId) {
        Product product = productService.getProductById(productId);
        if(product.getStock() > 0){
            return ResponseEntity.badRequest().body(new Message("Le stock n'est pas à 0"));
        }
        boolean produitSupprime = productService.deleteProduct(productId);
        if (produitSupprime) {
            return ResponseEntity.ok().body(new Message("Product deleted successfully"));
        } else {
            return ResponseEntity.internalServerError().body(new Message("Failed to delete product"));
        }
    }

    @PutMapping("/mettre-a-jour")
    public ResponseEntity<?> updateProduct(@RequestBody Product product) {
        boolean produitMisAJour = productService.updateProduct(product);
        if (produitMisAJour) {
            return ResponseEntity.ok().body(product);
        } else {
            return ResponseEntity.internalServerError().body(new Message("Failed to update product"));
        }
    }

}
