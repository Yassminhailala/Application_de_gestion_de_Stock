package com.example.service;

import com.example.classes.LigneCommande;
import com.example.classes.Commande;
import com.example.classes.Produit;
import com.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;  // ← IMPORT CORRECT
import org.hibernate.query.Query;
import java.util.List;

public class LigneCommandeService extends Service<LigneCommande> {

    public LigneCommandeService() {
        super(LigneCommande.class);
    }

    // Méthode spécifique pour créer une ligne de commande avec tous les paramètres
    public LigneCommande createLigneCommande(int quantite, Commande commande, Produit produit) {
        LigneCommande ligneCommande = new LigneCommande(quantite, commande, produit);
        return create(ligneCommande);
    }

    // Récupérer toutes les lignes de commande d'une commande spécifique
    public List<LigneCommande> getLignesByCommande(Commande commande) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query<LigneCommande> query = session.createQuery(
                    "FROM LigneCommande lc WHERE lc.commande = :commande", LigneCommande.class);
            query.setParameter("commande", commande);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) session.close();
        }
    }

    // Récupérer les lignes de commande pour un produit spécifique
    public List<LigneCommande> getLignesByProduit(Produit produit) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query<LigneCommande> query = session.createQuery(
                    "FROM LigneCommande lc WHERE lc.produit = :produit", LigneCommande.class);
            query.setParameter("produit", produit);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) session.close();
        }
    }

    // Calculer la quantité totale commandée pour un produit
    public int getQuantiteTotaleByProduit(Produit produit) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query<Long> query = session.createQuery(
                    "SELECT SUM(lc.quantite) FROM LigneCommande lc WHERE lc.produit = :produit", Long.class);
            query.setParameter("produit", produit);
            Long result = query.uniqueResult();
            return result != null ? result.intValue() : 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            if (session != null) session.close();
        }
    }

    // Supprimer toutes les lignes d'une commande
    public boolean deleteLignesByCommande(Commande commande) {
        Session session = null;
        Transaction tx = null;  // ← CORRECT : org.hibernate.Transaction
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();

            Query<?> query = session.createQuery(
                    "DELETE FROM LigneCommande lc WHERE lc.commande = :commande");
            query.setParameter("commande", commande);
            int deletedCount = query.executeUpdate();

            tx.commit();  // ← CORRECT : méthode de org.hibernate.Transaction
            return deletedCount > 0;
        } catch (Exception e) {
            if (tx != null) tx.rollback();  // ← CORRECT : méthode de org.hibernate.Transaction
            e.printStackTrace();
            return false;
        } finally {
            if (session != null) session.close();
        }
    }
}