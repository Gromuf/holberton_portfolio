# 🐍 Snake Game Backend & Frontend

Bienvenue dans le projet **Snake Game** ! Ce projet est composé d’un **backend** en **Java (Spring Boot)** et d’un **frontend** en **JavaScript avec Phaser**.

## 💁️ Structure du projet

```
/holberton_portfolio
│── snake-game-backend/          # Code source du backend (Spring Boot, PostgreSQL)
│   ├── src/          # Code source Java
│   ├── pom.xml       # Fichier de configuration Maven
│   └── requirements.txt # Instructions pour l'installation du backend
│── front-end/         # Code source du frontend (JavaScript, Phaser)
│   ├── src/          # Code source JS
│   ├── package.json  # Dépendances Node.js
│   └── requirements.txt # Instructions pour l'installation du frontend
│── README.md         # Ce fichier
```

---

## 🚀 Installation et exécution

Toutes les informations nécessaires pour **installer et exécuter** le projet en local sont disponibles dans les fichiers **`requirements.txt`** de chaque partie du projet :

- 📺 **Backend** : Voir le fichier [`backend/requirements.txt`](backend/requirements.txt)
- 📺 **Frontend** : Voir le fichier [`frontend/requirements.txt`](frontend/requirements.txt)

### ⚠️ Remarque importante pour le backend

Le **backend n'est pas exécutable en local** sans **modifications** des fichiers de configuration.\
Pour l’adapter à un environnement local, vous devrez :

- Modifier **`application.properties`** (`src/main/resources/application.properties`) pour configurer la base de données locale.
- Vérifier la configuration dans **`Config.java`** (si des variables d’environnement sont utilisées).
- Adapter **`pom.xml`** (si des dépendances spécifiques sont requises en local).

Si le backend est hébergé sur **Railway**, il faudra configurer les accès à la base de données distante.

Le **frontend**, en revanche, peut être exécuté en local **sans modification majeure** en suivant les instructions de **`frontend/requirements.txt`**.

---

## 💽 API Backend

Le backend expose une API REST accessible via `http://localhost:8080` (si exécuté en local) ou via l’URL de production.

Exemples d’endpoints disponibles :

| Méthode | Endpoint                    | Description                              |
| ------- | --------------------------- | ---------------------------------------- |
| `GET`   | `/players`                  | Récupère tous les joueurs                |
| `POST`  | `/players`                  | Crée un nouveau joueur                   |
| `GET`   | `/scores/player/{playerId}` | Récupère les scores d'un joueur          |
| `GET`   | `/leaderboard/{period}`     | Récupère le classement selon une période |

---

## 🎮 Exécution du Frontend

Le frontend est développé avec **Phaser** et peut être exécuté en local avec **Node.js**.

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
   *(Le port peut varier selon la configuration.)*

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
- **Node.js 18+**

---