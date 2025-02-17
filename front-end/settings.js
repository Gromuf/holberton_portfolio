const playerId = localStorage.getItem("playerId") || 1; // Remplacer 1 par l'ID dynamique

document.addEventListener("DOMContentLoaded", async () => {
  try {
    const response = await fetch(`http://localhost:8080/settings/${playerId}`);
    if (!response.ok) throw new Error("Failed to load settings");

    const settings = await response.json();
    document.getElementById("backgroundTheme").value = settings.backgroundTheme;
    document.getElementById("snakeColor").value = settings.snakeColor;
  } catch (error) {
    console.error("Error:", error);
    alert("Failed to load player settings.");
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
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({ backgroundTheme, snakeColor }),
        }
      );

      if (!response.ok) throw new Error("Failed to save settings");

      alert("Settings saved successfully!");
    } catch (error) {
      console.error("Error:", error);
      alert("Failed to save settings.");
    }
  });
