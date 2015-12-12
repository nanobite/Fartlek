package com.nnldev.fartlek.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Nano on 12/12/2015.
 */
public class Box extends Obstacle{
    public Box(String path, int x, int y) {
        super(path, x, y);
    }

    @Override
    public Vector3 getPosition() {
        return null;
    }

    @Override
    public void setPosition(Vector3 position) {

    }

    @Override
    public Texture getTexture() {
        return null;
    }

    @Override
    public void setTexture(Texture texture) {

    }

    @Override
    public Rectangle getRectangle() {
        return null;
    }

    @Override
    public void setRectangle(Rectangle rectangle) {

    }
}
