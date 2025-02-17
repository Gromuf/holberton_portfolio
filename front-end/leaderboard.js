async function fetchLeaderboard(period) {
  try {
    const response = await fetch(
      `https://back-production-45d2.up.railway.app/leaderboard/${period}`
    );
    if (!response.ok)
      throw new Error("Erreur lors de la récupération du leaderboard");

    const leaderboard = await response.json();
    const tableBody = document.getElementById("leaderboard-body");
    tableBody.innerHTML = "";

    leaderboard.forEach((entry) => {
      const row = `
		  <tr>
			<td>${entry.rank}</td>
			<td>${entry.username}</td>
			<td>${entry.score}</td>
			<td>${entry.period}</td>
		  </tr>
		`;
      tableBody.innerHTML += row;
    });
    // Gestion des boutons actifs
    const buttons = document.querySelectorAll(".filter-buttons .btn");
    buttons.forEach((btn) => btn.classList.remove("active"));
    document
      .querySelector(`.btn[onclick="fetchLeaderboard('${period}')"]`)
      .classList.add("active");
  } catch (error) {
    console.error("Erreur:", error);
    alert("Impossible de charger le leaderboard.");
  }
}

// Charger par défaut le leaderboard quotidien
window.onload = () => fetchLeaderboard("daily");
