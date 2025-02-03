const config = {
  type: Phaser.AUTO,
  width: 800,
  height: 600,
  scene: {
    preload: preload,
    create: create,
    update: update,
  },
};

const game = new Phaser.Game(config);

function preload() {
  this.load.image("food", "assets/food.png");
}

function create() {
  this.add.image(400, 300, "food");
}

function update() {}

async function submitScore(score) {
  const response = await fetch("http://localhost:8080/score/submit", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ score: score }),
  });
  const data = await response.json();
  console.log("Score soumis:", data);
}
