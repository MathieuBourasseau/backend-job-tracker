# Job Tracker — Back-end

> 🚧 **Projet en cours de développement.** Ce dépôt contient uniquement le back-end pour le moment (le front-end React/TypeScript sera développé séparément). Certaines fonctionnalités (authentification, gestion des erreurs centralisée) ne sont pas encore implémentées — voir la section [Roadmap](#roadmap).

Application de suivi de candidatures (recherche d'emploi/alternance), pensée pour remplacer un suivi manuel type Excel. Projet personnel de portfolio, développé pour démontrer une maîtrise de Java/Spring Boot sur un projet complet : authentification, CRUD, relations de données, logique métier.

## Stack technique

- **Java 25** / **Spring Boot 4.1**
- **Spring Data JPA** + **PostgreSQL**
- **Spring Boot Validation** (Bean Validation)
- Architecture en couches : `Controller → Service → Repository`, avec séparation DTO / Entité

## Fonctionnalités

### Implémentées
- Création de compte utilisateur (`User`)
- Gestion des candidatures (`Application`) : création, consultation (liste + détail), modification, suppression
- Gestion automatique des entreprises (`Company`) : réutilisation d'une entreprise existante par nom plutôt que duplication
- Historique des statuts (`Status`) horodaté et consultable, jamais écrasé — chaque changement de statut ajoute une nouvelle entrée
- Validation des données d'entrée (Bean Validation) sur les routes de création

### À venir
- Gestion centralisée des erreurs (`@ControllerAdvice`)
- Authentification (Spring Security + JWT) et isolation des données par utilisateur
- Relances automatiques (signalement à J+7 sans réponse)
- Tests (JUnit/Mockito)

## Modèle de données

Modélisation complète (MCD, MLD, MPD, dictionnaire de données) disponible dans [`documentation/Data/`](documentation/Data/).

4 entités : `User`, `Company`, `Application`, `Status` — une candidature référence un utilisateur et une entreprise, et possède un historique de statuts (relation one-to-many).

## API — aperçu des routes

| Méthode | Route | Description |
|---|---|---|
| `POST` | `/api/users` | Créer un compte utilisateur |
| `POST` | `/api/applications/users/{userId}` | Créer une candidature |
| `GET` | `/api/applications/users/{userId}` | Lister les candidatures d'un utilisateur |
| `GET` | `/api/applications/{applicationId}` | Récupérer une candidature |
| `PUT` | `/api/applications/{applicationId}` | Modifier une candidature |
| `DELETE` | `/api/applications/{applicationId}` | Supprimer une candidature |
| `POST` | `/api/statuses` | Ajouter un nouveau statut à une candidature |

> `userId` est actuellement passé explicitement dans l'URL — il sera extrait automatiquement du token JWT une fois l'authentification en place.

## Lancer le projet en local

**Prérequis** : JDK 25, Maven, PostgreSQL.

1. Créer la base de données PostgreSQL :
   ```sql
   CREATE DATABASE job_tracker_db;
   CREATE USER job_tracker_user WITH PASSWORD 'votre_mot_de_passe';
   ALTER DATABASE job_tracker_db OWNER TO job_tracker_user;
   ```
2. Définir les variables d'environnement `DB_USERNAME` et `DB_PASSWORD` (ex. via `.vscode/launch.json` en local).
3. Lancer l'application :
   ```bash
   ./mvnw spring-boot:run
   ```

## Roadmap

1. ~~Modélisation, entités, repositories, DTOs, services, controllers, validation~~
2. Gestion centralisée des erreurs
3. Authentification (Spring Security + JWT)
4. Tests manuels et automatisés
5. Front-end (React/TypeScript) — dépôt séparé
6. Déploiement (Render + Vercel)
