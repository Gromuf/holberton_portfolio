// auth.js

const API_BASE_URL = "https://back-production-45d2.up.railway.app";
export const login = async (email, password) => {
  try {
    const response = await fetch(`${API_BASE_URL}/auth/login`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ email, password }),
    });

    if (!response.ok) {
      const errorText = await response.text();
      throw new Error(`Login failed: ${errorText}`);
    }

    const data = await response.json();
    console.log("🟢 Login réussi:", data);

    if (data.token && data.playerId) {
      sessionStorage.setItem("jwtToken", data.token);
      sessionStorage.setItem("playerId", data.playerId);
      console.log("🔑 Token JWT et ID joueur stockés !");
    } else {
      console.error("❌ Token ou ID joueur manquant !");
    }

    if (data.redirect) {
      window.location.href = data.redirect;
    } else {
      alert("⚠️ Redirection manquante !");
    }

    return data;
  } catch (error) {
    console.error("🚨 Erreur lors de la connexion:", error);
    alert("🚨 Une erreur est survenue lors de la connexion.");
    throw error;
  }
};

export const logout = async () => {
  sessionStorage.removeItem("jwtToken"); // 🛑 Supprime le token de la session

  try {
    const response = await fetch(`${API_BASE_URL}/auth/logout`, {
      method: "POST",
      credentials: "include",
    });

    if (!response.ok) {
      const text = await response.text();
      throw new Error(`Logout failed: ${text}`);
    }

    console.log("🟢 Logout réussi.");
    window.location.href = "/scenes/login.html"; // ✅ Redirection après logout
    return response;
  } catch (error) {
    console.error("🚨 Erreur lors de la déconnexion:", error);
    alert("🚨 Une erreur est survenue lors de la déconnexion.");
    throw error;
  }
};
