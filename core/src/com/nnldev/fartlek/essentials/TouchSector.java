package com.nnldev.fartlek.essentials;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Nano on 12/12/2015.
 */
public class TouchSector {
    private Vector3 position;
    private Rectangle rectangle;

    /**
     * A region where the player can touch, makes finding touch events for buttons and stuff much easier much easier.
     *
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public TouchSector(float x, float y, float width, float height) {
        rectangle = new Rectangle(x, y, width, height);
        position = new Vector3(x, y, 0);
    }

    /**
     * Gets the position of the sector
     *
     * @return
     */
    public Vector3 getPosition() {
        return position;
    }

    /**
     * Sets the position of the sector to a new position
     *
     * @param position
     */
    public void setPosition(Vector3 position) {
        this.position = position;
    }

    /**
     * Gets the rectangle wich represents the bounds of the sector
     *
     * @return
     */
    public Rectangle getRectangle() {
        return rectangle;
    }

    /**
     * Sets the rectangle bounds of the touch sector
     *
     * @param rectangle
     */
    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }
}
