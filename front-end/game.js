async function fetchSettings() {
  const token = sessionStorage.getItem("jwtToken");
  const playerId = sessionStorage.getItem("playerId");

  if (token && playerId) {
    try {
      const response = await fetch(
        `https://back-production-45d2.up.railway.app/settings/${playerId}`,
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      );
      if (!response.ok) throw new Error("Impossible de charger les paramètres");

      const settings = await response.json();
      return {
        backgroundColor: settings.backgroundTheme || "#1a1a1a",
        snakeColor:
          parseInt(settings.snakeColor.replace("#", "0x")) || 0x00ffc3,
      };
    } catch (error) {
      console.error("Erreur lors de la récupération des paramètres:", error);
    }
  }
  return { backgroundColor: "#1a1a1a", snakeColor: 0x00ffc3 };
}

fetchSettings().then((settings) => {
  class SnakeGame extends Phaser.Scene {
    constructor() {
      super("SnakeGame");
      this.backgroundColor = settings.backgroundColor;
      this.snakeColor = settings.snakeColor;
    }

    create() {
      // Vérifie que Phaser est bien attaché à l'élément #game
      console.log("Vérification de #game:", document.getElementById("game"));

      this.cameras.main.setBackgroundColor(this.backgroundColor);
      this.score = 0;
      this.speed = 200;
      this.lastMoveTime = 0;
      this.direction = "RIGHT";
      this.snakePositions = [{ x: 160, y: 160 }];

      // Ajoute un texte pour le score
      this.scoreText = this.add.text(10, 10, "Score: 0", {
        fontFamily: "Press Start 2P",
        fontSize: "16px",
        fill: this.backgroundColor === "#1a1a1a" ? "#00ffc3" : "#000",
      });

      // Crée le serpent et la nourriture
      this.snake = this.add.group();
      this.drawSnake();
      this.food = this.add.rectangle(300, 300, 20, 20, 0xff0000).setOrigin(0);
      this.placeFood();

      // Écoute les touches du clavier
      this.input.keyboard.on("keydown", (event) => {
        if (event.key === "ArrowUp" && this.direction !== "DOWN")
          this.direction = "UP";
        else if (event.key === "ArrowDown" && this.direction !== "UP")
          this.direction = "DOWN";
        else if (event.key === "ArrowLeft" && this.direction !== "RIGHT")
          this.direction = "LEFT";
        else if (event.key === "ArrowRight" && this.direction !== "LEFT")
          this.direction = "RIGHT";
      });
    }

    update(time) {
      if (time >= this.lastMoveTime + this.speed) {
        this.lastMoveTime = time;
        this.moveSnake();
      }
    }

    drawSnake() {
      this.snake.clear(true, true);
      this.snakePositions.forEach((pos) => {
        const rect = this.add
          .rectangle(pos.x + 2, pos.y + 2, 16, 16, this.snakeColor) // Ajoute un léger espace
          .setOrigin(0)
          .setStrokeStyle(2, 0x000000);
        this.snake.add(rect);
      });
    }

    moveSnake() {
      const head = this.snakePositions[0];
      let newHead;

      switch (this.direction) {
        case "UP":
          newHead = { x: head.x, y: head.y - 20 };
          break;
        case "DOWN":
          newHead = { x: head.x, y: head.y + 20 };
          break;
        case "LEFT":
          newHead = { x: head.x - 20, y: head.y };
          break;
        case "RIGHT":
          newHead = { x: head.x + 20, y: head.y };
          break;
      }

      // Vérifie si le serpent sort de la zone de jeu
      if (
        newHead.x < 0 ||
        newHead.x >= 800 ||
        newHead.y < 0 ||
        newHead.y >= 600 ||
        this.snakePositions.some(
          (pos, index) =>
            index !== 0 && pos.x === newHead.x && pos.y === newHead.y
        )
      ) {
        this.gameOver();
        return;
      }

      // Ajoute la nouvelle tête
      this.snakePositions.unshift(newHead);

      // Vérifie si le serpent mange la nourriture
      if (newHead.x === this.food.x && newHead.y === this.food.y) {
        this.score += 10;
        this.scoreText.setText(`Score: ${this.score}`);
        this.speed = Math.max(50, this.speed - 10);
        this.placeFood();
      } else {
        this.snakePositions.pop();
      }

      this.drawSnake();
    }

    placeFood() {
      let x, y;
      do {
        x = Phaser.Math.Between(0, 39) * 20;
        y = Phaser.Math.Between(0, 29) * 20;
      } while (this.snakePositions.some((pos) => pos.x === x && pos.y === y));

      this.food.setPosition(x, y);
    }

    gameOver() {
      sessionStorage.setItem("lastScore", this.score);
      window.location.href = "/scenes/gameover.html";
    }
  }

  const config = {
    type: Phaser.AUTO,
    width: 800,
    height: 600,
    parent: "game", // S'assure que le jeu s'affiche dans #game
    scene: SnakeGame,
    backgroundColor: settings.backgroundColor,
  };

  const game = new Phaser.Game(config);

  // Attendre que Phaser soit bien chargé avant de redimensionner
  setTimeout(() => {
    game.scale.resize(800, 600);
  }, 100);

  // Débogage pour vérifier que Phaser est bien attaché
  console.log("Phaser initialisé dans #game:", document.getElementById("game"));
});
