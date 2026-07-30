# MPD — Tracker de candidatures

Modèle physique de données : précise les types SQL et contraintes exacts pour chaque table, en s'appuyant sur le MLD. Comme le projet utilise Hibernate (JPA) avec `ddl-auto=update`, ce document n'est pas exécuté tel quel en SQL — les tables seront générées automatiquement par Hibernate à partir des entités `@Entity`. Il sert de feuille de route pour écrire ces entités et leurs annotations (`@Column`, `@NotNull`...).

## USER

| Champ | Type | Contraintes |
|---|---|---|
| id | BIGINT | PK, auto-incrémenté |
| email | VARCHAR(255) | NOT NULL, UNIQUE |
| password | VARCHAR(255) | NOT NULL |

## COMPANY

| Champ | Type | Contraintes |
|---|---|---|
| id | BIGINT | PK, auto-incrémenté |
| name | VARCHAR(255) | NOT NULL |
| activity | VARCHAR(255) | NOT NULL |

## APPLICATION

| Champ | Type | Contraintes |
|---|---|---|
| id | BIGINT | PK, auto-incrémenté |
| link | VARCHAR(500) | nullable (facultatif) |
| contact | VARCHAR(255) | nullable (facultatif) |
| job_title | VARCHAR(255) | NOT NULL |
| location | VARCHAR(255) | NOT NULL |
| salary | INTEGER | nullable (facultatif) |
| contract | VARCHAR(50) | NOT NULL |
| application_date | DATE | NOT NULL |
| application_re_submission_date | DATE | nullable |
| application_re_submission_date_2 | DATE | nullable |
| interview | BOOLEAN | NOT NULL, défaut `false` |
| refusal_reason | VARCHAR(500) | nullable (facultatif) |
| user_id | BIGINT | NOT NULL, FK → user(id) |
| company_id | BIGINT | NOT NULL, FK → company(id) |

## STATUS

| Champ | Type | Contraintes |
|---|---|---|
| id | BIGINT | PK, auto-incrémenté |
| state | VARCHAR(50) | NOT NULL |
| date | TIMESTAMP | NOT NULL |
| application_id | BIGINT | NOT NULL, FK → application(id) |

Décisions notables :
- `email` en `UNIQUE` : nécessaire pour pouvoir détecter et rejeter une inscription avec un email déjà utilisé (cahier des charges 2.1).
- `interview` avec une valeur par défaut `false` : une candidature démarre sans entretien obtenu.
- Tous les `id` en `BIGINT` (correspondra à `Long` côté Java) plutôt qu'`INT`/`Integer`, convention standard Spring Boot/Hibernate pour ne pas être limité en volume.
