// Récupération du token et de l'ID joueur depuis le sessionStorage
const token = sessionStorage.getItem("jwtToken");
const playerId = sessionStorage.getItem("playerId");

if (!token || !playerId) {
  alert("Utilisateur non authentifié. Veuillez vous connecter.");
  window.location.href = "/scenes/login.html"; // Rediriger vers la page de login si pas de token
}

document.addEventListener("DOMContentLoaded", async () => {
  try {
    const response = await fetch(`http://localhost:8080/settings/${playerId}`, {
      headers: {
        Authorization: `Bearer ${token}`, // Envoi du token JWT
      },
    });
    if (!response.ok) throw new Error("Failed to load settings");

    const settings = await response.json();
    document.getElementById("backgroundTheme").value = settings.backgroundTheme;
    document.getElementById("snakeColor").value = settings.snakeColor;
  } catch (error) {
    console.error("Error:", error);
    alert("Impossible de charger les paramètres.");
  }
});

document
  .getElementById("settings-form")
  .addEventListener("submit", async (e) => {
    e.preventDefault();

    const backgroundTheme = document.getElementById("backgroundTheme").value;
    const snakeColor = document.getElementById("snakeColor").value;

    try {
      const response = await fetch(
        `http://localhost:8080/settings/${playerId}`,
        {
          method: "PUT",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`, // Envoi du token JWT
          },
          body: JSON.stringify({ backgroundTheme, snakeColor }),
        }
      );

      if (!response.ok) throw new Error("Failed to save settings");

      alert("Paramètres sauvegardés avec succès !");
    } catch (error) {
      console.error("Error:", error);
      alert("Impossible de sauvegarder les paramètres.");
    }
  });
