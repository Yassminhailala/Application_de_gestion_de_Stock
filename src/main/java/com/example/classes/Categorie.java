package com.example.classes;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "categorie")
public class Categorie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;

    @OneToMany(mappedBy = "categorie", fetch = FetchType.LAZY)
    private List<Produit> produits;

    public Categorie() {}

    // CONSTRUCTEUR MANQUANT - AJOUTEZ-LE
    public Categorie(String code, String libelle) {
        this.nom = libelle; // Utilisez 'nom' comme libelle
    }

    public Categorie(String nom) {
        this.nom = nom;
    }

    // Getters et Setters...
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public List<Produit> getProduits() { return produits; }
    public void setProduits(List<Produit> produits) { this.produits = produits; }
}