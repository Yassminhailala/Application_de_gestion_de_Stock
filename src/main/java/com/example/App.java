package com.example;

import com.example.classes.*;
import com.example.service.*;
import com.example.util.HibernateUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class App {
    public static void main(String[] args) {
        try {
            // Initialisation Hibernate
            HibernateUtil.getSessionFactory();

            // Création des services
            CategorieService categorieService = new CategorieService();
            ProduitService produitService = new ProduitService();
            CommandeService commandeService = new CommandeService();
            LigneCommandeService ligneCommandeService = new LigneCommandeService();

            System.out.println("=== APPLICATION DE GESTION DE STOCK ===");

            // TEST 1: Création de catégories
            System.out.println("\n1. CRÉATION DE CATÉGORIES");
            Categorie cat1 = new Categorie("CAT1", "Ordinateurs");
            Categorie cat2 = new Categorie("CAT2", "Périphériques");
            categorieService.create(cat1);
            categorieService.create(cat2);
            System.out.println("✅ Catégories créées");

            // TEST 2: Création de produits
            System.out.println("\n2. CRÉATION DE PRODUITS");
            Produit p1 = new Produit("ES12", 120, 10, cat1);
            Produit p2 = new Produit("ZR85", 100, 15, cat1);
            Produit p3 = new Produit("EE85", 200, 8, cat2);
            Produit p4 = new Produit("TEST50", 50, 20, cat2);
            produitService.create(p1);
            produitService.create(p2);
            produitService.create(p3);
            produitService.create(p4);
            System.out.println("✅ Produits créés");

            // TEST 3: Produits par catégorie
            System.out.println("\n3. PRODUITS PAR CATÉGORIE (Ordinateurs)");
            List<Produit> produitsParCategorie = produitService.getProduitsByCategorie(cat1);
            for (Produit p : produitsParCategorie) {
                System.out.println(" - " + p.getReference() + " : " + p.getPrix() + " DH");
            }

            // TEST 4: Produits avec prix > 100 DH
            System.out.println("\n4. PRODUITS AVEC PRIX > 100 DH");
            List<Produit> produitsChers = produitService.getProduitsPrixSuperieurA100();
            for (Produit p : produitsChers) {
                System.out.println(" - " + p.getReference() + " : " + p.getPrix() + " DH");
            }

            // TEST 5: Création de commandes
            System.out.println("\n5. CRÉATION DE COMMANDES");
            Commande cmd1 = new Commande(new Date());
            commandeService.create(cmd1);
            System.out.println("✅ Commande créée : " + cmd1.getId());

            // TEST 6: Ajout de lignes de commande
            System.out.println("\n6. AJOUT DE LIGNES DE COMMANDE");
            ligneCommandeService.createLigneCommande(7, cmd1, p1);
            ligneCommandeService.createLigneCommande(14, cmd1, p2);
            ligneCommandeService.createLigneCommande(5, cmd1, p3);
            System.out.println("✅ Lignes de commande ajoutées");

            // TEST 7: Affichage détail commande
            System.out.println("\n7. DÉTAIL DE LA COMMANDE");
            produitService.afficherDetailsCommande(cmd1);

            System.out.println("\n=== TOUS LES TESTS RÉUSSIS ===");

        } catch (Exception e) {
            System.err.println("❌ Erreur : " + e.getMessage());
            e.printStackTrace();
        } finally {
            HibernateUtil.shutdown();
        }
    }
}