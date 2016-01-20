//Lazar Vukoje
//Jan 17 2016
//Concrete class used to create bullet objects for the runner to shoot

package com.nnldev.fartlek.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.nnldev.fartlek.Fartlek;

public class Bullet {
    //instance vars
    private Vector3 position;
    private Vector3 velocity;
    private Texture texture;
    private String path;
    private Rectangle rectangle;
    private float verticalSpeed;
    public int kills;
    public boolean done;

    /**
     * Main constructor for bullet
     * Sets a basic position, speed, kills, and its done or not done state
     */
    public Bullet() {
        position = new Vector3(0, 160, 0);
        verticalSpeed = 16;
        kills = 0;
        done = false;
    }

    /**
     * Second constructor for Bullet, chains main constructor
     * Sets texture, x position, velocity and its rectangle hitbox
     * @param path the string that is the path to the bullets image
     * @param x the x position of the runner at the current point, used to determine where the bullet will be shot from
     */
    public Bullet(String path, float x) {
        this();
        this.path = path;
        texture = new Texture(path);
        setXPosition(x);
        velocity = new Vector3(0, verticalSpeed, 0);
        rectangle = new Rectangle(x, getYPosition(), 32, 32);
    }

    /**
     * Updates the bullet, moves it in accordance to the background
     * @param dt The time which has passed since the last update
     */
    public void update(float dt) {
        //sets the new y position by adding the velocity to its position in Obstacle
        setYPosition(position.y + velocity.y);
        //does the same for its rectangle, as that is its hit box
        rectangle.setY(position.y + velocity.y);
        if (position.y > Fartlek.HEIGHT) {
            //if it goes below the screen, stop its movement and dispose of the image
            velocity.set(0, 0, 0);
            dispose();
            done = true;
        }
    }

    /**
     * draws the bullet
     * @param sb the SpriteBatch being used to draw
     */
    public void render(SpriteBatch sb) {
        sb.draw(texture, getXPosition(), getYPosition(), 32, 32);
    }

    /**
     * gets the texture of the box
     * @return the texture
     */
    public Texture getTexture() {
        return texture;
    }

    /**
     * gets the rectangle
     * @return the rectangle
     */
    public Rectangle getRectangle() {
        return rectangle;
    }

    /**
     * sets rectangle
     * @param rectangle The rectangle for whom the bounds of this bullet shall be set to
     */
    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    /**
     * Gets the position of the box
     * @return the position
     */
    public Vector3 getPosition() {
        return position;
    }

    /**
     * Sets the position of the bullet
     * @param position The Vector3 of the position you would like to set the obstacle's position to
     */
    public void setPosition(Vector3 position) { this.position = position; }

    /**
     * Sets x position
     * @param x The x position being set
     */
    public void setXPosition(float x) {
        position.x = x;
    }

    /**
     * gets x position
     * @return the x position
     */
    public float getXPosition() {
        return position.x;
    }

    /**
     * Sets y position
     * @param y The x position being used
     */
    public void setYPosition(float y) {
        position.y = y;

    }

    /**
     * gets y position
     * @return the x position
     */
    public float getYPosition() {
        return position.y;
    }

    /**
     * gets the velocity
     * @return the velocity
     */
    public Vector3 getVelocity() {
        return velocity;
    }

    /**
     * sets the velocity
     * @param velocity the velocity being set
     */
    public void setVelocity(Vector3 velocity) {
        this.velocity = velocity;
    }

    /**
     * gets the path to the sprite image
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * sets the path
     * @param texturePath the path being used
     */
    public void setPath(String texturePath) {
        path = texturePath;
    }

    /**
     * Disposes of the bullets texture
     */
    public void dispose() {
        texture.dispose();
    }
}
