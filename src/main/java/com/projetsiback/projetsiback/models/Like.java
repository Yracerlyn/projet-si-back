package com.projetsiback.projetsiback.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("likes")
public class Like {
    private User user;
    private Product product;
    LocalDateTime likeDateTime;
}
