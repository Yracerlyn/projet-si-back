package com.projetsiback.projetsiback.service.product;

import com.projetsiback.projetsiback.models.CategorieDto;
import com.projetsiback.projetsiback.models.Product;
import com.projetsiback.projetsiback.models.User;
import com.projetsiback.projetsiback.models.dtos.ProductDto;
import com.projetsiback.projetsiback.models.dtos.UserDto;
import com.projetsiback.projetsiback.repository.ProductRepository;
import com.projetsiback.projetsiback.repository.UserRepository;
import com.projetsiback.projetsiback.service.SequenceGeneratorService;
import com.projetsiback.projetsiback.service.user.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final SequenceGeneratorService sequenceGeneratorService;
    private final UserService userService;

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

    public boolean likeProduct(int productId) {
        Product product = productRepository.findById(productId);
        Optional<User> user = userRepository.findById(userService.getCurrentUser().getId());

        if (product != null && user != null) {
            if (!product.getLikedBy().contains(user)) {
                product.getLikedBy().add(user.orElse(null));
                product.setNbLike(product.getNbLike() + 1);
                productRepository.save(product);
                return true;
            }
        }
        return false; // Retourne false si le produit ou l'utilisateur n'existe pas, ou si le produit a déjà été aimé par l'utilisateur
    }

    public boolean unlikeProduct(int productId) {
        Product product = productRepository.findById(productId);
        User user = userRepository.findById(userService.getCurrentUser().getId()).orElse(null);

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
        int productId = sequenceGeneratorService.generateSequence("productId");
        product.setId(productId);
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
