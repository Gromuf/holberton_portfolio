// 🎯 Gestion du formulaire de connexion
document.getElementById("loginForm").addEventListener("submit", async (e) => {
  e.preventDefault();

  // 🧠 Récupérer les valeurs des champs
  const email = document.getElementById("email").value.trim();
  const password = document.getElementById("password").value;

  // 🚨 Validation
  if (!email || !password) {
    alert("⚠️ Veuillez remplir tous les champs.");
    return;
  }

  // 📦 Préparer les données à envoyer au backend
  const loginData = { email, password };

  try {
    // 🌐 Envoi de la requête avec l'email et le mot de passe
    const response = await fetch("http://localhost:8080/auth/login", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(loginData),
      credentials: "include", // 🍪 Permet d'inclure automatiquement les cookies JWT
    });

    // 🔍 Traitement de la réponse
    if (response.ok) {
      const responseData = await response.json();
      alert(responseData.message);

      // ✅ Redirection vers la page de jeu après connexion réussie
      window.location.href = "game.html";
    } else {
      const errorMsg = await response.text();
      alert(`❌ Erreur : ${errorMsg}`);
    }
  } catch (error) {
    console.error("🚨 Erreur lors de la connexion:", error);
    alert("🚨 Une erreur réseau est survenue.");
  }
});
