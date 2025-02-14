// Récupérer le score et l'ID joueur
const score = sessionStorage.getItem("lastScore");
const playerId = sessionStorage.getItem("playerId");

// Afficher le score
document.getElementById("scoreDisplay").innerText = `Votre score : ${score}`;

// Envoyer le score au backend
fetch("http://localhost:8080/scores", {
  method: "POST",
  headers: {
    "Content-Type": "application/json",
    Authorization: `Bearer ${sessionStorage.getItem("jwtToken")}`,
  },
  body: JSON.stringify({
    playerId: parseInt(playerId),
    value: parseInt(score),
  }),
})
  .then((response) => {
    if (!response.ok) {
      throw new Error(`Erreur HTTP: ${response.status}`);
    }
    console.log("✅ Score enregistré avec succès");
  })
  .catch((error) => {
    console.error("🚨 Erreur lors de l’envoi du score:", error);
  });

// Gestion des boutons
document.getElementById("replayBtn").addEventListener("click", () => {
  window.location.href = "/scenes/game.html";
});

document.getElementById("menuBtn").addEventListener("click", () => {
  window.location.href = "/scenes/menu.html";
});
