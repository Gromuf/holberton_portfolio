document.addEventListener("DOMContentLoaded", function () {
  const urlParams = new URLSearchParams(window.location.search);
  const score = urlParams.get("score") || 0;
  document.getElementById("scoreDisplay").textContent = score;
  sessionStorage.setItem("lastScore", score);
});

// Envoi de la review
document
  .getElementById("submitReviewBtn")
  .addEventListener("click", function () {
    const playerId = sessionStorage.getItem("playerId");
    const score = sessionStorage.getItem("lastScore");
    const text = document.getElementById("reviewText").value;
    const token = sessionStorage.getItem("jwtToken"); // Récupération du token JWT

    if (!text.trim()) {
      alert("Veuillez entrer un avis !");
      return;
    }

    fetch("https://back-production-45d2.up.railway.app/reviews", {
      method: "POST",
      headers: {
        "Content-Type": "application/x-www-form-urlencoded",
        Authorization: `Bearer ${token}`, // Ajout du JWT Token
      },
      body: `playerId=${playerId}&text=${encodeURIComponent(
        text
      )}&score=${score}`,
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error(`Erreur HTTP: ${response.status}`);
        }
        return response.json();
      })
      .then((data) => {
        alert("Avis envoyé avec succès !");
        window.location.href = "/scenes/menu.html";
      })
      .catch((error) =>
        console.error("🚨 Erreur lors de l'envoi de l'avis :", error)
      );
  });

// Retour au menu
document.getElementById("menuBtn").addEventListener("click", function () {
  window.location.href = "/scenes/menu.html";
});
