package ru.itis.game;

import javafx.scene.image.Image;

import static ru.itis.game.SpaceInvaders.HEIGHT;
import static ru.itis.game.SpaceInvaders.score;

public class Enemy extends Spaceship {

    int speed = (score / 5) + 2;


    public Enemy(int posX, int posY, int size, Image image) {
        super(posX, posY, size, image);
    }

    public void update() {
        super.update();
        if (!exploding && !destroyed) posY += speed;
        if (posY > HEIGHT) destroyed = true;
    }
}
