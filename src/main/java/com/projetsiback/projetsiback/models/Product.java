package com.projetsiback.projetsiback.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("products")
public class Product {
    @Id
    private int id;
    private String name;
    private long nbLike;
    private List<User> likedBy;
    private String category;
    private Double discount;
    private double price;
    private String description;
    private int stock;
    private String comment;
    private User managedBy;
    private Date addedDate;
    private LocalDateTime modifiedDate;
    private String image;
}
