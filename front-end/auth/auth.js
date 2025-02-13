// auth.js

export const login = async (email, password) => {
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
  }

  // 🔄 Redirection vers le menu
  window.location.href = data.redirect;
};

export const logout = async () => {
  sessionStorage.removeItem("jwtToken"); // 🛑 Supprime le token de la session

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
};
