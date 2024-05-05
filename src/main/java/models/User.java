package models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
