package com.nnldev.fartlek.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

//Nick's obstacle class
public class Obstacle {
    private Vector3 position;
    private Texture texture;
    private Rectangle rectangle;
    private String path; //name of obstacle image, used to compare and make sure there aren't duplicate obstacles
    private boolean empty; //tells whether or not the obstacle is empty
    public int obstacleSpeed = -2;

    /**
     * @param path texture file path
     * @param x co ord
     * @param y co ord
     * @param emptyy is the state of the obstacle
     */
    public Obstacle(String path, float x, float y, boolean emptyy) {//i had to use weird double letters to avoid errors with this.
        position = new Vector3();
        this.path = path;
        texture = new Texture(path); //there can be multiple textures for obstacles
        position.x = x;
        position.y = y;
        rectangle = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
        empty = emptyy;
    }

    /**
     * @return the state of the obstacle
     */
    public boolean emptyStatus() {
        return empty;
    }

    /**
     * @param e state of the obstacle
     */
    public void setEmpty(boolean e) {
        empty = e;
    }

    /**
     * @param pathh texture file path
     */
    public void setTexture(String pathh) {
        path = pathh;
        texture = new Texture(pathh);
    }

    /**
     * @param y the new y pos
     */
    public void setY(float y) {
        position.y = y;
    }

    /**
     * @return the y pos
     */
    public float getY() {
        return position.y;
    }

    /**
     * @return the x pos
     */
    public float getX() {
        return position.x;
    }

    /**
     * @param x the new x co ord
     */
    public void setX(float x) {
        position.x = x;
    }

    /**
     * @return the vector 3 pos
     */
    public Vector3 getPosition() {
        return position;
    }

    /**
     * @param positionn the new vector 3 position
     */
    public void setPosition(Vector3 positionn) {
        position.set(positionn);
    }

    /**
     * @return the texture
     */
    public Texture getTexture() {
        return texture;
    }
}
