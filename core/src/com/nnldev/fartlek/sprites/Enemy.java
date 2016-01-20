//Lazar Vukoje
//January 18
//Concrete class for in-game enemies from obstacle abstract class
package com.nnldev.fartlek.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.nnldev.fartlek.Fartlek;

public class Enemy extends Obstacle {
    //instance vars
    private int health;
    //class vars
    public static final float ENEMY_WIDTH = (Fartlek.WIDTH) / 5.5f;
    public static final float ENEMY_HEIGHT = (Fartlek.WIDTH) / 5.5f;

    /**
     * Creates an enemy, chains obstacle constructor from parent class Obstacle
     * @param path the string that points to the sprite image
     * @param x the x position
     * @param y the y position
     * @param health the health of the enemy
     */
    public Enemy(String path, float x, float y, int health) {
        super(path, x, y, ENEMY_WIDTH, ENEMY_HEIGHT);
        this.health = health;
    }

    /**
     * Updates the enemy, moves it in accordance to the background
     * @param dt The time which has passed since the last update
     */
    @Override
    public void update(float dt) {
        //sets the new y position by adding the velocity to its position in Obstacle
        setYPosition(super.position.y+super.velocity.y);
        //does the same for its rectangle, as that is its hit box
        super.rectangle.setY(super.position.y+super.velocity.y);
        if (super.position.y + rectangle.getHeight() < 0) {
            //if it goes below the screen, stop its movement and dispose of the image
            super.velocity.set(0, 0, 0);
            dispose();
        }
    }

    /**
     * Gets the position of the box
     * @return the position
     */
    @Override
    public Vector3 getPosition() {
        return super.position;
    }

    /**
     * Sets the position of the box
     * @param position The Vector3 of the position you would like to set the obstacle's position to
     */
    @Override
    public void setPosition(Vector3 position) { super.position = position; }

    /**
     * Sets x position
     * @param x The x position being set
     */
    @Override
    public void setXPosition(float x) {
        super.position.x = x;
    }

    /**
     * gets x posiiton
     * @return the x position
     */
    public float getXPosition() {
        return position.x;
    }

    /**
     * Sets y position
     * @param y The x position being used
     */
    @Override
    public void setYPosition(float y) {
        super.position.y = y;

    }

    /**
     * gets y position
     * @return the x position
     */
    public float getYPosition() {
        return super.position.y;
    }

    /**
     * gets the velocity
     * @return the velocity
     */
    @Override
    public Vector3 getVelocity() {
        return super.getVelocity();
    }

    /**
     * sets the velocity
     * @param velocity the velocity being set
     */
    @Override
    public void setVelocity(Vector3 velocity) {
        super.setVelocity(velocity);
    }

    /**
     *
     * @return
     */
    @Override
    public String getPath() {
        return super.getPath();
    }

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
     * @param texture
     *            The texture which the obstacle will be set to
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
     * @param rectangle
     *            The rectangle for whome the bounds of this obstacle shall be
     *            set to
     */
    @Override
    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    /**
     * Gets the health of the box
     * @return The health of the box
     */
    public int getHealth() {
        return health;
    }

    /**
     * Sets the health of the box
     * @param health the health being set
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

    @Override
    public boolean equals(Obstacle obstacle) {
        if ((obstacle.path.equals(this.path))) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Path: " + path + "\nCoordinates: (" + getXPosition() + "," + getYPosition() + ")" + "\nVelocities: X="
                + velocity.x + "\tY=" + velocity.y + "\tZ=" + velocity.z + "\nWidth: " + rectangle.getWidth()
                + "\tHeight: " + rectangle.getHeight() + "\nHealth: " + health;
    }

}
