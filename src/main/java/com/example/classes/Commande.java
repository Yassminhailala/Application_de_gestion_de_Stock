package com.example.classes;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Date; // AJOUTEZ CET IMPORT
import java.util.List;

@Entity
@Table(name = "commande")
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDate dateCommande;

    @OneToMany(mappedBy = "commande", fetch = FetchType.LAZY)
    private List<LigneCommande> ligneCommandes;

    public Commande() {}

    public Commande(LocalDate dateCommande) {
        this.dateCommande = dateCommande;
    }

    // CONSTRUCTEUR MANQUANT - AJOUTEZ-LE
    public Commande(Date date) {
        this.dateCommande = LocalDate.now(); // Ou convertissez Date en LocalDate
    }

    // Getters et Setters...
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public LocalDate getDateCommande() { return dateCommande; }
    public void setDateCommande(LocalDate dateCommande) { this.dateCommande = dateCommande; }
    public List<LigneCommande> getLigneCommandes() { return ligneCommandes; }
    public void setLigneCommandes(List<LigneCommande> ligneCommandes) { this.ligneCommandes = ligneCommandes; }
}