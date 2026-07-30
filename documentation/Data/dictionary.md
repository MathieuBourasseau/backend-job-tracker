# Dictionnaire de données — Tracker de candidatures

Description métier de chaque champ des 4 entités, en complément du MPD (types/contraintes techniques).

## USER

| Champ | Type | Contraintes | Description |
|---|---|---|---|
| id | BIGINT | PK, auto-incrémenté | Identifiant technique unique de l'utilisateur. |
| email | VARCHAR(255) | NOT NULL, UNIQUE | Adresse email de connexion, sert aussi d'identifiant fonctionnel unique du compte. |
| password | VARCHAR(255) | NOT NULL | Mot de passe de l'utilisateur, stocké haché (jamais en clair). |

## COMPANY

| Champ | Type | Contraintes | Description |
|---|---|---|---|
| id | BIGINT | PK, auto-incrémenté | Identifiant technique unique de l'entreprise. |
| name | VARCHAR(255) | NOT NULL | Nom de l'entreprise. |
| activity | VARCHAR(255) | NOT NULL | Secteur d'activité de l'entreprise. |

## APPLICATION

| Champ | Type | Contraintes | Description |
|---|---|---|---|
| id | BIGINT | PK, auto-incrémenté | Identifiant technique unique de la candidature. |
| link | VARCHAR(500) | nullable | Lien vers l'offre d'emploi d'origine. |
| contact | VARCHAR(255) | nullable | Nom du recruteur ou de l'interlocuteur pour cette candidature. |
| job_title | VARCHAR(255) | NOT NULL | Intitulé du poste visé. |
| location | VARCHAR(255) | NOT NULL | Lieu du poste (peut différer du siège de l'entreprise selon l'offre). |
| salary | INTEGER | nullable | Salaire proposé ou visé, si connu/communiqué. |
| contract | VARCHAR(50) | NOT NULL | Type de contrat (CDI, CDD, alternance, stage...). |
| application_date | DATE | NOT NULL | Date d'envoi initial de la candidature. |
| application_re_submission_date | DATE | nullable | Date de la première relance effectuée. |
| application_re_submission_date_2 | DATE | nullable | Date de la deuxième relance effectuée (maximum 2 relances). |
| interview | BOOLEAN | NOT NULL, défaut `false` | Indique si un entretien a été obtenu pour cette candidature. |
| refusal_reason | VARCHAR(500) | nullable | Raison du refus, pertinente uniquement si le dernier statut est "refus". |
| user_id | BIGINT | NOT NULL, FK → user(id) | Utilisateur propriétaire de la candidature (isolation des données par utilisateur). |
| company_id | BIGINT | NOT NULL, FK → company(id) | Entreprise à laquelle la candidature est adressée. |

## STATUS

| Champ | Type | Contraintes | Description |
|---|---|---|---|
| id | BIGINT | PK, auto-incrémenté | Identifiant technique unique de l'entrée de statut. |
| state | VARCHAR(50) | NOT NULL | Valeur de l'état à cet instant (à faire / en cours / à relancer / refus). |
| date | TIMESTAMP | NOT NULL | Date et heure exactes du changement de statut (horodatage, permet plusieurs changements le même jour). |
| application_id | BIGINT | NOT NULL, FK → application(id) | Candidature à laquelle appartient cette entrée d'historique. |
