package ru.itis.game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import javafx.application.Application;

public class SpaceInvaders extends Application {
    static final Random random = new Random();
    static final int WIDTH = 800;
    static final int HEIGHT = 600;
    public static final int PLAYER_SIZE = 60;

    public static final Image PLAYER_IMG;
    static final Image EXPLOSION_IMG;

    static {
        try {
            PLAYER_IMG = new Image(new FileInputStream("/home/monitor/Изображения/images/space/spaceship.png"));
            EXPLOSION_IMG = new Image(new FileInputStream("/home/monitor/Изображения/images/space/explosions/SkybusterExplosion.jpg"));
        } catch (FileNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }


    static final int EXPLOSION_WIDTH = 200;
    static final int EXPLOSION_ROWS = 3;
    static final int EXPLOSION_COL = 3;
    static final int EXPLOSION_HEIGHT = 240;
    static final int EXPLOSION_STEPS = 12;

    public static final Image enemiesImg[];

    static {
        try {
            enemiesImg = new Image[]{
                    new Image(new FileInputStream("/home/monitor/Изображения/images/space/chronos.png")),
                    new Image(new FileInputStream("/home/monitor/Изображения/images/space/Cosmoteer.png")),
                    new Image(new FileInputStream("/home/monitor/Изображения/images/space/Flock.png")),
                    new Image(new FileInputStream("/home/monitor/Изображения/images/space/Flock_Prime.png")),
                    new Image(new FileInputStream("/home/monitor/Изображения/images/space/interceptor.png")),
                    new Image(new FileInputStream("/home/monitor/Изображения/images/space/Kar Ik Vot 349.png")),
                    new Image(new FileInputStream("/home/monitor/Изображения/images/space/Mamoth 5.png")),
                    new Image(new FileInputStream("/home/monitor/Изображения/images/space/Maquis_Raider.png")),
                    new Image(new FileInputStream("/home/monitor/Изображения/images/space/Saber.png")),
                    new Image(new FileInputStream("/home/monitor/Изображения/images/space/valkyrie.png")),
                    new Image(new FileInputStream("/home/monitor/Изображения/images/space/hurricane.png"))
            };
        } catch (FileNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

    final int MAX_BOMBS = 10, MAX_SHOTS = MAX_BOMBS * 2;
    boolean gameOver = false;
    static GraphicsContext gc;

    Spaceship player;
    List<Shot> shots;
    List<Universe> univ;
    List<Enemy> enemies;

    private double mouseX;
    static int score;


    @Override
    public void start(Stage primaryStage) throws Exception {
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), e -> run(gc)));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        canvas.setCursor(Cursor.MOVE);
        canvas.setOnMouseMoved(e -> mouseX = e.getX());
        canvas.setOnMouseClicked(e -> {
            if (shots.size() < MAX_SHOTS) shots.add(player.shoot());
            if (gameOver) {
                gameOver = false;
                setup();
            }
        });
        setup();
        primaryStage.setScene(new Scene(new StackPane(canvas)));
        primaryStage.setTitle("Space Invaders");
        primaryStage.show();
    }


    public void setup() {
        univ = new ArrayList<>();
        shots = new ArrayList<>();
        enemies = new ArrayList<>();
        player = new Spaceship(WIDTH / 2, HEIGHT - PLAYER_SIZE, PLAYER_SIZE, PLAYER_IMG);
        score = 0;
        IntStream.range(0, MAX_BOMBS).mapToObj(i -> this.newEnemy()).forEach(enemies::add);
    }

    public void run(GraphicsContext gc) {
        gc.setFill(Color.grayRgb(20));
        gc.fillRect(0, 0, WIDTH, HEIGHT);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFont(Font.font(20));
        gc.setFill(Color.WHITE);
        gc.fillText("Score: " + score, 60, 20);

        if (gameOver) {
            gc.setFont(Font.font(35));
            gc.setFill(Color.YELLOW);
            gc.fillText("Game Over \n Your Score is: " + score + "\n Click to play again", WIDTH / 2, HEIGHT / 2);
        }
        univ.forEach(Universe::render);

        player.update();
        player.render();
        player.posX = (int) mouseX;

        enemies.stream().peek(Spaceship::update).peek(Spaceship::render).forEach(e -> {
            if (player.collide(e) && !player.exploding) {
                player.explode();
            }
        });

        for (int i = shots.size() - 1; i >= 0; i--) {
            Shot shot = shots.get(i);
            if (shot.posY < 0 || shot.toRemove) {
                shots.remove(i);
                continue;
            }
            shot.update();
            shot.render();
            for (Enemy enemy : enemies) {
                if (shot.collide(enemy) && !enemy.exploding) {
                    score++;
                    enemy.explode();
                    shot.toRemove = true;
                }
            }
        }

        for (int i = enemies.size() - 1; i >= 0; i--) {
            if (enemies.get(i).destroyed) {
                enemies.set(i, newEnemy());
            }
        }
        gameOver = player.destroyed;
        if (random.nextInt(10) > 2) {
            univ.add(new Universe());
        }
        for (int i = 0; i < univ.size(); i++) {
            if (univ.get(i).posY > HEIGHT) {
                univ.remove(i);
            }
        }
    }


    Enemy newEnemy() {
        return new Enemy(50 + random.nextInt(WIDTH - 100), 0, PLAYER_SIZE, enemiesImg[random.nextInt(enemiesImg.length)]);
    }

    public static int distance(int x1, int y1, int x2, int y2) {
        return (int) Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
    }

    public static void main() {
        launch();
    }
}
