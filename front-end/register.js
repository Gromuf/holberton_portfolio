document
  .getElementById("registerForm")
  .addEventListener("submit", async (e) => {
    e.preventDefault();

    // Récupérer les valeurs des champs
    const username = document.getElementById("username").value.trim();
    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value;
    const confirmPassword = document.getElementById("confirmPassword").value;

    // Validation simple côté client
    if (password !== confirmPassword) {
      alert("❌ Les mots de passe ne correspondent pas.");
      return;
    }

    // Préparation des données
    const userData = {
      username,
      email,
      password,
    };

    try {
      // Envoi au backend
      const response = await fetch(
        "https://back-production-45d2.up.railway.app/players",
        {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(userData),
        }
      );

      if (response.ok) {
        alert("✅ Inscription réussie ! Redirection vers la connexion...");
        window.location.href = "login.html";
      } else if (response.status === 409) {
        alert("⚠️ Nom d'utilisateur ou email déjà utilisé.");
      } else {
        alert("❌ Erreur lors de l'inscription. Veuillez réessayer.");
      }
    } catch (error) {
      console.error("Erreur lors de l'inscription:", error);
      alert("🚨 Une erreur est survenue. Veuillez réessayer.");
    }
  });
