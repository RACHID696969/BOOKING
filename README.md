*BOOKING

# Projet Booking - Application de Réservation

Projet Java développé pour le cours ING3 2025.

## Équipe

- Mathis FAURE
- ILHA Rachid

## Description

Application de réservation d'hébergements avec interface graphique JavaFX. 
le but est de permettre aux utilisateurs de rechercher et réserver des hôtels, appartements et autres logements.

## Technologies

- Java 19
- JavaFX
- MySQL
- Architecture MVC + DAO + JDBC

## Structure du Projet

```
src/
├── Controleur/
├── Modele/
├── DAO/
└── Vue/
```

## Fonctionnalités

- Connexion et inscription des utilisateurs
- Recherche d'hébergements avec filtres
- Réservation avec calcul des prix
- Gestion des réservations
- Panel d'administration
- Système de réductions automatiques

## Configuration de la Base de Données

```java
URL = "jdbc:mysql://localhost:3306/BOOKING"
USER = "root"
PASSWORD = "azerty"
```

## Installation

1. Importer le projet dans IntelliJ IDEA
2. Configurer MySQL avec la base de données BOOKING
3. Modifier les paramètres de connexion dans ConnexionBD.java
4. Lancer Main.java

## Utilisation

Deux types d'utilisateurs :
- Client : accès aux réservations
- Admin : gestion complète de l'application

## Notes

- Les anciens clients bénéficient de 10% de réduction
- Réduction supplémentaire de 5% pour les réservations > 500€
- Réduction maximale : 30%