package ru.itis.game;

import javafx.scene.paint.Color;

import static ru.itis.game.SpaceInvaders.*;

public class Shot {
    public boolean toRemove;

    int posX, posY, speed = 10;
    static final int size = 6;

    public Shot(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public void update() {
        posY -= speed;
    }

    public void render() {
        gc.setFill(Color.RED);
        if (score >= 50 && score <= 70 || score >= 120) {
            gc.setFill(Color.YELLOWGREEN);
            speed = 50;
            gc.fillRect(posX - 5, posY - 10, size + 10, size + 30);
        } else {
            gc.fillOval(posX, posY, size, size);
        }
    }

    public boolean collide(Spaceship spaceship) {
        int dis = distance(this.posX + size / 2, this.posY + size / 2, spaceship.posX + spaceship.size / 2, spaceship.posY + spaceship.size / 2);
        return dis < spaceship.size / 2 + size / 2;
    }
}
