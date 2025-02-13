import { api } from "../api/api.js";

// Connexion
export const login = async (email, password) => {
  return api.post("/auth/login", { email, password });
};

// Déconnexion
export const logout = async () => {
  return api.post("/auth/logout", {});
};
