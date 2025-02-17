const API_BASE_URL = "back-production-45d2.up.railway.app";

export const api = {
  get: async (url) => {
    const token = sessionStorage.getItem("jwtToken"); // 📌 Récupère le token
    const response = await fetch(`${API_BASE_URL}${url}`, {
      method: "GET",
      headers: {
        Authorization: `Bearer ${token}`, // 🔑 Envoie le token dans l’Authorization
        "Content-Type": "application/json",
      },
      credentials: "include",
    });

    return response.json();
  },

  post: async (url, body) => {
    const token = sessionStorage.getItem("jwtToken"); // 📌 Récupère le token
    const response = await fetch(`${API_BASE_URL}${url}`, {
      method: "POST",
      headers: {
        Authorization: `Bearer ${token}`, // 🔑 Ajoute le token
        "Content-Type": "application/json",
      },
      credentials: "include",
      body: JSON.stringify(body),
    });

    if (!response.ok) {
      throw new Error(`Erreur API: ${response.status}`);
    }

    return await response.json();
  },
};
