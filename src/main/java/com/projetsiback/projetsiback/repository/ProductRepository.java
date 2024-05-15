package com.projetsiback.projetsiback.repository;

import com.projetsiback.projetsiback.models.Product;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product, Integer> {
    List<Product> findByCategory(String category);
    Product findById(int id);
    @Aggregation("{ $group: { _id: '$category' } }")
    List<String> findAllCategories();
}