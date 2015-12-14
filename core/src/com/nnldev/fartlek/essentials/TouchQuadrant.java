package com.nnldev.fartlek.essentials;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Nano on 12/12/2015.
 */
public class TouchQuadrant {
    private Vector3 position;
    private Rectangle rectangle;

    public TouchQuadrant(float x, float y, float width, float height) {
        rectangle = new Rectangle(x, y, width, height);
        position = new Vector3(x, y, 0);
    }

    public Vector3 getPosition() {
        return position;
    }

    public void setPosition(Vector3 position) {
        this.position = position;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }
}
