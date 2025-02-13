import { api } from "../api/api.js";

// Connexion
// auth.js
export const login = async (email, password) => {
  const response = await fetch("http://localhost:8080/auth/login", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    credentials: "include",
    body: JSON.stringify({ email, password }),
  });

  if (!response.ok) {
    const errorText = await response.text();
    throw new Error(`Login failed: ${errorText}`);
  }

  const data = await response.json();
  console.log("🟢 Login réussi:", data);
  return data;
};

// Déconnexion
export const logout = async () => {
  // Requête POST sans body
  const response = await fetch("http://localhost:8080/auth/logout", {
    method: "POST",
    credentials: "include",
  });

  if (!response.ok) {
    const text = await response.text();
    throw new Error(`Logout failed: ${text}`);
  }

  const data = await response.text();
  console.log("🟢 Logout réussi:", data);
  return { message: data, redirect: "/login.html" };
};
