package com.example.classes;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "produit")
@NamedQuery(name = "Produit.findPrixSup100",
        query = "SELECT p FROM Produit p WHERE p.prix > 100")
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String reference;
    private double prix;
    private int quantite;

    @ManyToOne
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;

    @OneToMany(mappedBy = "produit", fetch = FetchType.LAZY)
    private List<LigneCommande> ligneCommandes;

    public Produit() {}

    public Produit(String reference, double prix, int quantite, Categorie categorie) {
        this.reference = reference;
        this.prix = prix;
        this.quantite = quantite;
        this.categorie = categorie;
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }
    public double getPrix() { return prix; }
    public void setPrix(double prix) { this.prix = prix; }
    public int getQuantite() { return quantite; }
    public void setQuantite(int quantite) { this.quantite = quantite; }
    public Categorie getCategorie() { return categorie; }
    public void setCategorie(Categorie categorie) { this.categorie = categorie; }
}
