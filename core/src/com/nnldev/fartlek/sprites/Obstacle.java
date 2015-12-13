package com.nnldev.fartlek.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public interface Obstacle {
    /**
     * Returns the position of the obstacle
     *
     * @return The Vector3 position of the obstacle.
     */
    public abstract Vector3 getPosition();

    /**
     * Sets the position of the obstacle.
     *
     * @param position The Vector3 of the position you would like to set the obstacle's position to.
     */
    public abstract void setPosition(Vector3 position);

    /**
     * Returns the texture of the obstacle.
     *
     * @return
     */
    public abstract Texture getTexture();

    /**
     * Sets the texture of the obstacle.
     *
     * @param texture
     */
    public abstract void setTexture(Texture texture);

    /**
     * Gets the rectangle  which represents its hitbox of the obstacle to use it's position
     *
     * @return
     */
    public abstract Rectangle getRectangle();

    /**
     * Sets the rectangle of the obstacle to a new rectangle
     *
     * @param rectangle
     */
    public abstract void setRectangle(Rectangle rectangle);

    /**
     * Disposes of some of the objects that can cause memory leaks
     */
    public abstract void dispose();
}
