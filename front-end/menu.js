import { logout } from "../auth/auth.js";

// 🎯 Attacher les événements après le chargement du DOM
document.addEventListener("DOMContentLoaded", () => {
  const playButton = document.getElementById("playBtn");
  const mobileWarning = document.querySelector(".mobile-warning");

  // Détection du mobile ou tablette
  if (/Mobi|Android|iPhone|iPad/i.test(navigator.userAgent)) {
    playButton.style.display = "none"; // Cache le bouton
    mobileWarning.style.display = "block"; // Affiche le message
  }

  // 🕹️ Jouer
  playButton?.addEventListener("click", () => {
    window.location.href = "/scenes/game.html";
  });

  // ⚙️ Paramètres
  document.getElementById("settingsBtn").addEventListener("click", () => {
    window.location.href = "/scenes/settings.html";
  });

  // 🏆 Leaderboard
  document.getElementById("leaderboardBtn").addEventListener("click", () => {
    window.location.href = "/scenes/leaderboard.html";
  });

  // 📝 Voir les Avis
  document.getElementById("reviewsBtn").addEventListener("click", () => {
    window.location.href = "/scenes/reviews.html";
  });

  // 🚪 Déconnexion
  document.getElementById("logoutBtn").addEventListener("click", async () => {
    try {
      await logout();
      console.log("🟢 Déconnexion réussie");
    } catch (error) {
      console.error("🚨 Erreur déconnexion:", error);
      alert("🚨 Une erreur est survenue lors de la déconnexion.");
    }
  });
});
