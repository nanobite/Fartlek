//Lazar Vukoje
//Jan 07 2016
//Abstract class, the blueprint for obstacles and enemies
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

    /* constructor
	path, name of texture
	x, x position
	y, pretty much just to animate it
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

    public abstract float getXPosition();

    /**
     * Sets the y coordinate of the obstacle's position
     *
     * @param y The y coordinate of the obstacle's position
     */
    public abstract void setYPosition(float y);

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
     * Sets the rectangle of the obstacle to a new rectangle
     *
     * @param rectangle The rectangle for whome the bounds of this obstacle shall be
     *                  set to
     */
    public abstract void setRectangle(Rectangle rectangle);

    public Vector3 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector3 velocity) {
        this.velocity = velocity;
    }

    public String getPath() {
        return path;
    }

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

    /**
     * Returns a string representation of the Obstacle
     */
    public abstract String toString();
}
