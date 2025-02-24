document.addEventListener("DOMContentLoaded", function () {
  loadReviews(); // Charger les avis au chargement de la page

  document.getElementById("menuBtn").addEventListener("click", function () {
    window.location.href = "/scenes/menu.html";
  });
});

function loadReviews() {
  fetch("https://back-production-45d2.up.railway.app/reviews/with-players", {
    method: "GET",
    headers: {
      Authorization: `Bearer ${sessionStorage.getItem("jwtToken")}`,
    },
  })
    .then((response) => response.json())
    .then((reviews) => {
      const container = document.getElementById("reviewsList");
      container.innerHTML = ""; // Efface le contenu précédent

      reviews.forEach((review) => {
        const card = document.createElement("div");
        card.className = "review-card";
        card.innerHTML = `
                <h3>${review.playerName}</h3>
                <p>${review.text}</p>
                <span class="review-score">Score: ${review.score}</span>
            `;
        container.appendChild(card);
      });
    })
    .catch((error) =>
      console.error("🚨 Erreur lors du chargement des avis :", error)
    );
}

// Rafraîchir les avis toutes les 5 secondes
setInterval(loadReviews, 5000);
