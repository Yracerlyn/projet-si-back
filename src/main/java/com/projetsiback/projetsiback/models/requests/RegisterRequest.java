package com.projetsiback.projetsiback.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("registerRequests")
public class RegisterRequest {

    private String email;
    private String firstName;
    private String lastName;
    private String address;
    private String password;
}
