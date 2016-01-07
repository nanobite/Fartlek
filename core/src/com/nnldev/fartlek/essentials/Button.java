/**
 * @authos Nano
 * A class to allow for easy creation of buttons that allows for easy handling of user input.
 */
package com.nnldev.fartlek.essentials;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;


public class Button {
    private Vector3 position;
    private Texture texture;
    private Rectangle rectangle;
    private String path;

    /**
     * Draws a button, allowing for the decision to draw the button centred or not textured.
     *
     * @param path   The path of the image to fetch the image from.
     * @param x      The X coordinate of the image to be drawn.
     * @param y      The Y coordinate of the image to be drawn.
     * @param centre Whether the image will be centred onto the point or not.
     */
    public Button(String path, float x, float y, boolean centre) {
        this.path = path;
        if (centre) {
            texture = new Texture(path);
            position = new Vector3(x - (texture.getWidth() / 2), y - (texture.getHeight() / 2), 0);
        } else {
            texture = new Texture(path);
            position = new Vector3(x, y, 0);
        }
        rectangle = new Rectangle(position.x, position.y, texture.getWidth(), texture.getHeight());
    }

    /**
     * Gets the position of the button
     *
     * @return
     */
    public Vector3 getPosition() {
        return position;
    }

    /**
     * Sets the position of the button
     *
     * @param position
     */
    public void setPosition(Vector3 position) {
        this.position = position;
    }

    /**
     * Gets the texture of the button
     *
     * @return
     */
    public Texture getTexture() {
        return texture;
    }

    /**
     * Sets the texture of the button given a different texture
     *
     * @param texture
     */
    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    /**
     * Sets the texture of the button given a name for the picture
     *
     * @param path
     */
    public void setTexture(String path) {
        this.path = path;
        setTexture(new Texture(path));
    }

    /**
     * Gets the rectangle bounds for the button
     *
     * @return
     */
    public Rectangle getRectangle() {
        return rectangle;
    }

    /**
     * Returns whether or not the position of the click is in the rectangle or not
     *
     * @param coords The position of the click
     * @return
     */
    public boolean contains(Vector3 coords) {
        if (rectangle.contains(coords.x, coords.y)) Gdx.input.vibrate(50);
        return (rectangle.contains(coords.x, coords.y)) ? true : false;
    }

    /**
     * Returns whether ot not the position of a click is in the rectangle or not
     *
     * @param x The x position of the click
     * @param y The y position of the click
     * @return
     */
    public boolean contains(float x, float y) {
        return contains(new Vector3(x, y, 0));
    }

    /**
     * Sets the rectangle bounds for the button
     *
     * @param rectangle
     */
    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    /**
     * Gets the name path for the picture
     *
     * @return
     */
    public String getPath() {
        return path;
    }

    /**
     * Disposes of the picture of the button
     */
    public void dispose() {
        texture.dispose();
    }
}
