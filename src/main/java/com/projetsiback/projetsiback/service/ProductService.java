package com.projetsiback.projetsiback.service;

import com.projetsiback.projetsiback.models.CategorieDto;
import com.projetsiback.projetsiback.models.Product;
import com.projetsiback.projetsiback.models.User;
import com.projetsiback.projetsiback.repository.ProductRepository;
import com.projetsiback.projetsiback.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    private UserRepository userRepository;

    public List<CategorieDto> getAllCategories() {
        List<String> categoryNames = productRepository.findAllCategories();
        List<CategorieDto> categories = new ArrayList<>();
        for (String categoryName : categoryNames) {
            categories.add(new CategorieDto(categoryName));
        }
        return categories;
    }

    public List<Product> getAllProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    public Product getProductById(int productId) {
        return productRepository.findById(productId);
    }

    public boolean likeProduct(int productId, int userId) {
        Product product = productRepository.findById(productId);
        User user = userRepository.findById(userId).orElse(null);

        if (product != null && user != null) {
            if (!product.getLikedBy().contains(user)) {
                product.getLikedBy().add(user);
                product.setNbLike(product.getNbLike() + 1);
                productRepository.save(product);
                return true;
            }
        }
        return false; // Retourne false si le produit ou l'utilisateur n'existe pas, ou si le produit a déjà été aimé par l'utilisateur
    }

    public boolean unlikeProduct(int productId, int userId) {
        Product product = productRepository.findById(productId);
        User user = userRepository.findById(userId).orElse(null);

        if (product != null && user != null) {
            if (product.getLikedBy().contains(user)) {
                product.getLikedBy().remove(user);
                product.setNbLike(product.getNbLike() - 1);
                productRepository.save(product);
                return true;
            }
        }
        return false; // Retourne false si le produit ou l'utilisateur n'existe pas, ou si le produit n'a pas été aimé par l'utilisateur
    }
    public boolean addProduct(Product product) {
        productRepository.save(product);
        return true;
    }

    public boolean updateProduct(com.projetsiback.projetsiback.models.Product product) {
        productRepository.save(product);
        return true;
    }

    public boolean deleteProduct(int productId) {
        productRepository.deleteById(productId);
        return true;
    }
}
