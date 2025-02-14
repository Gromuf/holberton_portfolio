class SnakeGame extends Phaser.Scene {
  constructor() {
    super("SnakeGame");
  }

  preload() {
    // Charger les ressources si nécessaire
  }

  create() {
    this.score = 0;
    this.scoreText = this.add.text(10, 10, "Score: 0", {
      fontFamily: "Press Start 2P",
      fontSize: "16px",
      fill: "#00ffc3",
    });

    // Ajouter une bordure autour de la map
    this.add.rectangle(400, 300, 800, 600).setStrokeStyle(4, 0x00ffc3);

    // Création du serpent
    this.snake = this.add.group();
    this.direction = "RIGHT";
    this.speed = 200; // Vitesse initiale réduite
    this.lastMoveTime = 0;

    // Position initiale du serpent
    this.snakePositions = [{ x: 160, y: 160 }];
    this.drawSnake();

    // Générer la nourriture
    this.food = this.add.rectangle(300, 300, 20, 20, 0xff0000).setOrigin(0);
    this.placeFood();

    // Gestion des touches
    this.input.keyboard.on("keydown", (event) => {
      if (event.key === "ArrowUp" && this.direction !== "DOWN") {
        this.direction = "UP";
      } else if (event.key === "ArrowDown" && this.direction !== "UP") {
        this.direction = "DOWN";
      } else if (event.key === "ArrowLeft" && this.direction !== "RIGHT") {
        this.direction = "LEFT";
      } else if (event.key === "ArrowRight" && this.direction !== "LEFT") {
        this.direction = "RIGHT";
      }
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
        .rectangle(pos.x, pos.y, 20, 20, 0x00ffc3)
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

    // Vérifier collision avec les murs
    if (
      newHead.x < 0 ||
      newHead.x >= 800 ||
      newHead.y < 0 ||
      newHead.y >= 600
    ) {
      this.gameOver();
      return;
    }

    // Vérifier collision avec le corps
    if (
      this.snakePositions.some(
        (pos, index) =>
          index !== 0 && pos.x === newHead.x && pos.y === newHead.y
      )
    ) {
      this.gameOver();
      return;
    }

    // Ajouter la nouvelle tête
    this.snakePositions.unshift(newHead);

    // Vérifier si le serpent mange la nourriture
    if (newHead.x === this.food.x && newHead.y === this.food.y) {
      this.score += 10;
      this.scoreText.setText(`Score: ${this.score}`);
      this.speed = Math.max(50, this.speed - 10); // Augmenter la vitesse à chaque fruit
      this.placeFood();
    } else {
      // Retirer la queue
      this.snakePositions.pop();
    }

    this.drawSnake();
  }

  placeFood() {
    const x = Phaser.Math.Between(0, 39) * 20;
    const y = Phaser.Math.Between(0, 29) * 20;
    this.food.setPosition(x, y);
  }

  gameOver() {
    // Stocker le score et rediriger vers la page gameover
    sessionStorage.setItem("lastScore", this.score);
    window.location.href = "/scenes/gameover.html";
  }
}

const config = {
  type: Phaser.AUTO,
  width: 800,
  height: 600,
  scene: SnakeGame,
  backgroundColor: "#1a1a1a",
};

new Phaser.Game(config);
