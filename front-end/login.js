// login.js
import { login } from "./auth/auth.js";

// 🎯 Gestion du formulaire de connexion
document.getElementById("loginForm").addEventListener("submit", async (e) => {
  e.preventDefault();

  // 🧠 Récupérer les valeurs des champs
  const email = document.getElementById("email").value.trim();
  const password = document.getElementById("password").value;

  // 🚨 Validation des champs
  if (!email || !password) {
    alert("⚠️ Veuillez remplir tous les champs.");
    return;
  }

  // 🌐 Connexion via le service `auth.js`
  login(email, password)
    .then((response) => {
      console.log("🟢 Connexion réussie:", response);
      if (response.redirect) {
        window.location.href = response.redirect; // ✅ Redirection directe
      } else {
        alert(`❌ Erreur : ${response.message || "Identifiants incorrects"}`);
      }
    })
    .catch((error) => {
      console.error("🚨 Erreur lors de la connexion:", error);
      alert("🚨 Une erreur réseau est survenue.");
    });
});
