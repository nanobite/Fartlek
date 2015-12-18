package com.nnldev.fartlek.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Nano on 12/12/2015.
 */
public class Box extends Obstacle {
    private int health;

    /**
     * Creates a
     *
     * @param path
     * @param x
     * @param y
     * @param health
     */
    public Box(String path, int x, int y, int health) {
        this.health = health;
        texture = new Texture(path);
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0, 0, 0);
        rectangle = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
    }

    /**
     * Updates the box
     *
     * @param dt The time which passed wicne the last update
     */
    @Override
    public void update(float dt) {
        position.set(position.x + velocity.x, position.y + velocity.y, position.z + velocity.z);
        if (position.y + rectangle.getHeight() < 0) {
            velocity.set(0, 0, 0);
            dispose();
        }
    }

    /**
     * Gets the position of the box
     *
     * @return
     */
    @Override
    public Vector3 getPosition() {
        return position;
    }

    /**
     * Sets the position of the box
     *
     * @param position The Vector3 of the position you would like to set the obstacle's position to.
     */
    @Override
    public void setPosition(Vector3 position) {
        this.position = position;
    }

    /**
     * Gets the texture of the box
     *
     * @return
     */
    @Override
    public Texture getTexture() {
        return texture;
    }

    /**
     * @param texture The texture which the obstacle will be set to
     */
    @Override
    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    /**
     * @return
     */
    @Override
    public Rectangle getRectangle() {
        return rectangle;
    }

    /**
     * @param rectangle The rectangle for whome the bounds of this obstacle shall be set to
     */
    @Override
    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    /**
     * Gets the health of the box
     *
     * @return The health of the box
     */
    public int getHealth() {
        return health;
    }

    /**
     * Sets the health of the box
     *
     * @param health
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Disposes of the box's texture
     */
    @Override
    public void dispose() {
        texture.dispose();
    }
}
