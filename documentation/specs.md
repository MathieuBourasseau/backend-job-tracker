# Cahier des charges — Tracker de candidatures

**Projet personnel de portfolio — Développeur junior en recherche d'alternance**
**Stack cible :** Java / Spring Boot (back) + React/TypeScript (front) + PostgreSQL

---

## 1. Contexte et objectif

Application web permettant de centraliser et suivre ses candidatures de recherche d'emploi/alternance, en remplacement d'un suivi manuel sur Excel. L'objectif est double :
- **Fonctionnel** : disposer d'un outil réellement utilisé au quotidien
- **Portfolio** : démontrer une maîtrise de Java/Spring Boot sur un projet complet (auth, CRUD, relations de données, logique métier)

---

## 2. Fonctionnalités — Cœur de l'application (MVP)

### 2.1 Gestion de compte utilisateur
- L'utilisateur doit pouvoir **créer un compte** (email + mot de passe)
- L'utilisateur doit pouvoir **se connecter** à son compte
- L'utilisateur doit pouvoir **se déconnecter**
- Le système doit **gérer les erreurs courantes** : email déjà utilisé à l'inscription, identifiants invalides à la connexion
- Chaque utilisateur ne doit avoir accès **qu'à ses propres candidatures**

### 2.2 Gestion des candidatures (CRUD)
- L'utilisateur doit pouvoir **créer** une nouvelle candidature avec les champs suivants :
  - Entreprise
  - Lien vers l'offre (facultatif)
  - Contact (nom du recruteur/interlocuteur, facultatif)
  - Nom du poste
  - Lieu
  - Salaire (facultatif)
  - Type de contrat (CDI, CDD, alternance, stage...)
  - Secteur d'activité
  - Date de candidature (envoi initial)
  - État (voir 2.3)
  - Date(s) de relance — **2 relances maximum** après l'envoi initial
  - Entretien obtenu (oui/non)
  - Raison de refus (facultatif, pertinent uniquement si l'état est "refus")
- L'utilisateur doit pouvoir **consulter** la liste de ses candidatures
- L'utilisateur doit pouvoir **modifier** une candidature existante (informations et statut)
- L'utilisateur doit pouvoir **supprimer** une candidature

### 2.3 Statuts et repérage visuel
L'application doit permettre de repérer facilement l'état de chaque candidature via un code couleur :

| Statut | Couleur |
|---|---|
| À faire | Fond blanc |
| En cours | Fond jaune |
| À relancer | Fond orange |
| Refus / absence de réponse | Fond rouge |

### 2.4 Historique des statuts
- Chaque changement de statut doit être **horodaté et conservé** (historique consultable), pas seulement le statut courant
- Permet de visualiser le parcours complet d'une candidature (envoyée le X, relancée le Y, entretien le Z...)

### 2.5 Relances automatiques
- L'application doit **signaler automatiquement** qu'une relance est à effectuer lorsque **7 jours se sont écoulés sans réponse**
- Cette signalisation ne s'applique **pas** aux candidatures déjà classées en "refus" ou "absence de réponse définitive"
- Calcul **dynamique** (à l'affichage, comparaison date du jour / date de dernière action), pas de job planifié pour le MVP

---

## 3. Fonctionnalités — Évolutions (V2, si le temps le permet)

| Fonctionnalité | Description | Complexité |
|---|---|---|
| Dashboard de statistiques | Taux de réponse, délai moyen, répartition par statut (Recharts) | Moyenne |
| Recherche / filtres | Par entreprise, statut, plage de dates | Faible |
| Tri | Par date de candidature, par date de dernière relance | Faible |
| Export | CSV / PDF des candidatures | Moyenne |
| Import Excel | Migration des données depuis le fichier Excel existant | Moyenne |
| Notification email | Envoi d'un email de relance à J+7 (au lieu d'un simple badge) | Élevée (queue/scheduler) |
| Carte des entreprises | Géolocalisation + temps de trajet estimé | Moyenne |

---

## 4. Exigences techniques

### 4.1 Back-end
- Java / Spring Boot
- Spring Data JPA + PostgreSQL
- Spring Security + JWT pour l'authentification
- Architecture en couches : Controller / Service / Repository / DTO
- Validation des données (Bean Validation)
- Gestion centralisée des erreurs (`@ExceptionHandler`)
- Tests unitaires (JUnit + Mockito) et tests d'intégration

### 4.2 Front-end
- React + TypeScript
- Tailwind CSS
- Consommation de l'API REST du back
- Gestion de l'état de connexion (token JWT stocké côté client)

### 4.3 Déploiement
- Back : Render
- Front : Vercel
- Base de données : PostgreSQL hébergée (Render ou équivalent)

---

## 5. Modèle de données (aperçu — à détailler ensuite)

- **User** : id, email, mot de passe (hashé)
- **Company** : id, nom, secteur_activite
- **Application** : id, user_id, company_id, poste, lieu, salaire, type_contrat, lien_offre, contact, date_candidature, statut, date_relance_1, date_relance_2, entretien_obtenu, raison_refus
- **StatusHistory** *(à confirmer — voir échange en cours)* : id, application_id, statut, date_changement

---

## 6. Hors périmètre (explicitement exclu du MVP)

- Notifications email
- Gestion multi-utilisateurs collaborative (partage de candidatures entre utilisateurs)
- Application mobile native
- Job planifié / cron pour les relances (calcul dynamique suffisant pour le MVP)

---

## 7. Prochaines étapes

1. Modélisation détaillée des entités JPA et de leurs relations
2. Définition des endpoints de l'API REST
3. Développement back (modèle → auth → CRUD)
4. Développement front en parallèle décalé
5. Tests
6. Déploiement