package com.projetsiback.projetsiback.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Purchase {
    private Product product;
    private int quantite;
    private double purchasePrice;
}
