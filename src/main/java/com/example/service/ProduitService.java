package com.example.service;
import com.example.util.HibernateUtil;
import com.example.classes.Categorie;
import com.example.classes.Commande;
import com.example.classes.Produit;
import org.hibernate.Session;
import org.hibernate.query.Query;
import java.util.Date;
import java.util.List;

public class ProduitService extends Service<Produit> {

    public ProduitService() {
        super(Produit.class);
    }

    // 1. Produits par catégorie
    public List<Produit> getProduitsByCategorie(Categorie categorie) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query<Produit> query = session.createQuery(
                    "FROM Produit p WHERE p.categorie = :categorie", Produit.class);
            query.setParameter("categorie", categorie);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) session.close();
        }
    }

    // 2. Produits commandés entre deux dates
    public List<Produit> getProduitsCommandesBetweenDates(Date startDate, Date endDate) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query<Produit> query = session.createQuery(
                    "SELECT DISTINCT lc.produit FROM LigneCommande lc " +
                            "WHERE lc.commande.dateCommande BETWEEN :startDate AND :endDate", Produit.class);
            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) session.close();
        }
    }

    // 3. Produits d'une commande spécifique (avec quantité)
    public List<Object[]> getProduitsByCommande(Commande commande) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query<Object[]> query = session.createQuery(
                    "SELECT lc.produit, lc.quantite FROM LigneCommande lc " +
                            "WHERE lc.commande = :commande", Object[].class);
            query.setParameter("commande", commande);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) session.close();
        }
    }

    // 4. Produits avec prix > 100 DH (requête nommée)
    public List<Produit> getProduitsPrixSuperieurA100() {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query<Produit> query = session.createNamedQuery("Produit.findPrixSup100", Produit.class);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) session.close();
        }
    }

    // Méthode pour afficher le détail d'une commande (comme dans l'exemple)
    public void afficherDetailsCommande(Commande commande) {
        List<Object[]> produitsAvecQuantite = getProduitsByCommande(commande);

        System.out.println("Commande : " + commande.getId() + "\tDate : " + commande.getDateCommande());
        System.out.println("Liste des produits :");
        System.out.println("Référence\tPrix\tQuantité");

        for (Object[] result : produitsAvecQuantite) {
            Produit p = (Produit) result[0];
            int quantite = (int) result[1];
            System.out.println(p.getReference() + "\t\t" + p.getPrix() + " DH\t" + quantite);
        }
    }
}