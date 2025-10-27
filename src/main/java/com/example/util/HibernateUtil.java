package com.example.util;

import com.example.classes.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                configuration.configure("hibernate.cfg.xml");

                // Ajouter toutes les classes entités
                configuration.addAnnotatedClass(Categorie.class);
                configuration.addAnnotatedClass(Produit.class);
                configuration.addAnnotatedClass(Commande.class);
                configuration.addAnnotatedClass(LigneCommande.class);

                sessionFactory = configuration.buildSessionFactory();
                System.out.println("✅ Toutes les entités enregistrées dans Hibernate");
            } catch (Exception e) {
                System.err.println("❌ Erreur configuration Hibernate: " + e.getMessage());
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        return sessionFactory;
    }

    public static void shutdown() {
        System.out.println("🔚 Arrêt d'Hibernate...");
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}