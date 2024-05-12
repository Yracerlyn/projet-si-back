package com.projetsiback.projetsiback.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("users")
public class User {
    @Id
    private int id;

    private String mail;

    private String password;

    private String avatar;

    private String lastName;

    private String firstName;

    private String address;

    private int companyNote;
}
