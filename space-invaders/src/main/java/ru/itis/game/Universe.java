package ru.itis.game;

import javafx.scene.paint.Color;

import static ru.itis.game.SpaceInvaders.*;

public class Universe {
    int posX, posY;
    private int h, w, r, g, b;
    private double opacity;

    public Universe() {
        posX = random.nextInt(WIDTH);
        posY = 0;
        w = random.nextInt(5) + 1;
        h = random.nextInt(5) + 1;
        r = random.nextInt(100) + 150;
        g = random.nextInt(100) + 150;
        b = random.nextInt(100) + 150;
        opacity = random.nextFloat();
        if (opacity < 0) opacity *= -1;
        if (opacity > 0.5) opacity = 0.5;
    }

    public void render() {
        if (opacity > 0.8) opacity -= 0.01;
        if (opacity < 0.1) opacity += 0.01;
        gc.setFill(Color.rgb(r, g, b, opacity));
        gc.fillOval(posX, posY, w, h);
        posY += 20;
    }
}
