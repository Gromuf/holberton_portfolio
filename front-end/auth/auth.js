// auth.js

export const login = async (email, password) => {
  try {
    const response = await fetch("http://localhost:8080/auth/login", {
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

    if (data.token) {
      // 📌 Stocker le token en sessionStorage
      sessionStorage.setItem("jwtToken", data.token);
      console.log("🔑 Token JWT stocké en session:", data.token);
    } else {
      console.error("❌ Pas de token reçu !");
      alert("❌ Authentification échouée : aucun token reçu.");
      return;
    }

    // ✅ Vérifier que `redirect` est défini avant d’y accéder
    if (data.redirect) {
      window.location.href = data.redirect;
      return data;
    } else {
      console.error("❌ Pas de redirection définie !");
      alert("❌ Authentification réussie mais aucune redirection définie.");
      return data;
    }
  } catch (error) {
    console.error("🚨 Erreur lors de la connexion:", error);
    alert("🚨 Une erreur est survenue lors de la connexion.");
    throw error;
  }
};

export const logout = async () => {
  sessionStorage.removeItem("jwtToken"); // 🛑 Supprime le token de la session

  try {
    const response = await fetch("http://localhost:8080/auth/logout", {
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
