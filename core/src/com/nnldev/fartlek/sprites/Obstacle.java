package com.nnldev.fartlek.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;


public class Obstacle {
    private Vector3 position;
    private static Texture texture;
    private Rectangle rectangle;
    private static String path; //name of obstacle image, used to compare and make sure there aren't duplicate obstacles
    private static boolean empty; //tells whether or not the obstacle is empty
    public static int obstacleSpeed = -2;

    /**
     * @param pathh
     * @param x
     * @param y
     * @param e
     */
    public Obstacle(String pathh, float x, float y, boolean e) {//i had to use weird double letters to avoid errors with this.
        position = new Vector3();
        path = pathh;
        texture = new Texture(path); //there can be multiple textures for obstacles
        position.x = x;
        position.y = y;
        rectangle = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
        empty = e;
    }

    /**
     * @return
     */
    public boolean emptyStatus() {
        return empty;
    }

    /**
     * @param e
     */
    public void setEmpty(boolean e) {
        empty = e;
    }

    /**
     * @param p
     */
    public void setTexture(String path) {
        this.path = path;
        texture = new Texture(path);
    }

    /**
     * @param y
     */
    public void setY(float y) {
        position.y = y;
    }

    /**
     * @return
     */
    public float getY() {
        return position.y;
    }

    /**
     * @return
     */
    public float getX() {
        return position.x;
    }

    /**
     * @param x
     */
    public void setX(float x) {
        position.x = x;
    }

    /**
     * @return
     */
    public Vector3 getPosition() {
        return position;
    }

    /**
     * @param position
     */
    public void setPosition(Vector3 position) {
        this.position.set(position);
    }

    /**
     * @return
     */
    public Texture getTexture() {
        return texture;
    }
}
