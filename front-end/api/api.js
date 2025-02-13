// api/api.js
const API_BASE_URL = "http://localhost:8080";

export const api = {
  get: async (url) => {
    const response = await fetch(`${API_BASE_URL}${url}`, {
      method: "GET",
      credentials: "include",
    });
    return response.json();
  },

  post: async (url, body) => {
    const response = await fetch(`${API_BASE_URL}${url}`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      credentials: "include",
      body: JSON.stringify(body),
    });

    // ✅ Gérer les réponses vides sans planter
    if (!response.ok) {
      throw new Error(`Erreur API: ${response.status}`);
    }

    return await response.json(); // 🔍 Toujours attendre du JSON pour pouvoir lire le `redirect`
  },
};
