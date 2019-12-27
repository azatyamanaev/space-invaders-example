package ru.itis.game;


import javafx.scene.image.Image;

import static ru.itis.game.SpaceInvaders.*;

public class Spaceship {
    int posX, posY, size;
    boolean exploding, destroyed;
    Image image;
    int explosionStep = 0;

    public Spaceship(int posX, int posY, int size, Image image) {
        this.posX = posX;
        this.posY = posY;
        this.size = size;
        this.image = image;
    }

    public Shot shoot() {
        return new Shot(posX + size / 2 - Shot.size / 2, posY - Shot.size);
    }

    public void update() {
        if (exploding) explosionStep++;
        destroyed = explosionStep > EXPLOSION_STEPS;
    }

    public void render() {
        if (exploding) {
            gc.drawImage(EXPLOSION_IMG, explosionStep % EXPLOSION_COL * EXPLOSION_WIDTH, (explosionStep / EXPLOSION_ROWS) * EXPLOSION_HEIGHT + 1, EXPLOSION_WIDTH, EXPLOSION_HEIGHT, posX, posY, size, size);
        } else {
            gc.drawImage(image, posX, posY, size, size);
        }
    }

    public boolean collide(Spaceship other) {
        int d = distance(this.posX + size / 2, this.posY + size / 2, other.posX + other.size / 2, other.posY + other.size / 2);
        return d < other.size / 2 + this.size / 2;
    }

    public void explode() {
        exploding = true;
        explosionStep = -1;
    }
}
