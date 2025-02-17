const token = sessionStorage.getItem("jwtToken");
const playerId = sessionStorage.getItem("playerId");

if (!token || !playerId) {
  alert("Utilisateur non authentifié. Veuillez vous connecter.");
  window.location.href = "/scenes/login.html";
}

const backgroundColors = [
  "#1a1a1a",
  "#f0f0f0",
  "#ad5745",
  "#3b5998",
  "#00ffc3",
];
const snakeColors = ["#00ffc3", "#ff0000", "#8219ca", "#3498db", "#2ecc71"];

function createColorPicker(containerId, colors, inputId) {
  const container = document.getElementById(containerId);
  colors.forEach((color) => {
    const colorSquare = document.createElement("div");
    colorSquare.classList.add("color-square");
    colorSquare.style.backgroundColor = color;
    colorSquare.addEventListener("click", () => {
      document.getElementById(inputId).value = color;
      container
        .querySelectorAll(".color-square")
        .forEach((sq) => sq.classList.remove("selected"));
      colorSquare.classList.add("selected");
    });
    container.appendChild(colorSquare);
  });
}

async function fetchSettings() {
  try {
    const response = await fetch(
      `https://back-production-45d2.up.railway.app/settings/${playerId}`,
      {
        headers: { Authorization: `Bearer ${token}` },
      }
    );
    if (!response.ok) throw new Error("Failed to load settings");
    const settings = await response.json();

    document.getElementById("backgroundTheme").value = settings.backgroundTheme;
    document.getElementById("snakeColor").value = settings.snakeColor;

    document
      .querySelector(
        `#backgroundPicker .color-square[style="background-color: ${settings.backgroundTheme};"]`
      )
      ?.classList.add("selected");
    document
      .querySelector(
        `#snakeColorPicker .color-square[style="background-color: ${settings.snakeColor};"]`
      )
      ?.classList.add("selected");
  } catch (error) {
    console.error("Error:", error);
    alert("Impossible de charger les paramètres.");
  }
}

document.addEventListener("DOMContentLoaded", () => {
  createColorPicker("backgroundPicker", backgroundColors, "backgroundTheme");
  createColorPicker("snakeColorPicker", snakeColors, "snakeColor");
  fetchSettings();
});

document
  .getElementById("settings-form")
  .addEventListener("submit", async (e) => {
    e.preventDefault();
    const backgroundTheme = document.getElementById("backgroundTheme").value;
    const snakeColor = document.getElementById("snakeColor").value;

    try {
      const response = await fetch(
        `https://back-production-45d2.up.railway.app/settings/${playerId}`,
        {
          method: "PUT",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
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
