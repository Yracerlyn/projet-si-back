package com.projetsiback.projetsiback.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("authResponses")
public class AuthResponse {

    private String token;
    private String refreshToken;

}
