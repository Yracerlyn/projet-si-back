package com.projetsiback.projetsiback.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("resetPasswordRequests")
public class ResetPasswordRequest {
    private String email;
    private String newPassword;
}