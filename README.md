# 🐍 Snake Game Backend & Frontend

Bienvenue dans le projet **Snake Game** ! Ce projet est composé d’un **backend** en **Java (Spring Boot)** et d’un **frontend** en **JavaScript avec Phaser**. L'objectif est de fournir une expérience de jeu fluide avec un système de leaderboard et des scores persistants.

---

## 📚 Contexte du projet

Ce projet a été réalisé dans le cadre d'un **projet étudiant** visant à développer une application web interactive intégrant un backend robuste et un frontend dynamique. Il s'agit d'un exercice de mise en pratique des compétences acquises en **développement full-stack**, en particulier sur les technologies **Spring Boot**, **PostgreSQL**, et **Phaser 3**.

---

## 📁 Structure du projet

```
/holberton_portfolio
│── snake-game-backend/          # Code source du backend (Spring Boot, PostgreSQL)
│   ├── src/                     # Code source Java
│   ├── pom.xml                  # Fichier de configuration Maven
│   ├── application.properties    # Configuration du backend
│   ├── README.md                 # Instructions spécifiques au backend
│   └── requirements.txt          # Instructions d'installation du backend
│── front-end/                    # Code source du frontend (JavaScript, Phaser)
│   ├── src/                      # Code source JS
│   ├── package.json              # Dépendances Node.js
│   ├── README.md                 # Instructions spécifiques au frontend
│   └── requirements.txt          # Instructions d'installation du frontend
│── README.md                     # Ce fichier
```

---

## 🚀 Installation et exécution

Toutes les informations nécessaires pour **installer et exécuter** le projet en local sont disponibles dans les fichiers **`requirements.txt`** de chaque partie du projet :

- **Backend** : Voir le fichier [`backend/requirements.txt`](backend/requirements.txt)
- **Frontend** : Voir le fichier [`frontend/requirements.txt`](frontend/requirements.txt)

### ⚠️ Remarque importante pour le backend

Le **backend n'est pas exécutable en local** sans **modifications** des fichiers de configuration.  
Pour l’adapter à un environnement local, vous devrez :

- Modifier **`application.properties`** (`src/main/resources/application.properties`) pour configurer la base de données locale.
- Vérifier la configuration dans **`Config.java`** (si des variables d’environnement sont utilisées).
- Adapter **`pom.xml`** (si des dépendances spécifiques sont requises en local).

Si le backend est hébergé sur **Railway**, il faudra configurer les accès à la base de données distante.

Le **frontend**, en revanche, peut être exécuté en local **sans modification majeure** en suivant les instructions de **`frontend/requirements.txt`**.

---

## 📍 API Backend

Le backend expose une API REST accessible via `http://localhost:8080` (si exécuté en local) ou via l’URL de production.

### **Endpoints disponibles :**

| Méthode | Endpoint                    | Description                              |
| ------- | --------------------------- | ---------------------------------------- |
| `GET`   | `/players`                  | Récupère tous les joueurs                |
| `POST`  | `/players`                  | Crée un nouveau joueur                   |
| `GET`   | `/scores/player/{playerId}` | Récupère les scores d'un joueur          |
| `POST`  | `/scores`                   | Enregistre un score pour un joueur       |
| `GET`   | `/leaderboard/{period}`     | Récupère le classement selon une période |

Exemple de requête pour récupérer le classement des scores :

```bash
curl -X GET http://localhost:8080/leaderboard/daily
```

Si une authentification est requise, assurez-vous d'ajouter un token dans l'en-tête de la requête.

---

## 🎮 Exécution du Frontend

Le frontend est développé avec **Phaser** et peut être exécuté en local avec **Node.js**.

### **Installation et démarrage**

1. **Installer les dépendances** :

   ```bash
   cd frontend
   npm install
   ```

2. **Lancer le serveur de développement** :

   ```bash
   npm start
   ```

3. **Accéder au jeu** via le navigateur :

   ```
   http://localhost:3000
   ```

   _(Le port peut varier selon la configuration.)_

---

## 🛠 Outils et technologies utilisées

### **Backend**

- **Java 17+**
- **Spring Boot**
- **PostgreSQL** (ou H2 en local)
- **Maven**

### **Frontend**

- **JavaScript**
- **Phaser 3**

---

## 🏆 Fonctionnalités principales

- 🏅 **Système de leaderboard** : Suivi des scores des joueurs (journalier, hebdomadaire, mensuel, all-time).
- 👤 **Authentification des joueurs** : Création de compte et connexion.
- 🎮 **Gameplay interactif** : Contrôlez un serpent qui grandit en mangeant des fruits.
- 🏁 **Gestion des parties** : Calcul du score et soumission automatique.
- 🎨 **Personnalisation** : Thèmes et couleurs modifiables.

---

## 👨‍👩‍👦 Auteur

- **Nom :** Louis Beaumois
- **GitHub :** https://github.com/Gromuf
- **Projet réalisé dans le cadre d'une formation étudiante**

---
