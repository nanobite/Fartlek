package com.nnldev.fartlek.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.nnldev.fartlek.Fartlek;


public abstract class Obstacle {
    protected Texture texture;
    protected Vector3 velocity;
    protected Vector3 position;
    protected Rectangle rectangle;
    protected String path; //name of obstacle image, used to compare and make sure there aren't duplicate obstacles
    public int obstacleSpeed = -8;
    boolean empty;

    /**
     * Creates a new box with a texture and an x and y position
     *
     * @param path The path for the obstacle's texture
     * @param x    The obstacle's x position
     * @param y    The obstacle's y position
     */
    protected Obstacle(String path, float x, float y) {
        this.path = path;
        texture = new Texture(path); //there can be multiple textures for obstacles
        position = new Vector3(x, y, 0); // position
        velocity = new Vector3(0, obstacleSpeed, 0);
        
    }
    protected Obstacle(String path, float x, float y, float width, float height) {
        this(path, x, y);
        rectangle = new Rectangle(x, y, width, height);
    }

    /**
     * Creates a new box with a texture, an x and y position and a boolean for whether it is empty or not
     *
     * @param path  The path for the obstacle's texture
     * @param x     The obstacle's x position
     * @param y     The obstacle's y position
     * @param empty Whether the object is empty or not
     */
    protected Obstacle(String path, float x, float y, boolean empty) {
        this(path, x, y);
        this.empty = empty;
    }

    /**
     * Creates a new obstacle based on a previously made obstacle
     *
     * @param obstacle
     */
    protected Obstacle(Obstacle obstacle) {
        this(obstacle.getPath(), obstacle.getXPosition(), obstacle.getYPosition(), obstacle.getEmpty());
    }

    /**
     * Updates the obstacle and it's position and what not
     *
     * @param dt The time which passed since the last update
     */
    public abstract void update(float dt);

    /**
     * Returns the position of the obstacle
     *
     * @return The Vector3 position of the obstacle.
     */
    public abstract Vector3 getPosition();

    /**
     * Sets the position of the obstacle.
     *
     * @param position The Vector3 of the position you would like to set the
     *                 obstacle's position to.
     */
    public abstract void setPosition(Vector3 position);

    /**
     * Sets the x coordinate of the obstacle's position
     *
     * @param x The x coordinate of the obstacle's position
     */
    public abstract void setXPosition(float x);

    /**
     * Gets the x position of the obstacle
     *
     * @return the x position of the obstacle
     */
    public abstract float getXPosition();

    /**
     * Sets the y coordinate of the obstacle's position
     *
     * @param y The y coordinate of the obstacle's position
     */
    public abstract void setYPosition(float y);

    /**
     * Gets the y coordinate of the obstacle's position
     *
     * @return The y coordinate of the obstacle's position
     */
    public abstract float getYPosition();

    /**
     * Returns the texture of the obstacle.
     *
     * @return The texture of the obstacle.
     */
    public abstract Texture getTexture();

    /**
     * Sets the texture of the obstacle.
     *
     * @param texture The texture which the obstacle will be set to
     */
    public abstract void setTexture(Texture texture);

    /**
     * Gets the rectangle which represents its hitbox of the obstacle to use
     * it's position
     *
     * @return The rectangle whose bounds represent the bounds of the obstacle
     */
    public abstract Rectangle getRectangle();

    /**
     * Sets whether or not it's empty
     *
     * @param empty
     */
    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    /**
     * Gets whether or not if it's empty
     *
     * @return
     */
    public boolean getEmpty() {
        return empty;
    }

    /**
     * Sets the rectangle of the obstacle to a new rectangle
     *
     * @param rectangle The rectangle for whome the bounds of this obstacle shall be
     *                  set to
     */
    public abstract void setRectangle(Rectangle rectangle);

    /**
     * Gets the velocities of the obstacle
     *
     * @return The obstacle's velocities in Vector3 format
     */
    public Vector3 getVelocity() {
        return velocity;
    }

    /**
     * Sets the velocities of the obstacle
     *
     * @param velocity The velocities of theo obstacle
     */
    public void setVelocity(Vector3 velocity) {
        this.velocity = velocity;
    }

    /**
     * Gets the path of the obstacle
     *
     * @return The path of the obstacle
     */
    public String getPath() {
        return path;
    }

    /**
     * Sets the path of the obstacle
     *
     * @param texturePath The path of the obstacle's texture
     * @deprecated Should not be used in any circumstance
     */
    public void setPath(String texturePath) {
        this.path = texturePath;
    }

    /**
     * Disposes of some of the objects that can cause memory leaks
     */
    public void dispose() {
        texture.dispose();
    }

    /**
     * Checks if two obstacles are equal or not
     *
     * @param obstacle something
     * @return boolean somthing
     */
    public abstract boolean equals(Obstacle obstacle);
    //TODO: make a clone method

    /**
     * Returns a string representation of the Obstacle
     */
    public abstract String toString();
}
