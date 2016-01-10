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
     * Creates a new box obstacle which has a health
     *
     * @param path   The path of the box
     * @param x      The x coordinate of the box
     * @param y      The y coordinate of the box
     * @param health The health of the box
     */
    public Box(String path, float x, float y, int health) {
        super(path, x, y);
        this.health = health;
    }

    /**
     * Creates a new box obstacle which has a health and an empty status
     *
     * @param path   The path of the box
     * @param x      The x coordinate of the box
     * @param y      The y coordinate of the box
     * @param health The health of the box
     * @param empty  Whether or not the box is empty
     */
    public Box(String path, float x, float y, int health, boolean empty) {
        super(path, x, y, empty);
        this.health = health;
    }

    /**
     * Updates the box
     *
     * @param dt The time which passed since the last update
     */
    @Override
    public void update(float dt) {
        setYPosition(super.position.y + super.velocity.y);
        super.rectangle.setY(super.position.y + super.velocity.y);
        if (super.position.y + rectangle.getHeight() < 0) {
            super.velocity.set(0, 0, 0);
            dispose();
        }
    }

    /**
     * Gets the position of the box
     *
     * @return the position of the box
     */
    @Override
    public Vector3 getPosition() {
        return super.position;
    }

    /**
     * Sets the position of the box
     *
     * @param position The Vector3 of the position you would like to set the
     *                 obstacle's position to.
     */
    @Override
    public void setPosition(Vector3 position) {
        super.position = position;
    }

    /**
     * Sets the x coordinate of the box
     *
     * @param x The x coordinate of the obstacle's position
     */
    @Override
    public void setXPosition(float x) {
        super.position.x = x;
    }

    /**
     * Returns the x position of the obstacle
     *
     * @return the x position of the obstacle
     */
    public float getXPosition() {
        return position.x;
    }

    /**
     * Sets the y position of the box
     *
     * @param y The y coordinate of the obstacle's position
     */
    @Override
    public void setYPosition(float y) {
        super.position.y = y;

    }

    /**
     * Gets the y position of the box
     *
     * @return The y positionf of the box
     */
    @Override
    public float getYPosition() {
        return super.position.y;
    }

    /**
     * Gets the velocity of the box
     *
     * @return
     */
    @Override
    public Vector3 getVelocity() {
        return super.getVelocity();
    }

    /**
     * Sets the velocities of the obstacle
     *
     * @param velocity The velocities of theo obstacle
     */
    @Override
    public void setVelocity(Vector3 velocity) {
        super.setVelocity(velocity);
    }

    /**
     * Gets the path of the obstacle
     *
     * @return
     */
    @Override
    public String getPath() {
        return super.getPath();
    }

    /**
     * @param texturePath
     * @deprecated There is no situation where this should be used
     */
    @Override
    public void setPath(String texturePath) {
        // TODO Auto-generated method stub
        super.setPath(texturePath);
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
        super.texture = texture;
    }

    /**
     * @return
     */
    @Override
    public Rectangle getRectangle() {
        return super.rectangle;
    }

    /**
     * @param rectangle The rectangle for whome the bounds of this obstacle shall be
     *                  set to
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
        super.dispose();
    }

    /**
     * Checks if two boxes are equal or not
     *
     * @param obstacle something
     * @return
     */
    @Override
    public boolean equals(Obstacle obstacle) {
        if ((obstacle.path.equals(this.path))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns a string representation of the box
     *
     * @return The string representation of the box
     */
    @Override
    public String toString() {
        return "Path: " + path + "\nCoordinates: (" + getXPosition() + "," + getYPosition() + ")" + "\nVelocities: X="
                + velocity.x + "\tY=" + velocity.y + "\tZ=" + velocity.z + "\nWidth: " + rectangle.getWidth()
                + "\tHeight: " + rectangle.getHeight() + "\nHealth: " + health;
    }

}
