// menu.js
import { logout } from "../auth/auth.js";

// 🎯 Attacher les événements après le chargement du DOM
document.addEventListener("DOMContentLoaded", () => {
  // 🕹️ Jouer
  document.getElementById("playBtn").addEventListener("click", () => {
    window.location.href = "/game/start";
  });

  // ⚙️ Paramètres
  document.getElementById("settingsBtn").addEventListener("click", () => {
    window.location.href = "/scenes/settings.html";
  });

  // 🏆 Leaderboard
  document.getElementById("leaderboardBtn").addEventListener("click", () => {
    window.location.href = "/scenes/leaderboard.html";
  });

  // 🚪 Déconnexion
  document.getElementById("logoutBtn").addEventListener("click", async () => {
    try {
      await logout(); // Le logout redirige déjà vers /scenes/login.html
      console.log("🟢 Déconnexion réussie");
    } catch (error) {
      console.error("🚨 Erreur déconnexion:", error);
      alert("🚨 Une erreur est survenue lors de la déconnexion.");
    }
  });
});
