# Job Tracker — Back-end

> 🚧 **Projet en cours de développement.** Ce dépôt contient uniquement le back-end pour le moment (le front-end React/TypeScript sera développé séparément). Les tests automatisés sont encore en cours d'écriture — voir la section [Roadmap](#roadmap).

Application de suivi de candidatures (recherche d'emploi/alternance), pensée pour remplacer un suivi manuel type Excel. Projet personnel de portfolio, développé pour démontrer une maîtrise de Java/Spring Boot sur un projet complet : authentification, CRUD, relations de données, logique métier.

## Stack technique

- **Java 25** / **Spring Boot 4.1**
- **Spring Data JPA** + **PostgreSQL**
- **Spring Security** + **JJWT** (authentification par token)
- **Spring Boot Validation** (Bean Validation)
- **JUnit 5** + **Mockito** (tests unitaires)
- Architecture en couches : `Controller → Service → Repository`, avec séparation DTO / Entité

## Fonctionnalités

### Implémentées
- Inscription (`User`) avec mot de passe haché (BCrypt) et connexion (JWT)
- Authentification par token sur toutes les routes protégées, avec isolation des données : chaque utilisateur n'a accès qu'à ses propres candidatures
- Gestion des candidatures (`Application`) : création, consultation (liste + détail), modification, suppression
- Gestion automatique des entreprises (`Company`) : réutilisation d'une entreprise existante par nom plutôt que duplication
- Historique des statuts (`Status`) horodaté et consultable, jamais écrasé — chaque changement de statut ajoute une nouvelle entrée
- Validation des données d'entrée (Bean Validation) sur les routes de création
- Gestion centralisée des erreurs (`@RestControllerAdvice`)

### À venir
- Relances automatiques (signalement à J+7 sans réponse)
- Suite de tests automatisés complète (unitaires en cours, intégration à venir)

## Modèle de données

Modélisation complète (MCD, MLD, MPD, dictionnaire de données) disponible dans [`documentation/Data/`](documentation/Data/).

4 entités : `User`, `Company`, `Application`, `Status` — une candidature référence un utilisateur et une entreprise, et possède un historique de statuts (relation one-to-many).

## API — aperçu des routes

| Méthode | Route | Auth requise | Description |
|---|---|---|---|
| `POST` | `/api/users` | Non | Créer un compte utilisateur |
| `POST` | `/api/auth/login` | Non | Se connecter, renvoie un token JWT |
| `POST` | `/api/applications` | Oui | Créer une candidature |
| `GET` | `/api/applications` | Oui | Lister les candidatures de l'utilisateur connecté |
| `GET` | `/api/applications/{applicationId}` | Oui | Récupérer une candidature (si elle appartient à l'utilisateur connecté) |
| `PUT` | `/api/applications/{applicationId}` | Oui | Modifier une candidature |
| `DELETE` | `/api/applications/{applicationId}` | Oui | Supprimer une candidature |
| `POST` | `/api/statuses` | Oui | Ajouter un nouveau statut à une candidature |

> Les routes protégées attendent un header `Authorization: Bearer <token>`. L'identité de l'utilisateur est extraite automatiquement du token — jamais transmise par le client dans l'URL ou le body.

## Lancer le projet en local

**Prérequis** : JDK 25, Maven, PostgreSQL.

1. Créer la base de données PostgreSQL :
   ```sql
   CREATE DATABASE job_tracker_db;
   CREATE USER job_tracker_user WITH PASSWORD 'votre_mot_de_passe';
   ALTER DATABASE job_tracker_db OWNER TO job_tracker_user;
   ```
2. Définir les variables d'environnement `DB_USERNAME`, `DB_PASSWORD` et `JWT_SECRET` (ex. via `.vscode/launch.json` en local — jamais commitées).
3. Lancer l'application :
   ```bash
   ./mvnw spring-boot:run
   ```

## Roadmap

1. ~~Modélisation, entités, repositories, DTOs, services, controllers, validation~~
2. ~~Gestion centralisée des erreurs~~
3. ~~Authentification (Spring Security + JWT)~~
4. Tests automatisés (unitaires en cours, intégration à venir)
5. Front-end (React/TypeScript) — dépôt séparé
6. Déploiement (Render + Vercel)
